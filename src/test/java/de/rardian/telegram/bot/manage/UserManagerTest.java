package de.rardian.telegram.bot.manage;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.rardian.telegram.bot.model.User;

public class UserManagerTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void type() throws Exception {
		assertThat(UserManager.class, notNullValue());
	}

	@Test
	public void instantiation() throws Exception {
		UserManager target = new UserManager();
		assertThat(target, notNullValue());
	}

	@Test
	public void isUserKnown_userUnknown() throws Exception {
		// Init
		UserManager underTest = new UserManager();

		// Run
		boolean result = underTest.isUserKnown(User.newIdentTestUser());

		// Assert
		assertThat(result, is(false));
	}

	@Test
	public void isUserKnown_userKnown() throws Exception {
		// Init
		UserManager underTest = new UserManager();
		underTest.registerUser(User.newIdentTestUser());

		// Run
		boolean result = underTest.isUserKnown(User.newIdentTestUser());

		// Assert
		assertThat(result, is(true));
	}

	@Test
	public void isUserKnown_userNull() throws Exception {
		// Init
		UserManager underTest = new UserManager();

		// Run
		boolean result = underTest.isUserKnown(null);

		// Assert
		assertThat(result, is(false));
	}

	@Test
	public void registerUser() throws Exception {
		// Init
		UserManager underTest = new UserManager();

		// Run
		underTest.registerUser(User.newIdentTestUser());

		// Assert
		assertThat(underTest.isUserKnown(User.newIdentTestUser()), is(true));
	}

	@Test
	public void registerUser_userNull() throws Exception {
		// Init
		thrown.expect(NullPointerException.class);

		UserManager underTest = new UserManager();

		// Run
		underTest.registerUser(null);
	}
}
