package it.polito.ezgas;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;

public class GasStationConverterTest {


	// black box tests
	
	@Test
	public void testToGasStationDto() {
		
		GasStation gs = mock(GasStation.class);
		GasStationDto gsdto = null;
		
		when(gs.getGasStationId()).thenReturn(100);
		when(gs.getCarSharing()).thenReturn("car sharing");
		when(gs.getGasStationName()).thenReturn("GS name");
		when(gs.getGasStationAddress()).thenReturn("Addr");
		when(gs.getLat()).thenReturn(1000.21);
		when(gs.getLon()).thenReturn(25.49);
		when(gs.getDieselPrice()).thenReturn(1.15);
		when(gs.getHasDiesel()).thenReturn(true);
		when(gs.getSuperPrice()).thenReturn(1.22);
		when(gs.getHasSuper()).thenReturn(true);
		when(gs.getMethanePrice()).thenReturn(.0);
		when(gs.getHasMethane()).thenReturn(false);
		when(gs.getSuperPlusPrice()).thenReturn(.0);
		when(gs.getHasSuperPlus()).thenReturn(false);
		when(gs.getGasPrice()).thenReturn(.96);
		when(gs.getHasGas()).thenReturn(true);
		when(gs.getReportUser()).thenReturn(1);
		when(gs.getReportTimestamp()).thenReturn("3256544");
		when(gs.getReportDependability()).thenReturn(57.5);
		
		gsdto = GasStationConverter.toGasStationDto(gs);

		assertEquals(gs.getGasStationId(), gsdto.getGasStationId());
		assertEquals(gs.getGasStationName(), gsdto.getGasStationName());
		assertEquals(gs.getGasStationAddress(), gsdto.getGasStationAddress());
		assertEquals(gs.getCarSharing(), gsdto.getCarSharing());
		assertEquals(gs.getLat(), gsdto.getLat(), .0);
		assertEquals(gs.getLon(), gsdto.getLon(), .0);
		assertEquals(gs.getDieselPrice(), gsdto.getDieselPrice(), .0);
		assertEquals(gs.getHasDiesel(), gsdto.getHasDiesel());
		assertEquals(gs.getSuperPrice(), gsdto.getSuperPrice(), .0);
		assertEquals(gs.getHasSuper(), gsdto.getHasSuper());
		assertEquals(gs.getMethanePrice(), gsdto.getMethanePrice(), .0);
		assertEquals(gs.getHasMethane(), gsdto.getHasMethane());
		assertEquals(gs.getSuperPlusPrice(), gsdto.getSuperPlusPrice(), .0);
		assertEquals(gs.getHasSuperPlus(), gsdto.getHasSuperPlus());
		assertEquals(gs.getGasPrice(), gsdto.getGasPrice(), .0);
		assertEquals(gs.getHasGas(), gsdto.getHasGas());
		assertEquals(gs.getReportUser(), gsdto.getReportUser());
		assertEquals(gs.getReportTimestamp(), gsdto.getReportTimestamp());
		assertEquals(gs.getReportDependability(), gsdto.getReportDependability(), .0);
	}
	
	@Test
	public void testToGasStation() {
		
		GasStationDto gsdto = mock(GasStationDto.class);
		GasStation gs = null;
		
		when(gsdto.getGasStationId()).thenReturn(100);
		when(gsdto.getCarSharing()).thenReturn("car sharing");
		when(gsdto.getGasStationName()).thenReturn("GS name");
		when(gsdto.getGasStationAddress()).thenReturn("Addr");
		when(gsdto.getLat()).thenReturn(1000.21);
		when(gsdto.getLon()).thenReturn(25.49);
		when(gsdto.getDieselPrice()).thenReturn(1.15);
		when(gsdto.getHasDiesel()).thenReturn(true);
		when(gsdto.getSuperPrice()).thenReturn(1.22);
		when(gsdto.getHasSuper()).thenReturn(true);
		when(gsdto.getMethanePrice()).thenReturn(.0);
		when(gsdto.getHasMethane()).thenReturn(false);
		when(gsdto.getSuperPlusPrice()).thenReturn(.0);
		when(gsdto.getHasSuperPlus()).thenReturn(false);
		when(gsdto.getGasPrice()).thenReturn(.96);
		when(gsdto.getHasGas()).thenReturn(true);
		when(gsdto.getReportUser()).thenReturn(1);
		when(gsdto.getReportTimestamp()).thenReturn("3256544");
		when(gsdto.getReportDependability()).thenReturn(57.5);
		
		gs = GasStationConverter.toGasStation(gsdto);
		
		assertEquals(gsdto.getGasStationId(), gs.getGasStationId());
		assertEquals(gsdto.getGasStationName(), gs.getGasStationName());
		assertEquals(gsdto.getGasStationAddress(), gs.getGasStationAddress());
		assertEquals(gsdto.getCarSharing(), gs.getCarSharing());
		assertEquals(gsdto.getLat(), gs.getLat(), .0);
		assertEquals(gsdto.getLon(), gs.getLon(), .0);
		assertEquals(gsdto.getDieselPrice(), gs.getDieselPrice(), .0);
		assertEquals(gsdto.getHasDiesel(), gs.getHasDiesel());
		assertEquals(gsdto.getSuperPrice(), gs.getSuperPrice(), .0);
		assertEquals(gsdto.getHasSuper(), gs.getHasSuper());
		assertEquals(gsdto.getMethanePrice(), gs.getMethanePrice(), .0);
		assertEquals(gsdto.getHasMethane(), gs.getHasMethane());
		assertEquals(gsdto.getSuperPlusPrice(), gs.getSuperPlusPrice(), .0);
		assertEquals(gsdto.getHasSuperPlus(), gs.getHasSuperPlus());
		assertEquals(gsdto.getGasPrice(), gs.getGasPrice(), .0);
		assertEquals(gsdto.getHasGas(), gs.getHasGas());
		assertEquals(gsdto.getReportUser(), gs.getReportUser());
		assertEquals(gsdto.getReportTimestamp(), gs.getReportTimestamp());
		assertEquals(gsdto.getReportDependability(), gs.getReportDependability(), .0);	
	}
	
