package it.polito.ezgas;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;

public class UserConverterTest {

	// black box tests
	
	@Test
	public void testToUserDto() {
		User u = mock(User.class);
		UserDto udto = null;

		when(u.getUserId()).thenReturn(1);
		when(u.getUserName()).thenReturn("Admin1");
		when(u.getPassword()).thenReturn("fakePassw0rd");
		when(u.getEmail()).thenReturn("fake@email.com");
		when(u.getReputation()).thenReturn(5);
		when(u.getAdmin()).thenReturn(true);

		udto = UserConverter.toUserDto(u);

		assertEquals(u.getUserId(), udto.getUserId());
		assertEquals(u.getUserName(), udto.getUserName());
		assertEquals(u.getPassword(), udto.getPassword());
		assertEquals(u.getEmail(), udto.getEmail());
		assertEquals(u.getReputation(), udto.getReputation());
		assertEquals(u.getAdmin(), udto.getAdmin());
	}

	@Test
	public void testToUserDto1() {
		User u = mock(User.class);
		UserDto udto = null;

		when(u.getUserId()).thenReturn(100);
		when(u.getUserName()).thenReturn("NormalUser1");
		when(u.getPassword()).thenReturn("fakePassw0rd");
		when(u.getEmail()).thenReturn("fake@email.com");
		when(u.getReputation()).thenReturn(4);
		when(u.getAdmin()).thenReturn(false);

		udto = UserConverter.toUserDto(u);

		assertEquals(u.getUserId(), udto.getUserId());
		assertEquals(u.getUserName(), udto.getUserName());
		assertEquals(u.getPassword(), udto.getPassword());
		assertEquals(u.getEmail(), udto.getEmail());
		assertEquals(u.getReputation(), udto.getReputation());
		assertEquals(u.getAdmin(), udto.getAdmin());
	}

	@Test
	public void testToUser() {
		UserDto udto = mock(UserDto.class);
		User u = null;

		when(udto.getUserId()).thenReturn(1);
		when(udto.getUserName()).thenReturn("AdminDto1");
		when(udto.getPassword()).thenReturn("fakePassw0rd");
		when(udto.getEmail()).thenReturn("fake@email.com");
		when(udto.getReputation()).thenReturn(5);
		when(udto.getAdmin()).thenReturn(true);

		u = UserConverter.toUser(udto);

		assertEquals(udto.getUserId(), u.getUserId());
		assertEquals(udto.getUserName(), u.getUserName());
		assertEquals(udto.getPassword(), u.getPassword());
		assertEquals(udto.getEmail(), u.getEmail());
		assertEquals(udto.getReputation(), u.getReputation());
		assertEquals(udto.getAdmin(), u.getAdmin());
	}

	@Test
	public void testToUser1() {
		UserDto udto = mock(UserDto.class);
		User u = null;

		when(udto.getUserId()).thenReturn(100);
		when(udto.getUserName()).thenReturn("NormalUserDto1");
		when(udto.getPassword()).thenReturn("fakePassw0rd");
		when(udto.getEmail()).thenReturn("fake@email.com");
		when(udto.getReputation()).thenReturn(4);
		when(udto.getAdmin()).thenReturn(false);

		u = UserConverter.toUser(udto);

		assertEquals(udto.getUserId(), u.getUserId());
		assertEquals(udto.getUserName(), u.getUserName());
		assertEquals(udto.getPassword(), u.getPassword());
		assertEquals(udto.getEmail(), u.getEmail());
		assertEquals(udto.getReputation(), u.getReputation());
		assertEquals(udto.getAdmin(), u.getAdmin());
	}

	// white box tests
	
	@Test
	public void testToUserDto2() {
		User u = null;
		UserDto udto = null;

		udto = UserConverter.toUserDto(u);

		assertNull(udto);
	}

	@Test
	public void testToUser2() {
		UserDto udto = null;
		User u = null;

		u = UserConverter.toUser(udto);

		assertNull(u);
	}
	
	// integration tests
	
	@Test
	public void testToUserDto3() {

		UserDto udto = null;
		User u = new User("Admin1", "fakePassw0rd", "fake@email.com", 5);
		u.setUserId(1);
		u.setAdmin(true);

		udto = UserConverter.toUserDto(u);

		assertEquals(u.getUserId(), udto.getUserId());
		assertEquals(u.getUserName(), udto.getUserName());
		assertEquals(u.getPassword(), udto.getPassword());
		assertEquals(u.getEmail(), udto.getEmail());
		assertEquals(u.getReputation(), udto.getReputation());
		assertEquals(u.getAdmin(), udto.getAdmin());
	}
	
	@Test
	public void testToUserDto4() {

		UserDto udto = null;
		User u = new User("NormalUser1", "fakePassw0rd", "fake@email.com", 4);
		u.setUserId(100);
		u.setAdmin(false);

		udto = UserConverter.toUserDto(u);

		assertEquals(u.getUserId(), udto.getUserId());
		assertEquals(u.getUserName(), udto.getUserName());
		assertEquals(u.getPassword(), udto.getPassword());
		assertEquals(u.getEmail(), udto.getEmail());
		assertEquals(u.getReputation(), udto.getReputation());
		assertEquals(u.getAdmin(), udto.getAdmin());
	}

	@Test
	public void testToUser3() {

		User u = null;
		UserDto udto = new UserDto(1, "AdminDto1", "fakePassw0rd", 
								  "fake@email.com", 5, true);

		u = UserConverter.toUser(udto);

		assertEquals(udto.getUserId(), u.getUserId());
		assertEquals(udto.getUserName(), u.getUserName());
		assertEquals(udto.getPassword(), u.getPassword());
		assertEquals(udto.getEmail(), u.getEmail());
		assertEquals(udto.getReputation(), u.getReputation());
		assertEquals(udto.getAdmin(), u.getAdmin());
	}
	
	@Test
	public void testToUser4() {

		User u = null;
		UserDto udto = new UserDto(100, "NormalUserDto1", "fakePassw0rd", 
								  "fake@email.com", 4, false);

		u = UserConverter.toUser(udto);

		assertEquals(udto.getUserId(), u.getUserId());
		assertEquals(udto.getUserName(), u.getUserName());
		assertEquals(udto.getPassword(), u.getPassword());
		assertEquals(udto.getEmail(), u.getEmail());
		assertEquals(udto.getReputation(), u.getReputation());
		assertEquals(udto.getAdmin(), u.getAdmin());
	}
}
