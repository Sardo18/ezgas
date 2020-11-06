package it.polito.ezgas.service.impl;

import java.time.DateTimeException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import exception.GPSDataException;
import exception.InvalidGasStationException;
import exception.InvalidGasTypeException;
import exception.InvalidUserException;
import exception.PriceException;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.service.GasStationService;
import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.repository.GasStationRepository;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.UserService;




/**
 * Created by softeng on 27/4/2020.
 */
@Service
public class GasStationServiceimpl implements GasStationService {
	
	@Autowired
		UserService userService;
	@Autowired
		UserRepository userRepository;
	@Autowired
		GasStationRepository gasStationRepository;

	@Override
	public GasStationDto getGasStationById(Integer gasStationId) throws InvalidGasStationException {
		if(gasStationId == null || gasStationId <= 0) {
			throw new InvalidGasStationException("Invalid GasStation Id");		
		}
				
		return GasStationConverter.toGasStationDto(gasStationRepository.findOne(gasStationId));
	}

	@Override
	public GasStationDto saveGasStation(GasStationDto gasStationDto) throws PriceException, GPSDataException {
		
		//We check the prices only if the gas station is already present 
		//(so if the id is not null and if you can find a gas station with the given ID) 
		if(gasStationDto.getGasStationId() != null && gasStationRepository.findOne(gasStationDto.getGasStationId()) != null) {

			if((gasStationDto.getHasDiesel() && gasStationDto.getDieselPrice() <= 0) || 
					(gasStationDto.getHasSuper() && gasStationDto.getSuperPrice() <= 0) ||
					(gasStationDto.getHasSuperPlus() && gasStationDto.getSuperPlusPrice() <= 0) || 
					(gasStationDto.getHasGas() && gasStationDto.getGasPrice() <= 0 ) ||
					(gasStationDto.getHasMethane() && gasStationDto.getMethanePrice() <=0)) {
				 throw new PriceException("Invalid Prices");
			}
		}
		
		if( gasStationDto.getLon() <= -180 || gasStationDto.getLon() > 180 || gasStationDto.getLat() <= -90 || gasStationDto.getLat() > 90) {
			throw new GPSDataException("Invalid coordinates");
		}	
		
		
		GasStation gs = GasStationConverter.toGasStation(gasStationDto);
		gasStationRepository.save(gs);
		
		return gasStationDto;
	}

	@Override
	public List<GasStationDto> getAllGasStations() {
		
		return 	gasStationRepository.findAll()
				.stream()
				.map( g -> GasStationConverter.toGasStationDto(g))
				.collect(Collectors.toList());
	}

	@Override
	public Boolean deleteGasStation(Integer gasStationId) throws InvalidGasStationException {				
		
		if(getGasStationById(gasStationId) != null) {
			gasStationRepository.delete(gasStationId);
			return true;
		}
		
		return false;
	}

