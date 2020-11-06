package it.polito.ezgas;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
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

class TestUserServiceimpl {
	
	UserServiceimpl userServiceimpl;
	UserRepository userRepository;
	

	@BeforeEach
	public void setUp() throws Exception {
		userRepository = mock(UserRepository.class);
		userServiceimpl = new UserServiceimpl(userRepository);
	}

	@Test
	public void testGetUserById1() {
		//passing a null Id, Exception expected
		when(userRepository.findOne(anyInt())).thenReturn(null);
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.getUserById(null), 
				"Expected an InvalidUserException for a null id to be thrown");
	}
	
	@Test
	public void testGetUserById2() {
		//passing a negative Id, Exception expected
		when(userRepository.findOne(anyInt())).thenReturn(null);
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.getUserById(-1), 
				"Expected an InvalidUserException for a null id to be thrown");
	}
	
	@Test
	public void testGetUserById3() throws InvalidUserException {
		//passing valid Id, checking for the result
		User u = new User("name", "pwd", "name@pwd.it",0);
		when(userRepository.findOne(anyInt())).thenReturn(u);
		
		UserDto result = userServiceimpl.getUserById(1);
		assertTrue(usersEquals(u, result), "User not the one expected");		
	}

	@Test
	public void testGetUserById4() throws InvalidUserException {
		//user doesn't exists
		when(userRepository.findOne(anyInt())).thenReturn(null);
		
		UserDto result = userServiceimpl.getUserById(1);
		assertTrue( result == null, "User not the one expected");		
	}

	
	@Test
	public void testSaveUser1(){
		//saving a null user, should return null
		UserDto uDto = null;
		User u = null;
		when(userRepository.save(any(User.class))).thenReturn(null);
		
		UserDto result = userServiceimpl.saveUser(uDto);
		assertTrue(usersEquals(u, result), "User not saved properly");		
	}
	
	@Test
	public void testSaveUser2(){
		//saving a normal user 
		UserDto uDto = new UserDto(null, "name", "pwd", "name@pwd.it",0);
		User u = new User("name", "pwd", "name@pwd.it",0);
		u.setUserId(1);
		u.setAdmin(uDto.getAdmin());//this is done by the converter, but by mocking .save() the effort of the converter is lost. DON'T KNOW HOW TO DO THIS 
		when(userRepository.save(any(User.class))).thenReturn(u);
		
		UserDto result = userServiceimpl.saveUser(uDto);
		assertTrue(usersEquals(u, result), "User not saved properly");		
	}

	@Test
	public void testSaveUser3(){
		//saving an admin with an existing email 
		User old = new User("name", "pwd", "name@pwd.it",0);
		UserDto uDto = new UserDto(null, "name", "pwd", "name@pwd.it",0);
		uDto.setAdmin(true);
		User u = new User("name", "pwd", "name@pwd.it",0);
		u.setUserId(1);
		u.setAdmin(true);//this is done by the converter, but by mocking .save() the effort of the converter is lost. DON'T KNOW HOW TO DO THIS 
		when(userRepository.findByEmail("name@pwd.it")).thenReturn(old);
		when(userRepository.save(any(User.class))).thenReturn(u);
		
		UserDto result = userServiceimpl.saveUser(uDto);
		assertTrue(usersEquals(u, result), "User not saved properly");		
	}
	
	@Test
	public void testSaveUser4(){
		//saving a normal user with an existing email 
		User old = new User("name", "pwd", "name@pwd.it",0);
		UserDto uDto = new UserDto(null, "name", "pwd", "name@pwd.it",0);
		uDto.setAdmin(false);
		when(userRepository.findByEmail("name@pwd.it")).thenReturn(old);
		when(userRepository.save(any(User.class))).thenReturn(null);
		
		UserDto result = userServiceimpl.saveUser(uDto);
		assertTrue(usersEquals(null, result), "User not saved properly");		
	}
	
	@Test
	public void testSaveUser5(){
		//modify an existing user
		UserDto uDto = new UserDto(1, "name", "pwd", "name@pwd.it",0);
		uDto.setAdmin(false);
		User u = new User("name", "pwd", "name@pwd.it",0);
		u.setUserId(uDto.getUserId());
		u.setAdmin(false);//this is done by the converter, but by mocking .save() the effort of the converter is lost. DON'T KNOW HOW TO DO THIS 
		when(userRepository.findByEmail(anyString())).thenReturn(u);
		when(userRepository.save(any(User.class))).thenReturn(u);
		
		UserDto result = userServiceimpl.saveUser(uDto);
		assertTrue(usersEquals(u, result), "User not saved properly");		
	}

	
	@Test
	public void testGetAllUsers1(){
		//Retrieving the list of users
		
		List<User> users = new ArrayList<>();
		User u1 = new User("name1", "pwd1", "name1@pwd1.it",1);
		User u = new User("name", "pwd", "name@pwd.it",0);
		users.add(u);
		users.add(u1);
		
		when(userRepository.findAll()).thenReturn(users);

		List<UserDto> result = userServiceimpl.getAllUsers();
		assertTrue(userListEqual(users,result), "User not saved properly");		
	}
	
	@Test
	public void testGetAllUsers2(){
		//Retrieving the empty list of users
		
		List<User> users = new ArrayList<>();
		
		when(userRepository.findAll()).thenReturn(users);

		List<UserDto> result = userServiceimpl.getAllUsers();
		assertTrue(userListEqual(users,result), "User not saved properly");		
	}
	
	@Test
	public void testDeleteUser1(){
		//passing a invalid(null) Id, Exception expected
		when(userRepository.findOne(anyInt())).thenReturn(null);
		//is needed to mock the delete here?
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.deleteUser(null), 
				"Expected an InvalidUserException for a null id to be thrown");
	}
	
	@Test
	public void testDeleteUser2() {
		//passing an invalid id(negative), exception expected
		User u = new User("name", "pwd", "name@pwd.it",0);
		when(userRepository.findOne(anyInt())).thenReturn(u);
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.deleteUser(-1), 
				"Expected an InvalidUserException for a null id to be thrown");
	}
	
	
	@Test
	public void testDeleteUser3() throws InvalidUserException {
		//passing valid Id, checking for the result
		User u = new User("name", "pwd", "name@pwd.it",0);
		when(userRepository.findOne(anyInt())).thenReturn(u);
	
		assertTrue(userServiceimpl.deleteUser(1), "User not delelted correctly");
	}

	@Test
	public void testDeleteUser4() throws InvalidUserException  {
		//passing an invalid Id, id not in the database, Exception expected
		when(userRepository.findOne(anyInt())).thenReturn(null);
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.deleteUser(999), 
				"Expected an InvalidUserException for a null id to be thrown");		
	}
	
	@Test
	public void testLogin1() throws InvalidLoginDataException{	
		//should be successful, right credentials
		User u = new User("rightname", "rightpwd", "name@pwd.it",0);
		u.setUserId(1);
		u.setAdmin(false);
		IdPw credentials = new IdPw("rightname", "rightpwd");
		when(userRepository.findByEmail(anyString())).thenReturn(u);
		LoginDto result = userServiceimpl.login(credentials);
		assertTrue(validLogin(u,result), "Expected to be successful, credentials are right");
	
	}
	
	@Test
	public void testLogin2(){		
		//should fail, wrong password
		IdPw credentials = new IdPw("rightname", "wrongpwd");
		User u = new User("rightname", "rightpwd", "name@pwd.it",0);
		u.setUserId(1);
		u.setAdmin(false);
		when(userRepository.findByEmail(anyString())).thenReturn(u);
		assertThrows(exception.InvalidLoginDataException.class, () ->  userServiceimpl.login(credentials), 
				"Expected an InvalidLoginDataException for wrong password to be thrown");
	}
	
	@Test
	public void testLogin3(){		
		//should fail, wrong user name
		IdPw credentials = new IdPw("wrongname", "wrongpwd");
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		assertThrows(exception.InvalidLoginDataException.class, () ->  userServiceimpl.login(credentials), 
				"Expected an InvalidLoginDataException for null credentials to be thrown");
	}
	
	@Test
	public void testLogin4(){		
		//should fail, credentials null
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		assertThrows(exception.InvalidLoginDataException.class, () ->  userServiceimpl.login(null), 
				"Expected an InvalidLoginDataException for null credentials to be thrown");
	}
	
	@Test
	public void testincreaseUserReputation1() {
		//passing a null Id, Exception expected		 
		when(userRepository.findOne(anyInt())).thenReturn(null);
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.increaseUserReputation(null), 
				"Expected an InvalidUserException for a null id to be thrown");
	}
	
	@Test
	public void testincreaseUserReputation2(){
		//passing a negative Id, Exception expected
		when(userRepository.findOne(anyInt())).thenReturn(null);
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.getUserById(-1), 
				"Expected an InvalidUserException for a negative id to be thrown");
	}
	
	@Test
	public void testIncreaseUserReputation3() throws InvalidUserException {
		//passing valid Id, checking for the result
		User u = new User("name", "pwd", "name@pwd.it",2);
		u.setUserId(1);
		u.setAdmin(false);
		when(userRepository.findOne(anyInt())).thenReturn(u);
		
		Integer result = userServiceimpl.increaseUserReputation(1);
		assertTrue( u.getReputation() + 1 == result, "The reputation is not the one expected");		
	}

	@Test
	public void testIncreaseUserReputation4() throws InvalidUserException {
		//passing valid Id, checking for the result
		User u = new User("name", "pwd", "name@pwd.it",-2);
		u.setUserId(1);
		u.setAdmin(false);
		when(userRepository.findOne(anyInt())).thenReturn(u);
		
		Integer result = userServiceimpl.increaseUserReputation(1);
		assertTrue( -1 == result, "The reputation is not the one expected");		
	}
	
	@Test
	public void testIncreaseUserReputation5() throws InvalidUserException {
		//passing valid Id but the reputation is the highest possible(5), it should not change
		User u = new User("name", "pwd", "name@pwd.it",5);
		u.setUserId(1);
		u.setAdmin(false);
		when(userRepository.findOne(anyInt())).thenReturn(u);
		
		Integer result = userServiceimpl.increaseUserReputation(1);
		assertTrue( u.getReputation()  == result, "The reputation is not the one expected");		
	}
	
	@Test
	public void testIncreaseUserReputation6() {		
		//user doesn't exists
		when(userRepository.findOne(anyInt())).thenReturn(null);
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.decreaseUserReputation(999), 
				"Expected an InvalidUserException for an invalid id to be thrown");
	}
	
	@Test
	public 	void testDecreaseUserReputation1() {
		//passing a null Id, Exception expected		 
		when(userRepository.findOne(anyInt())).thenReturn(null);
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.decreaseUserReputation(null), 
				"Expected an InvalidUserException for a null id to be thrown");
	}
	
	@Test
	public void testDecreaseUserReputation2(){
		//passing a negative Id, Exception expected
		when(userRepository.findOne(anyInt())).thenReturn(null);
		
		assertThrows(exception.InvalidUserException.class, () ->  userServiceimpl.decreaseUserReputation(-1), 
				"Expected an InvalidUserException for a negative id to be thrown");
	}
	
	@Test
	public void testDecreaseUserReputation3() throws InvalidUserException {
		//passing valid Id, checking for the result
		User u = new User("name", "pwd", "name@pwd.it",2);
		u.setUserId(1);
		u.setAdmin(false);
		when(userRepository.findOne(anyInt())).thenReturn(u);
		
		Integer result = userServiceimpl.decreaseUserReputation(1);
		assertTrue( u.getReputation() - 1 == result, "The reputation is not the one expected");		
	}

	@Test
	public void testDecreaseUserReputation4() throws InvalidUserException {
		//passing valid Id, checking for the result
		User u = new User("name", "pwd", "name@pwd.it",-2);
		u.setUserId(1);
		u.setAdmin(false);
		when(userRepository.findOne(anyInt())).thenReturn(u);
		
		Integer result = userServiceimpl.decreaseUserReputation(1);
		assertTrue( u.getReputation() - 1 == result, "The reputation is not the one expected");		
	}
	
	@Test
	public void testDecreaseUserReputation5() throws InvalidUserException {
		//passing valid Id but the reputation is the lowest possible(-5), it should not change
		User u = new User("name", "pwd", "name@pwd.it",-5);
		u.setUserId(1);
		u.setAdmin(false);
		when(userRepository.findOne(anyInt())).thenReturn(u);
		
		Integer result = userServiceimpl.decreaseUserReputation(1);
		assertTrue( u.getReputation() == result, "The reputation is not the one expected");		
	}
	
	@Test
	public void testDecreaseUserReputation6() {		
		//user doesn't exists

		when(userRepository.findOne(anyInt())).thenReturn(null);
		
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

