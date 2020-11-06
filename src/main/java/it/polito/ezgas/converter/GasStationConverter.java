package it.polito.ezgas.converter;

import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;



public class GasStationConverter{
	
	public static GasStationDto toGasStationDto(GasStation gasStation) {
		
	
		if(gasStation == null) {
			return null;
		}
	
		return new GasStationDto(gasStation.getGasStationId(),gasStation.getGasStationName(), gasStation.getGasStationAddress(),
				gasStation.getHasDiesel(), gasStation.getHasSuper(), gasStation.getHasSuperPlus(), gasStation.getHasGas(), gasStation.getHasMethane(),
				gasStation.getCarSharing(), gasStation.getLat(), gasStation.getLon(), gasStation.getDieselPrice(), gasStation.getSuperPrice(), 
				gasStation.getSuperPlusPrice(), gasStation.getGasPrice(), gasStation.getMethanePrice(), gasStation.getReportUser(), gasStation.getReportTimestamp(),
				gasStation.getReportDependability());
	}
	
	public static GasStation toGasStation(GasStationDto gasStationDto) {
		
		
		if(gasStationDto == null) {
			return null;
		}
		
		GasStation g = new GasStation(gasStationDto.getGasStationName(), gasStationDto.getGasStationAddress(),
				gasStationDto.getHasDiesel(), gasStationDto.getHasSuper(), gasStationDto.getHasSuperPlus(), gasStationDto.getHasGas(), gasStationDto.getHasMethane(),
				gasStationDto.getCarSharing(), gasStationDto.getLat(), gasStationDto.getLon(), gasStationDto.getDieselPrice(), gasStationDto.getSuperPrice(), 
				gasStationDto.getSuperPlusPrice(), gasStationDto.getGasPrice(), gasStationDto.getMethanePrice(), gasStationDto.getReportUser(), gasStationDto.getReportTimestamp(),
				gasStationDto.getReportDependability());
		
		g.setGasStationId(gasStationDto.getGasStationId());
		
	
		
		return g;
		
	}
}