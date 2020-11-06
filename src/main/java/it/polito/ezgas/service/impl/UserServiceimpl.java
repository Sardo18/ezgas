package it.polito.ezgas.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.InvalidLoginDataException;
import exception.InvalidUserException;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.service.UserService;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.repository.UserRepository;

/**
 * Created by softeng on 27/4/2020.
 */
@Service
public class UserServiceimpl implements UserService {
	
	@Autowired 
	UserService userService;
	

	UserRepository userRepository;
	
	public UserServiceimpl(UserRepository u) {
		this.userRepository = u;
	}
	
	
	@Override
	public UserDto getUserById(Integer userId) throws InvalidUserException {
		// TODO Auto-generated method stub
		
		if(userId == null || userId < 0) {
			throw new InvalidUserException("Invalid User Id");
		}
		return UserConverter.toUserDto(userRepository.findOne(userId));
	}

	@Override
	public UserDto saveUser(UserDto userDto) {
		// TODO Auto-generated method stub
		if(userDto == null)
			return null;
		
			if(userDto.getUserId() == null && userRepository.findByEmail(userDto.getEmail()) != null && userDto.getAdmin() == false)
				return null;
		
		User u= UserConverter.toUser(userDto);

		return UserConverter.toUserDto(userRepository.save(u));
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		
		return userRepository.findAll()
				.stream()
				.map(us -> UserConverter.toUserDto(us))
				.collect(Collectors.toList());
	}

	@Override
	public Boolean deleteUser(Integer userId) throws InvalidUserException {
		// TODO Auto-generated method stub
		
		if(getUserById(userId)!= null) {
			userRepository.delete(userId);
			return true;
		} else {
			throw new InvalidUserException("Invalid unse Id");
		}
		
	}

	@Override
	public LoginDto login(IdPw credentials) throws InvalidLoginDataException {
		// TODO Auto-generated method stub
		
		if(credentials == null) {
			throw new InvalidLoginDataException("Invalid Credentials");
		}
		String token="token";
		User u = userRepository.findByEmail(credentials.getUser());
		
		if(u==null) {
			throw new InvalidLoginDataException("Invalid User Name");
		}
		if(!u.getPassword().equals(credentials.getPw())) {
			throw new InvalidLoginDataException("Invalid Password");
		}
		LoginDto d = new LoginDto(u.getUserId(), u.getUserName(), token, u.getEmail(), u.getReputation());
		d.setAdmin(u.getAdmin());
		 
		return d;
	}
	
	@Override
	public Integer increaseUserReputation(Integer userId) throws InvalidUserException {
		// TODO Auto-generated method stub
		
		Integer rep;
		UserDto u = getUserById(userId);
		if (u == null) {
			 throw new InvalidUserException("Invalid User Id");
		}
		
		if(u.getReputation()==5) {
			return u.getReputation();
		}
		
		rep = u.getReputation()+1;
		u.setReputation(rep);
		userRepository.save(UserConverter.toUser(u));
		return rep;
	}

	@Override
	public Integer decreaseUserReputation(Integer userId) throws InvalidUserException {
		// TODO Auto-generated method stub
		
		Integer rep;
		UserDto u = getUserById(userId);
		if (u == null) 
			 throw new InvalidUserException("Invalid User Id");
			 
		if(u.getReputation()==-5) {
			return u.getReputation();
		}
		
		rep = u.getReputation()-1;
		u.setReputation(rep);
		userRepository.save(UserConverter.toUser(u));
		return rep;
	}
	
}
