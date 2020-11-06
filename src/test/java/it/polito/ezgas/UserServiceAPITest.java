package it.polito.ezgas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import exception.InvalidLoginDataException;
import exception.InvalidUserException;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.impl.UserServiceimpl;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceAPITest {
	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;
    
    private UserServiceimpl userServiceimpl;
    
    private User user1;
    private User user2;
    private User user3;
    
    @Before
    public void init() {
    	user1 = new User("Admin1", "fakePassw0rd", "fake@email.com", 5);
    	user1.setAdmin(true);
    	
    	user2 = new User("User2", "Passw0rdFake!", "ake12@email.com", -1);
    	user2.setAdmin(false);
    	
    	user3 = new User("User3", "Passw0rdFake!", "ake13@email.com", -5);
    	user3.setAdmin(false);
		
    	userServiceimpl = new UserServiceimpl(userRepository);
    	entityManager.persist(user1);
    	entityManager.persist(user2);
    	entityManager.persist(user3);
    }
    
    @After
    public void clear() {
    	entityManager.remove(user1);
    	entityManager.remove(user2);
    	entityManager.remove(user3);
    }
    
    @Test
	public void testAPIGetUserById1() {
		//passing a null Id, Exception expected
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.getUserById(null), 
				"Expected an InvalidUserException for a null id to be thrown");
	}
	
	@Test
	public void testAPIGetUserById2() {
		//passing a negative Id, Exception expected
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.getUserById(-1), 
				"Expected an InvalidUserException for a null id to be thrown");
	}
	
	@Test
	public void testAPIGetUserById3() throws InvalidUserException {
		//passing valid Id, checking for the result
		User u = new User("Admin1", "fakePassw0rd", "fake@email.com", 5);
		
		UserDto result = userServiceimpl.getUserById(user1.getUserId());
		u.setAdmin(result.getAdmin());
		u.setUserId(user1.getUserId());
		
		assertTrue(usersEquals(u, result), "User not the one expected");		
	}

	@Test
	public void testAPIGetUserById4() throws InvalidUserException {
		//user doesn't exists
		
		UserDto result = userServiceimpl.getUserById(10);
		assertTrue( result == null, "User not the one expected");		
	}

	
	@Test
	public void testSaveUser1(){
		//saving a null user, should return null
		UserDto uDto = null;
		User u = null;
		
		UserDto result = userServiceimpl.saveUser(uDto);
		assertTrue(usersEquals(u, result), "User not saved properly");		
	}
	
	@Test
	public void testSaveUser2(){
		//saving a normal user 
		UserDto uDto = new UserDto(null, "name", "pwd", "name@pwd.it",0);
		User u = new User("name", "pwd", "name@pwd.it",0);
		u.setAdmin(uDto.getAdmin());
		
		UserDto result = userServiceimpl.saveUser(uDto);
		userRepository.delete(result.getUserId());
		u.setUserId(result.getUserId());
		assertTrue(usersEquals(u, result), "User not saved properly");		
	}

	@Test
	public void testSaveUser3(){
		//saving an admin with an existing email 
		UserDto newAdmin = new UserDto(null, "Admin2", "fakePassw0rd2", "fake@email.com", 5);
		newAdmin.setAdmin(true);

		
		UserDto result = userServiceimpl.saveUser(newAdmin);
	
		assertTrue( (newAdmin.getUserName() == result.getUserName() && newAdmin.getEmail() == result.getEmail() && 
				newAdmin.getPassword() == result.getPassword() && newAdmin.getReputation() == result.getReputation()
				&& newAdmin.getAdmin() == result.getAdmin()),"User not saved properly");		
	}
	
	@Test
	public void testSaveUser4(){
		//saving a normal user with an existing email 
		UserDto newUser = new UserDto(null,"NewUser", "Passw0rdFake!", "ake12@email.com", -1);
		newUser.setAdmin(false);
		
		UserDto result = userServiceimpl.saveUser(newUser);
		assertTrue(usersEquals(null, result), "User not saved properly");		
	}
	
	@Test
	public void testSaveUser5() throws InvalidUserException{
		//modify an existing user 	user2 =("User2", "Passw0rdFake!", "ake12@email.com", -1);
		UserDto uDto = new UserDto(1, "User2new", "pwd", "ake12@email.com",2);
	
		UserDto result = userServiceimpl.saveUser(uDto);
		UserDto u = userServiceimpl.getUserById(result.getUserId());
		assertTrue( (u.getUserName() == result.getUserName() && u.getEmail() == result.getEmail() && 
				u.getPassword() == result.getPassword() && u.getReputation() == result.getReputation() &&
				u.getUserId() == result.getUserId() && u.getAdmin() == result.getAdmin()),"User not saved properly");		
		
	}

	
	@Test
	public void testGetAllUsers1(){
		//Retrieving the list of users
		
		List<User> users = new ArrayList<>();
		User user= new User("admin", "admin", "admin@ezgas.com", 5);
		user.setAdmin(true);
		user.setUserId(1);//this is the admin inserted during the bootstrap
		users.add(user);
		users.add(user1);
		users.add(user2);
		users.add(user3);


		List<UserDto> result = userServiceimpl.getAllUsers();
		System.out.println(result);
		System.out.println(users);
		assertTrue(userListEqual(users,result), "User not saved properly");		
	}
	
	
	@Test
	public void testDeleteUser1(){
		//passing a invalid(null) Id, Exception expected
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.deleteUser(null), 
				"Expected an InvalidUserException for a null id to be thrown");
	}
	
	@Test
	public void testDeleteUser2() {
		//passing an invalid id(negative), exception expected
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.deleteUser(-1), 
				"Expected an InvalidUserException for a null id to be thrown");
	}
	
	
	@Test
	public void testDeleteUser3() throws InvalidUserException {
		//passing valid Id, checking for the result
	
		assertTrue(userServiceimpl.deleteUser(user3.getUserId()), "User not delelted correctly");
	}

	@Test
	public void testDeleteUser4() throws InvalidUserException  {
		//passing an invalid Id, id not in the database, Exception expected
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.deleteUser(999), 
				"Expected an InvalidUserException for a null id to be thrown");		
	}
	
	@Test
	public void testLogin1() throws InvalidLoginDataException{	
		//should be successful, right credentials (testing user2 = new User("User2", "Passw0rdFake!", "ake12@email.com", -1))
		IdPw credentials = new IdPw("ake12@email.com", "Passw0rdFake!");
		LoginDto result = userServiceimpl.login(credentials);
		assertTrue(validLogin(user2,result), "Expected to be successful, credentials are right");
	
	}
	
	@Test
	public void testLogin2(){		
		//should fail, wrong password  (testing user2 = new User("User2", "Passw0rdFake!", "ake12@email.com", -1))
		IdPw credentials = new IdPw("ake12@email.com", "wrongpwd");

		assertThrows(exception.InvalidLoginDataException.class, () ->  userServiceimpl.login(credentials), 
				"Expected an InvalidLoginDataException for wrong password to be thrown");
	}
	
	@Test
	public void testLogin3(){		
		//should fail, wrong user name (testing user2 = new User("User2", "Passw0rdFake!", "ake12@email.com", -1))
		IdPw credentials = new IdPw("user2", "Passw0rdFake"); //emailNeeded

		assertThrows(exception.InvalidLoginDataException.class, () ->  userServiceimpl.login(credentials), 
				"Expected an InvalidLoginDataException for null credentials to be thrown");
	}
	
	@Test
	public void testLogin4(){		
		//should fail, credentials null

		assertThrows(exception.InvalidLoginDataException.class, () ->  userServiceimpl.login(null), 
				"Expected an InvalidLoginDataException for null credentials to be thrown");
	}
	
	@Test
	public void testincreaseUserReputation1() {
		//passing a null Id, Exception expected		 
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.increaseUserReputation(null), 
				"Expected an InvalidUserException for a null id to be thrown");
	}
	
	@Test
	public void testincreaseUserReputation2(){
		//passing a negative Id, Exception expected
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.getUserById(-1), 
				"Expected an InvalidUserException for a negative id to be thrown");
	}
	
	@Test
	public void testIncreaseUserReputation3() throws InvalidUserException {
		//passing valid Id, checking for the result
	   	Integer oldReputation = user2.getReputation();

		
		Integer result = userServiceimpl.increaseUserReputation(user2.getUserId());
		assertTrue( oldReputation +1 == result, "The reputation is not the one expected");		
		userServiceimpl.decreaseUserReputation(user2.getUserId());		
	}

	
	@Test
	public void testIncreaseUserReputation4() throws InvalidUserException {
		//passing valid Id but the reputation is the highest possible(5), it should not change
	   	Integer oldReputation = user1.getReputation();

		
		Integer result = userServiceimpl.increaseUserReputation(user1.getUserId());
		assertTrue( oldReputation  == result, "The reputation is not the one expected");		
		
	}
	
	@Test
	public void testIncreaseUserReputation5() {		
		//user doesn't exists
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.decreaseUserReputation(999), 
				"Expected an InvalidUserException for an invalid id to be thrown");
	}
	
	@Test
	public 	void testDecreaseUserReputation1() {
		//passing a null Id, Exception expected		 
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.decreaseUserReputation(null), 
				"Expected an InvalidUserException for a null id to be thrown");
	}
	
	@Test
	public void testDecreaseUserReputation2(){
		//passing a negative Id, Exception expected
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.decreaseUserReputation(-1), 
				"Expected an InvalidUserException for a negative id to be thrown");
	}
	
	@Test
	public void testDecreaseUserReputation3() throws InvalidUserException {
		//passing valid Id, checking for the result
    	Integer oldReputation = user2.getReputation();

		
		Integer result = userServiceimpl.decreaseUserReputation(user2.getUserId());
		assertTrue( oldReputation - 1 == result, "The reputation is not the one expected");		
		userServiceimpl.increaseUserReputation(user2.getUserId());
	}
	
	@Test
	public void testDecreaseUserReputation4() throws InvalidUserException {
		//passing valid Id but the reputation is the lowest possible(-5), it should not change

		Integer oldReputation = user3.getReputation() ;
		
		Integer result = userServiceimpl.decreaseUserReputation(user3.getUserId());
		assertTrue( oldReputation == result, "The reputation is not the one expected");		
	}
	
	@Test
	public void testDecreaseUserReputation5() {		
		//user doesn't exists
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.increaseUserReputation(999), 
				"Expected an InvalidUserException for an invalid id to be thrown");
	}	

	private Boolean validLogin(User u, LoginDto result) {
		if(u == null || result == null)
			return false;
		
		if(u.getUserName() == result.getUserName() && u.getEmail() == result.getEmail() && u.getUserId() == result.getUserId() 
				&& u.getAdmin() == result.getAdmin() && u.getReputation() == result.getReputation() )
			return true;
		
		return false;
	}

	private Boolean usersEquals(User u, UserDto result) {
		
		if(u == null && result == null)
			return true;
		
		if(u.getUserName() == result.getUserName() && u.getEmail() == result.getEmail() && 
				u.getPassword() == result.getPassword() && u.getReputation() == result.getReputation() &&
				u.getUserId() == result.getUserId() && u.getAdmin() == result.getAdmin())
			return true;
		return false;
	}
	
	private Boolean userListEqual(List<User> users, List<UserDto> result) {
		
		if(users.size() != result.size())
			return false;
		for(int i = 0 ; i < users.size(); i++) {
			if(!usersEquals(users.get(i), result.get(i)))
				return false;
		}		
		return true;
	}
	
	
	
}



