package it.polito.ezgas;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import exception.GPSDataException;
import exception.InvalidGasStationException;
import exception.InvalidGasTypeException;
import exception.InvalidUserException;
import exception.PriceException;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.GasStationRepository;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.GasStationService;
import it.polito.ezgas.service.UserService;
import it.polito.ezgas.service.impl.GasStationServiceimpl;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EZGasGasStationServiceTest {
	
	GasStationServiceimpl gss;
	GasStationDto gsdto = mock(GasStationDto.class);

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GasStationService gasStationService;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private GasStationRepository gasStationRepository;
	
	@Before
	public void init() {
		gss = new GasStationServiceimpl();
	}
	
	
	@Test
	public void testGetAllGasStations() throws InvalidGasStationException {
		GasStation gs1 = new GasStation("gasStationName", "gasStationAddress", true, true, true, true, true, "carSharing", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4, "reportTimestamp", 1.0);
		GasStation gs2 = new GasStation("gasStationName2", "gasStationAddress2", true, true, true, true, true, "carSharing", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4, "reportTimestamp", 1.0);
		
		when(gasStationRepository.findAll()).thenReturn(Stream.of(gs1, gs2).collect(Collectors.toList()));
		//when(gasStationRepository.findOne(100)).thenReturn(gs1);
		
		assertEquals(2, gasStationService.getAllGasStations().size());
	}
	
	@Test
	public void testGetAllGasStations2() throws InvalidGasStationException {
		when(gasStationRepository.findAll()).thenReturn(null);
		assertThrows(NullPointerException.class, () ->  gasStationService.getAllGasStations().size(), 
				"Expected a NullPointerException for a null list to be thrown");
	}
	
	@Test
	public void testGetGasStationById() {
		when(gasStationRepository.findOne(anyInt())).thenReturn(null);
		
		assertThrows(InvalidGasStationException.class, () ->  gss.getGasStationById(null), 
				"Expected a NullPointerException for a null id to be thrown");
	}
	
	@Test
	public void testGetGasStationById2() throws InvalidGasStationException {
		GasStation gs1 = new GasStation("gasStationName", "gasStationAddress", true, true, true, true, true, "carSharing", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4, "reportTimestamp", 1.0);
		gs1.setGasStationId(100);
		when(gasStationRepository.findOne(anyInt())).thenReturn(gs1);
		
		GasStationDto result = gasStationService.getGasStationById(100);
		assertTrue(gasStationEquals(gs1, result), "Gas Station not the one expected");
	}
	
	@Test
	public void testSaveGasStation() throws InvalidGasStationException, PriceException, GPSDataException {
		//assert that all characteristics are equal separately
		GasStation gs1 = new GasStation("gasStationName", "gasStationAddress", true, true, true, true, true, "carSharing", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4, "reportTimestamp", 1.0);
		GasStationDto gsdto = new GasStationDto(1, "gasStationName", "gasStationAddress", true, true, true, true, true, "carSharing", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4, "reportTimestamp", 1.0);
		
		gs1.setGasStationId(gsdto.getGasStationId());
		
		when(gasStationRepository.save(gs1)).thenReturn(gs1);
		
		GasStationDto result = gasStationService.saveGasStation(gsdto);
		assertTrue(gasStationEquals(gs1, result), "Gas Station saved properly");	
		
	}
	
	@Test()
	public void testSaveGasStation2() {	
	    try {
	    	GasStation gs1 = null;
			gasStationService.saveGasStation(GasStationConverter.toGasStationDto(gs1));
	        fail("No NullPointerException");         
	     } 
	    catch (Exception e) {
	    	 e.printStackTrace();
	    }
	}
	
	@Test
	public void testDeleteGasStation(){
		//passing a invalid(null) Id, Exception expected
		when(userRepository.findOne(anyInt())).thenReturn(null);
		//is needed to mock the delete here?
		
		assertThrows(InvalidGasStationException.class, () ->  gasStationService.deleteGasStation(null), 
				"Expected an InvalidGasStationException for a null id to be thrown");
	}
	
	@Test
	public void testDeleteGasStation2() {
		GasStation gs1 = new GasStation("gasStationName", "gasStationAddress", true, true, true, true, true, "carSharing", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4, "reportTimestamp", 1.0);
		when(gasStationRepository.findOne(anyInt())).thenReturn(gs1);
		
		assertThrows(InvalidGasStationException.class, () ->  gasStationService.deleteGasStation(gs1.getGasStationId()), 
				"Expected an InvalidUserException for a null id to be thrown");
	}

	@Test
	public void testGetGasStationsByGasolineType() throws InvalidGasStationException, InvalidGasTypeException {
		GasStation gs1 = new GasStation("gasStationName", "gasStationAddress", true, true, true, true, true, "carSharing", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4, "reportTimestamp", 1.0);
		GasStation gs2 = new GasStation("gasStationName2", "gasStationAddress2", true, true, true, true, true, "carSharing", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4, "reportTimestamp", 1.0);
		
		when(gasStationRepository.findAll()).thenReturn(Stream.of(gs1, gs2).collect(Collectors.toList()));
		//when(gasStationRepository.findOne(100)).thenReturn(gs1);
		
		assertEquals(gasStationRepository.findAll().size() ,gasStationService.getGasStationsByGasolineType("diesel").size());
	}
	
	@Test
	public void testGetGasStationsByGasolineType2() throws InvalidGasStationException, InvalidGasTypeException {
		GasStation gs1 = null;
		GasStation gs2 = null;
		
		when(gasStationRepository.findAll()).thenReturn(Stream.of(gs1, gs2).collect(Collectors.toList()));
		assertThrows(NullPointerException.class, () ->  gasStationService.getGasStationsByGasolineType("diesel"), 
				"Expected an NullPointerException for a null id to be thrown");
	}

	@Test
	public void testGetGasStationByProximity() {
		when(gasStationRepository.findAll()).thenReturn(null);
		
		assertThrows(NullPointerException.class, () ->  gasStationService.getGasStationsByProximity(0, 0), 
				"Expected a NullPointerException for a null id to be thrown");
	}
	
	@Test
	public void testGetGasStationByProximity2() throws InvalidGasStationException, GPSDataException {
		GasStation gs1 = new GasStation("gasStationName", "gasStationAddress", true, true, true, true, true, "carSharing", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4, "reportTimestamp", 1.0);
		when(gasStationRepository.findAll()).thenReturn(Stream.of(gs1).collect(Collectors.toList()));
		
		assertTrue(gasStationEquals(gs1, gasStationService.getGasStationsByProximity(gs1.getLon(), gs1.getLat()).get(0)), "GasStation not the one expected");
	}

	@Test
	public void testGetGasStationsWithCoordinates() {
		when(gasStationRepository.findAll()).thenReturn(null);
		
		assertThrows(NullPointerException.class, () ->  gss.getGasStationsWithCoordinates(0, 0, "diesel", "carsharing"), 
				"Expected a NullPointerException for a null id to be thrown");
	}
	@Test
	public void testGetGasStationsWithCoordinates2() throws InvalidGasTypeException, GPSDataException {
		GasStation gs1 = new GasStation("gasStationName", "gasStationAddress", true, true, true, true, true, "carSharing", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4, "reportTimestamp", 1.0);
		GasStation gs2 = new GasStation("gasStationName2", "gasStationAddress2", true, true, true, true, true, "carSharing", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4, "reportTimestamp", 1.0);

		when(gasStationRepository.findAll()).thenReturn(Stream.of(gs1, gs2).collect(Collectors.toList()));
		assertEquals(0, gasStationService.getGasStationsWithCoordinates(0, 0, "diesel", "carsharing").size());
	}
	
	@Test
	public void testGetGasStationsWithoutCoordinates() {
		when(gasStationRepository.findAll()).thenReturn(null);
		
		assertThrows(NullPointerException.class, () ->  gasStationService.getGasStationsWithoutCoordinates("diesel", "carsharing"), 
				"Expected a NullPointerException for a null id to be thrown");
	}
	@Test
	public void testGetGasStationsWithoutCoordinates2() throws InvalidGasTypeException {
		GasStation gs1 = new GasStation("gasStationName", "gasStationAddress", true, true, true, true, true, "carSharing", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4, "reportTimestamp", 1.0);

		when(gasStationRepository.findAll()).thenReturn(Stream.of(gs1).collect(Collectors.toList()));
		assertEquals(0, gasStationService.getGasStationsWithoutCoordinates("diesel", "carsharing").size());
	}
	
	@Test
	public void testGetGasStationByCarSharing() {
		when(gasStationRepository.findAll()).thenReturn(null);
		
		assertThrows(NullPointerException.class, () ->  gasStationService.getGasStationByCarSharing("carSharing"), 
				"Expected a NullPointerException for a null id to be thrown");
	}
	@Test
	public void testGetGasStationByCarSharing2() throws InvalidGasTypeException {
		GasStation gs1 = new GasStation("gasStationName", "gasStationAddress", true, true, true, true, true, "carSharing", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4, "reportTimestamp", 1.0);

		when(gasStationRepository.findAll()).thenReturn(Stream.of(gs1).collect(Collectors.toList()));
		assertTrue(gasStationEquals(gs1,gasStationService.getGasStationByCarSharing("carSharing").get(0)), "GasStation not the one expected");
	}
	
	
	@Test
	public void testSetReport() {
		when(gasStationRepository.findAll()).thenReturn(null);
		assertThrows(InvalidGasStationException.class, () ->  gss.setReport(null, 0, 0, 0, 0, 0, null),
				"Expected a InvalidGasStationException for a null id to be thrown");
	}
	
	/*
	@Test
	public void testSetReport2() throws InvalidGasStationException, PriceException, InvalidUserException {
		GasStation gs1 = new GasStation("gasStationName", "gasStationAddress", true, true, true, true, true, "carSharing", 1.6, 1.4, 1.0, 1.4, 2.6, 2.4, 3, 4, "reportTimestamp", 1.0);
		User u = new User("name", "pwd", "name@pwd.it",5);
		u.setUserId(1);
		gs1.setGasStationId(100);
		when(gasStationRepository.save(gs1)).thenReturn(gs1);
		when(userRepository.save(u)).thenReturn(u);
		
		gasStationService.setReport(gs1.getGasStationId(), 4, 5, 3, 4, 3, u.getUserId());
		
		assertTrue(gasStationService.getGasStationById(100).getDieselPrice() == 4);
	}*/
	
	
	@Test
	public void testEvaluateDependability1() {
		try {
			double result = gss.evaluateDependability(null, "25658965"); //error null
			assertEquals("The method should fail because the reputation is null", -1.0, result, 0.0);
		} catch(Exception e) {
			fail("User reputation is null but the method should not throw any exceptions");
		}
	}
	
	@Test
	public void testEvaluateDependability2() {
		double result = gss.evaluateDependability(-6, "25658965"); //error <-5
		assertEquals("The method should fail because the reputation is less than -5", -1.0, result, 0.0);
	}
	
	@Test
	public void testEvaluateDependability3() {
		try {
			double result = gss.evaluateDependability(-3, null); //error timestamp null
			assertEquals("The method should fail because the timestamp is null", -1.0, result, 0.0);
		} catch(Exception e) {
			assertTrue("Timestamp is null but the method should not throw any exceptions", false);
		}
	}
	
	@Test
	public void testEvaluateDependability4() {
		try {
			double result = gss.evaluateDependability(-3, "ad2d65"); //error timestamp not num
			assertEquals("The method should fail because the timestamp is non a number", -1.0, result, 0.0);
		} catch(Exception e) {
			fail("Timestamp is not a number but the method should not throw any exceptions");
		}
	}
	
	@Test
	public void testEvaluateDependability5() {
		double result = gss.evaluateDependability(-3, "-25658965"); //error timestamp negative
		assertEquals("The method should fail because the timestamp is negative", -1.0, result, 0.0);
	}

	@Test
	public void testEvaluateDependability6() {
		try {
			double result = gss.evaluateDependability(-3, "999999999999999999999999"); //error positive but too big
			assertEquals("The method should fail because the timestamp is too big", -1.0, result, 0.0);
		} catch(Exception e) {
			fail("Timestamp is too big but the method should not throw any exceptions");
		}
		
	}

	@Test
	public void testEvaluateDependability7() {
		long tomorrow = System.currentTimeMillis() + 1*25*3600*1000;
		double result = gss.evaluateDependability(-1, Long.toString(tomorrow)); //error timestamp is in future
		assertEquals("The method should fail because the timestamp refers to the future", -1.0, result, 0.0);
	}

	@Test
	public void testEvaluateDependability8() {
		long sevenDaysEarlier = System.currentTimeMillis() - 7*25*3600*1000;
		double obsolescence = 1.0 - 7.0/7.0;
		double expected = 50.0*(-5.0+5.0)/10.0 + 50.0 * obsolescence;
		double result = gss.evaluateDependability(-5, Long.toString(sevenDaysEarlier));
		assertEquals(expected, result, 0.01);
	}

	@Test
	public void testEvaluateDependability9() {
		long today = System.currentTimeMillis();
		double obsolescence = 1.0 - 0/7.0;
		double expected = 50.0*(-3.0+5.0)/10.0 + 50.0 * obsolescence;
		double result = gss.evaluateDependability(-3, Long.toString(today));
		assertEquals(expected, result, 0.01);
	}
	
	@Test
	public void testEvaluateDependability10() {
		long eightDaysEarlier = System.currentTimeMillis() - 8*25*3600*1000;
		double obsolescence = 0;
		double expected = 50.0*(-3.0+5.0)/10.0 + 50.0 * obsolescence;
		double result = gss.evaluateDependability(-3, Long.toString(eightDaysEarlier));
		assertEquals(expected, result, 0.01);
	}
	
	@Test
	public void testEvaluateDependability11() {
		double result = gss.evaluateDependability(+6, "25658965"); //error >5
		assertEquals("The method should fail because the reputation is greater than 5", -1.0, result, 0.0);
	}
	
	
	@Test
	public void testEvaluateDependability12() {
		try {
			double result = gss.evaluateDependability(+4, null); //error timestamp null
			assertEquals("The method should fail because the timestamp is null", -1.0, result, 0.0);
		} catch(Exception e) {
			fail("Timestamp is null but the method should not throw any exceptions");
		}
	}
	
	@Test
	public void testEvaluateDependability13() {
		try {
			double result = gss.evaluateDependability(+2, "ad2d65"); //error timestamp not num
			assertEquals("The method should fail because the timestamp is non a number", -1.0, result, 0.0);
		} catch(Exception e) {
			fail("Timestamp is not a number but the method should not throw any exceptions");
		}
	}
	
	@Test
	public void testEvaluateDependability14() {
		double result = gss.evaluateDependability(+5, "-1"); //error timestamp negative
		assertEquals("The method should fail because the timestamp is negative", -1.0, result, 0.0);
	}

	@Test
	public void testEvaluateDependability15() {
		try {
			double result = gss.evaluateDependability(+3, "999999999999999999999999"); //error positive but too big
			assertEquals("The method should fail because the timestamp is too big", -1.0, result, 0.0);
		} catch(Exception e) {
			fail("Timestamp is too big but the method should not throw any exceptions");
		}
	}

	@Test
	public void testEvaluateDependability16() {
		long tomorrow = System.currentTimeMillis() + 1*25*3600*1000;
		double result = gss.evaluateDependability(+5, Long.toString(tomorrow)); //error timestamp is future time
		assertEquals("The method should fail because the timestamp refers to the future", -1.0, result, 0.0);
	}

	@Test
	public void testEvaluateDependability17() {
		long sevenDaysEarlier = System.currentTimeMillis() - 7*25*3600*1000;
		double obsolescence = 1.0 - 7.0/7.0;
		double expected = 50.0*(+5.0+5.0)/10.0 + 50.0 * obsolescence;
		double result = gss.evaluateDependability(+5, Long.toString(sevenDaysEarlier));
		assertEquals(expected, result, 0.01);
	}

	@Test
	public void testEvaluateDependability18() {
		long today = System.currentTimeMillis();
		double obsolescence = 1.0 - 0/7.0;
		double expected = 50.0*(+3.0+5.0)/10.0 + 50.0 * obsolescence;
		double result = gss.evaluateDependability(+3, Long.toString(today));
		assertEquals(expected, result, 0.01);
	}
	
	@Test
	public void testEvaluateDependability19() {
		long eightDaysEarlier = System.currentTimeMillis() - 8*25*3600*1000;
		double obsolescence = 0;
		double expected = 50.0*(+4.0+5.0)/10.0 + 50.0 * obsolescence;
		double result = gss.evaluateDependability(+4, Long.toString(eightDaysEarlier));
		assertEquals(expected, result, 0.01);
	}
	
	@Test
	public void testEvaluateDependability20() {
		try {
			double result = gss.evaluateDependability(-3, "-999999999999999999999999"); //error negative but too big
			assertEquals("The method should fail because the timestamp is too big", -1.0, result, 0.0);
		} catch(Exception e) {
			fail("Timestamp is too big but the method should not throw any exceptions");
		}
	}
	
	@Test
	public void testEvaluateDependability21() {
		try {
			double result = gss.evaluateDependability(+3, "-999999999999999999999999"); //error negative but too big
			assertEquals("The method should fail because the timestamp is too big", -1.0, result, 0.0);
		} catch(Exception e) {
			fail("Timestamp is too big but the method should not throw any exceptions");
		}
	}
	
	@Test
	public void testEvaluateDependability22() {
		long sixDaysEarlier = System.currentTimeMillis() - 6*25*3600*1000;
		double obsolescence = 1.0 - 6.0/7.0;
		double expected = 50.0*(-5.0+5.0)/10.0 + 50.0 * obsolescence;
		double result = gss.evaluateDependability(-5, Long.toString(sixDaysEarlier));
		assertEquals(expected, result, 0.01);
	}
	
	@Test
	public void testEvaluateDependability23() {
		System.out.println("This is the test");
		long sixDaysEarlier = System.currentTimeMillis() - 6*25*3600*1000;
		double obsolescence = 1.0 - 6.0/7.0;
		double expected = 50.0*(+5.0+5.0)/10.0 + 50.0 * obsolescence;
		double result = gss.evaluateDependability(+5, Long.toString(sixDaysEarlier));
		assertEquals(expected, result, 0.01);
	}

	private Boolean gasStationEquals(GasStation gs, GasStationDto result) {
		
		if(gs == null && result == null)
			return true;
		
		if(gs.getHasDiesel() == result.getHasDiesel() && gs.getHasGas() == result.getHasGas() && 
				gs.getHasMethane() == result.getHasMethane() && gs.getHasSuper() == result.getHasSuper() &&
				gs.getHasSuperPlus() == result.getHasSuperPlus() && gs.getCarSharing() == result.getCarSharing()&& 
				gs.getDieselPrice() == result.getDieselPrice() && gs.getGasPrice() == result.getGasPrice() &&
				gs.getGasStationAddress() == result.getGasStationAddress() && gs.getGasStationId() == result.getGasStationId())
			return true;
		return false;
	}
}