	// white box tests
	
	@Test
	public void testToGasStationDto1() {
		
		GasStation gs = null;
		GasStationDto gsdto = null;
		
		gsdto = GasStationConverter.toGasStationDto(gs);
		assertNull(gsdto);	
	}
	
	@Test
	public void testToGasStation1() {
		
		GasStationDto gsdto = null;
		GasStation gs = null;
		
		gs = GasStationConverter.toGasStation(gsdto);
		
		assertNull(gs);		
	}
	
	// integration tests
	
	@Test
	public void testToGasStationDto2() {
		
		GasStationDto gsdto = null;
		GasStation gs = new GasStation("GS name", "Addr", 
									   true, true, false, true, false, 
									   "car sharing", 1000.21, 25.49, 
									   1.15, 1.22, .0, .96, .0,
									   1, "3256544", 57.5);
		gs.setGasStationId(100);
		
		
		gsdto = GasStationConverter.toGasStationDto(gs);
		
		assertEquals(gs.getGasStationId(), gsdto.getGasStationId());
		assertEquals(gs.getGasStationName(), gsdto.getGasStationName());
		assertEquals(gs.getGasStationAddress(), gsdto.getGasStationAddress());
		assertEquals(gs.getCarSharing(), gsdto.getCarSharing());
		assertEquals(gs.getLat(), gsdto.getLat(), .0);
		assertEquals(gs.getLon(), gsdto.getLon(), .0);
		assertEquals(gs.getDieselPrice(), gsdto.getDieselPrice(), .0);
		assertEquals(gs.getHasDiesel(), gsdto.getHasDiesel());
		assertEquals(gs.getSuperPrice(), gsdto.getSuperPrice(), .0);
		assertEquals(gs.getHasSuper(), gsdto.getHasSuper());
		assertEquals(gs.getMethanePrice(), gsdto.getMethanePrice(), .0);
		assertEquals(gs.getHasMethane(), gsdto.getHasMethane());
		assertEquals(gs.getSuperPlusPrice(), gsdto.getSuperPlusPrice(), .0);
		assertEquals(gs.getHasSuperPlus(), gsdto.getHasSuperPlus());
		assertEquals(gs.getGasPrice(), gsdto.getGasPrice(), .0);
		assertEquals(gs.getHasGas(), gsdto.getHasGas());
		assertEquals(gs.getReportUser(), gsdto.getReportUser());
		assertEquals(gs.getReportTimestamp(), gsdto.getReportTimestamp());
		assertEquals(gs.getReportDependability(), gsdto.getReportDependability(), .0);
	}
	
	@Test
	public void testToGasStation2() {
		
		GasStation gs = null;
		GasStationDto gsdto = new GasStationDto(100, "GS name", "Addr", 
				   								true, true, false, true, false, 
				   								"car sharing", 1000.21, 25.49, 
				   								1.15, 1.22, .0, .96, .0,
				   								1, "3256544", 57.5);
		
		gs = GasStationConverter.toGasStation(gsdto);
		
		assertEquals(gsdto.getGasStationId(), gs.getGasStationId());
		assertEquals(gsdto.getGasStationName(), gs.getGasStationName());
		assertEquals(gsdto.getGasStationAddress(), gs.getGasStationAddress());
		assertEquals(gsdto.getCarSharing(), gs.getCarSharing());
		assertEquals(gsdto.getLat(), gs.getLat(), .0);
		assertEquals(gsdto.getLon(), gs.getLon(), .0);
		assertEquals(gsdto.getDieselPrice(), gs.getDieselPrice(), .0);
		assertEquals(gsdto.getHasDiesel(), gs.getHasDiesel());
		assertEquals(gsdto.getSuperPrice(), gs.getSuperPrice(), .0);
		assertEquals(gsdto.getHasSuper(), gs.getHasSuper());
		assertEquals(gsdto.getMethanePrice(), gs.getMethanePrice(), .0);
		assertEquals(gsdto.getHasMethane(), gs.getHasMethane());
		assertEquals(gsdto.getSuperPlusPrice(), gs.getSuperPlusPrice(), .0);
		assertEquals(gsdto.getHasSuperPlus(), gs.getHasSuperPlus());
		assertEquals(gsdto.getGasPrice(), gs.getGasPrice(), .0);
		assertEquals(gsdto.getHasGas(), gs.getHasGas());
		assertEquals(gsdto.getReportUser(), gs.getReportUser());
		assertEquals(gsdto.getReportTimestamp(), gs.getReportTimestamp());
		assertEquals(gsdto.getReportDependability(), gs.getReportDependability(), .0);	
	}
}