	@Override
	public List<GasStationDto> getGasStationsByGasolineType(String gasolinetype) throws InvalidGasTypeException {
		
		if(gasolinetype.equalsIgnoreCase("null")) {
			throw new InvalidGasTypeException("Invalid GasType");			
		} 		
		if(!gasolinetype.equalsIgnoreCase("diesel") && !gasolinetype.equalsIgnoreCase("super") && !gasolinetype.equalsIgnoreCase("superPlus") &&
				!gasolinetype.equalsIgnoreCase("gas") && !gasolinetype.equalsIgnoreCase("methane")) {
			
			throw new InvalidGasTypeException("Invalid GasType");	
		}	
		
	
		if(gasolinetype.equalsIgnoreCase("diesel")) {
			
			return gasStationRepository.findAll()
					.stream()
					.filter(g -> g.getHasDiesel())
					.map(g -> GasStationConverter.toGasStationDto(g))
					.sorted((g1,g2)-> (int) (g1.getDieselPrice() - g2.getDieselPrice()))
					.collect(Collectors.toList());
		}
		
		if(gasolinetype.equalsIgnoreCase("super")) {
			
			return gasStationRepository.findAll()
					.stream()
					.filter(g -> g.getHasSuper())
					.map(g -> GasStationConverter.toGasStationDto(g))
					.sorted((g1,g2)-> (int) (g1.getSuperPrice() - g2.getSuperPrice()))
					.collect(Collectors.toList());
		}
		
		if(gasolinetype.equalsIgnoreCase("superplus")) {
			return gasStationRepository.findAll()
					.stream()
					.filter(g -> g.getHasSuperPlus())
					.map(g -> GasStationConverter.toGasStationDto(g))
					.sorted((g1,g2)-> (int) (g1.getSuperPlusPrice() - g2.getSuperPlusPrice()))
					.collect(Collectors.toList());
		}
		
		if(gasolinetype.equalsIgnoreCase("gas")) {
			
			return gasStationRepository.findAll()
					.stream()
					.filter(g -> g.getHasGas())
					.map(g -> GasStationConverter.toGasStationDto(g))
					.sorted((g1,g2)-> (int) (g1.getGasPrice() - g2.getGasPrice()))
					.collect(Collectors.toList());
		}
		
		if(gasolinetype.equalsIgnoreCase("methane")) {
			
			return gasStationRepository.findAll()
					.stream()
					.filter(g -> g.getHasMethane())
					.map(g -> GasStationConverter.toGasStationDto(g))
					.sorted((g1,g2)-> (int) (g1.getMethanePrice() - g2.getMethanePrice()))
					.collect(Collectors.toList());
		}
		
		return null;
	}

	@Override
	public List<GasStationDto> getGasStationsByProximity(double lat, double lon) throws GPSDataException {
		// TODO Auto-generated method stub
		
		if( lon <= -180 || lon > 180 || lat <= -90 || lat > 90) {
			throw new GPSDataException("Invalid coordinates");
		}	

		return 	gasStationRepository.findAll()
				.stream()
				.filter( g -> distanceLessThanOneKm(g, lat , lon))
				.map( g -> GasStationConverter.toGasStationDto(g))
				.collect(Collectors.toList());
		
	}


	@Override
	public List<GasStationDto> getGasStationsWithCoordinates(double lat, double lon, String gasolinetype,
			String carsharing) throws InvalidGasTypeException, GPSDataException {
		
		if( lon <= -180 || lon > 180 || lat <= -90 || lat > 90) {
			throw new GPSDataException("Invalid coordinates");
		}	
		
		return getGasStationsWithoutCoordinates(gasolinetype, carsharing)
					.stream()
					.filter( g -> distanceLessThanOneKm(GasStationConverter.toGasStation(g), lat, lon))
					.collect(Collectors.toList());
			
	}
	
	private boolean distanceLessThanOneKm(GasStation gs, double lat, double lon) {

        // The math module contains a function 
        // named toRadians which converts from 
        // degrees to radians. 
       double lon1 = Math.toRadians(lon); 
       double lon2 = Math.toRadians(gs.getLon()); 
       double lat1 = Math.toRadians(lat); 
       double lat2 = Math.toRadians(gs.getLat()); 
        
        // Haversine formula  
        double dlon = lon2 - lon1;  
        double dlat = lat2 - lat1; 
        double a = Math.pow(Math.sin(dlat / 2), 2) 
        		+ Math.cos(lat1) * Math.cos(lat2) 
        		* Math.pow(Math.sin(dlon / 2),2); 
        
        double c = 2 * Math.asin(Math.sqrt(a)); 
        
        // Radius of earth in kilometers. Use 3956  
        // for miles 
        double r = 6371; 
        
        // calculate the result
        if( c * r > 1)
        	return false;
        else 
        	return true;
       
    }

