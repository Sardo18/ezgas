package it.polito.ezgas;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;
    
    private User user1;
    private User user2;
    
    @Before
    public void init() {
    	user1 = new User("Admin1", "fakePassw0rd", "fake@email.com", 5);
    	user1.setAdmin(true);
    	
    	user2 = new User("User2", "Passw0rdFake!", "ake12@email.com", -1);
    	user2.setAdmin(false);
		
    	entityManager.persist(user1);
    	entityManager.persist(user2);
    }
    
    @After
    public void clear() {
    	entityManager.remove(user1);
    	entityManager.remove(user2);
    }
    
    @Test
    public void testFindByEmailPresent() {

    	User u = repository.findByEmail(user1.getEmail());
    	assertNotNull(u);
    	User user = u;
    	assertEquals("The method does not return the right user. Username assertion failed", user1.getUserName(), user.getUserName());
    	assertEquals("The method does not return the right user. Password assertion failed", user1.getPassword(), user.getPassword());
    	assertEquals("The method does not return the right user. Email assertion failed", user1.getEmail(), user.getEmail());
    	assertEquals("The method does not return the right user. Reputation assertion failed", user1.getReputation(), user.getReputation());
    }
    
    @Test
    public void testFindByEmailNotPresent() {
    	User u = repository.findByEmail("wrong@email.com");
    	assertNull(u);
    }
}
