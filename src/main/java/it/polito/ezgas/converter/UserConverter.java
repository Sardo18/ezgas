package it.polito.ezgas.converter;

import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;



public class UserConverter {
	
	public static UserDto toUserDto(User user) {
		
		if(user == null) {
			return null;
		}
		
		return new UserDto(user.getUserId(), user.getUserName(), user.getPassword(),
				user.getEmail(), user.getReputation(), user.getAdmin());
	}
	
	public static User toUser(UserDto userDto) {
		if(userDto==null) {
			return null;
		}
		User u = new User(userDto.getUserName(), userDto.getPassword(),
				userDto.getEmail(), userDto.getReputation());
		u.setUserId(userDto.getUserId());
		u.setAdmin(userDto.getAdmin());
		return u;

}
}