	@Override
	public List<GasStationDto> getGasStationsWithoutCoordinates(String gasolinetype, String carsharing)
			throws InvalidGasTypeException {
		// TODO Auto-generated method stub

		if(!gasolinetype.equalsIgnoreCase("null")) {
			
			if(!gasolinetype.equalsIgnoreCase("diesel") && !gasolinetype.equalsIgnoreCase("super") && !gasolinetype.equalsIgnoreCase("superPlus") 
					&& !gasolinetype.equalsIgnoreCase("gas")	&& !gasolinetype.equalsIgnoreCase("methane")) {
				throw new InvalidGasTypeException("Invalid GasType");	
			}	
			
			if(!carsharing.equalsIgnoreCase("null")) {				
					return getGasStationsByGasolineType(gasolinetype)
							.stream()
							.filter(g -> g.getCarSharing().equals(carsharing))
							.collect(Collectors.toList());
			}else
					return getGasStationsByGasolineType(gasolinetype);						
					
		}else {			
			if(!carsharing.equalsIgnoreCase("null")) 			
				return getGasStationByCarSharing(carsharing);
			else
				return getAllGasStations();
		}
	}

	@Override
	public void setReport(Integer gasStationId, double dieselPrice, double superPrice, double superPlusPrice,
			double gasPrice, double methanePrice, Integer userId)
			throws InvalidGasStationException, PriceException, InvalidUserException {
		// TODO Auto-generated method stub
		
		if(gasStationId == null || gasStationId <= 0) {
			throw new InvalidGasStationException("Invalid GasStation Id");		
		}		
		
		UserDto user = userService.getUserById(userId);
		
		GasStationDto gs = getGasStationById(gasStationId);	

		
		if((gs.getHasDiesel() && dieselPrice <= 0) || (gs.getHasSuper() && superPrice <= 0) ||
				(gs.getHasSuperPlus() && superPlusPrice <= 0)  || (gs.getHasGas() && gasPrice <= 0 ) ||
				(gs.getHasMethane() && methanePrice <= 0)) {
			 throw new PriceException("Invalid Prices");
		}
		
		gs.setDieselPrice(dieselPrice);
		gs.setSuperPrice(superPrice);
		gs.setSuperPlusPrice(superPlusPrice);
		gs.setGasPrice(gasPrice);
		gs.setMethanePrice(methanePrice);
		
		gs.setReportUser(userId);  
		gs.setReportTimestamp(Long.toString(System.currentTimeMillis()));
		gs.setUserDto(user);
		
		double reportDependability = evaluateDependability(user.getReputation(), gs.getReportTimestamp());		
		gs.setReportDependability(reportDependability);		
		
		gasStationRepository.save(GasStationConverter.toGasStation(gs));
		
	}


	public double evaluateDependability(Integer reputation, String timestamp) {		
		
		if (reputation == null || reputation < -5 || reputation > 5)
			return -1.0;
		
		try {
	   	Long tg = Long.valueOf(timestamp);	   
	   	long now = System.currentTimeMillis();
	   	
	   	if(tg > now || tg < 0) {
	   		return -1.0;
	   	}
	   	

	    long diff = now - tg;
	    diff = diff / 1000 / 3600 / 24;
	    
	    System.out.println(diff);
	    
	    double obsolescence = 0;
		
		if(diff < 7) {
	    	obsolescence = 1.0 - diff/7.0;
	    }
		
		 System.out.println(obsolescence);
		return 50.0 * (reputation + 5.0 )/10.0 + 50.0 * obsolescence;
		} catch(NumberFormatException r) {
			return -1.0;
		}
	}

	@Override
	public List<GasStationDto> getGasStationByCarSharing(String carSharing) {

		
		return  gasStationRepository.findAll()
				.stream()
				.filter(gs -> gs.getCarSharing().equalsIgnoreCase(carSharing))
				.map(gs -> GasStationConverter.toGasStationDto(gs))
				.collect(Collectors.toList());
		}	

}
//last version