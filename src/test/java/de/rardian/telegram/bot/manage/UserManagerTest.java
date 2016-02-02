package de.rardian.telegram.bot.manage;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.rardian.telegram.bot.model.User;
import de.rardian.telegram.bot.model.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserManagerTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserManager underTest;

	@Test
	public void type() throws Exception {
		assertThat(UserManager.class, notNullValue());
	}

	@Test
	public void instantiation() throws Exception {
		assertThat(underTest, notNullValue());
	}

	@Test
	public void isUserKnown_userUnknown() throws Exception {
		// Run
		boolean result = underTest.isUserKnown(User.newIdentTestUser());

		// Assert
		assertThat(result, is(false));
	}

	@Test
	public void isUserKnown_userKnown() throws Exception {
		// Init
		User testUser = User.newIdentTestUser();
		when(userRepository.save(testUser)).thenReturn(testUser);
		when(userRepository.exists(Long.valueOf(testUser.getId()))).thenReturn(Boolean.TRUE);
		underTest.registerUser(testUser);

		// Run
		boolean result = underTest.isUserKnown(User.newIdentTestUser());

		// Assert
		assertThat(result, is(true));
	}

	@Test
	public void isUserKnown_userNull() throws Exception {
		// Init
		thrown.expect(NullPointerException.class);

		// Run / Assert
		underTest.isUserKnown(null);
	}

	@Test
	public void registerUser() throws Exception {
		// Init
		User testUser = User.newIdentTestUser();
		when(userRepository.save(testUser)).thenReturn(testUser);
		when(userRepository.exists(Long.valueOf(testUser.getId()))).thenReturn(Boolean.TRUE);

		// Run
		underTest.registerUser(testUser);

		// Assert
		assertThat(underTest.isUserKnown(testUser), is(true));
	}

	@Test
	public void registerUser_usernameNull() throws Exception {
		// Init
		JSONObject json = new JSONObject("{\"id\":92097519,\"first_name\":\"Vorname\"}");
		User testUser = new User().fillWithJson(json);
		when(userRepository.save(testUser)).thenReturn(testUser);
		when(userRepository.exists(Long.valueOf(testUser.getId()))).thenReturn(Boolean.TRUE);

		// Run
		underTest.registerUser(testUser);

		// Assert
		assertThat(underTest.isUserKnown(testUser), is(true));
	}

	@Test
	public void registerUser_userNull() throws Exception {
		// Init
		thrown.expect(NullPointerException.class);

		// Run / Assert
		underTest.registerUser(null);
	}
}
