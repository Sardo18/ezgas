package it.polito.ezgas;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.repository.GasStationRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GasStationRepositoryTest {
	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GasStationRepository repository;

    private GasStation g1;
    private GasStation g2;
    
    @Before
    public void init() {
    	g1 = new GasStation();
    	g1.setGasStationName("GasStation1");
    	g1.setLat(100.87);
    	g1.setLon(55.27);
    	

    	g2 = new GasStation();
    	g2.setGasStationName("GasStation2");
    	g2.setLat(87.100);
    	g2.setLon(27.55);
    	
    	entityManager.persist(g1);
    	entityManager.persist(g2);
    }
    
    @After
    public void clear() {
    	entityManager.remove(g1);
    	entityManager.remove(g2);
    }
    
    @Test
    public void testFindByLatAndLonPresent() {
    	GasStation g = repository.findByLatAndLon(g1.getLat(), g1.getLon());
    	assertNotNull(g);
    	assertEquals(g1.getGasStationName(), g.getGasStationName());
    	assertEquals(g1.getLat(), g.getLat(), 0.0);
    	assertEquals(g1.getLon(), g.getLon(), 0.0);
    }
    
    @Test
    public void testFindByLatAndLonNotPresent() {
    	GasStation g = repository.findByLatAndLon(101.5, 11.25);
    	assertNull(g);
    }
}
