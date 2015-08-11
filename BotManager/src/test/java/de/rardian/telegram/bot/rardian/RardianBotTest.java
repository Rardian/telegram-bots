package de.rardian.telegram.bot.rardian;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mockito.Mockito;

import de.rardian.telegram.bot.manage.Message;
import de.rardian.telegram.bot.manage.UserManager;
import de.rardian.telegram.bot.model.User;

public class RardianBotTest {

	@Test
	public void type() throws Exception {
		assertThat(RardianBotTest.class, notNullValue());
	}

	@Test
	public void instantiation() throws Exception {
		RardianBotTest target = new RardianBotTest();
		assertThat(target, notNullValue());
	}

	@Test
	public void testProcessMessage_newUser() {
		// Init
		RardianBot underTest = new RardianBot();
		UserManager managerMock = Mockito.mock(UserManager.class);
		underTest.setUserManager(managerMock);

		// Run
		underTest.processMessage(Message.TEST_MESSAGE);

		// Assert
		Mockito.verify(managerMock).registerUser(User.TEST_USER);
	}

	@Test
	public void testProcessMessage_registeredUser() {
		// Init
		RardianBot underTest = new RardianBot();
		UserManager managerMock = Mockito.mock(UserManager.class);
		Mockito.when(managerMock.isUserKnown(User.TEST_USER)).thenReturn(Boolean.TRUE);

		underTest.setUserManager(managerMock);

		// Run
		underTest.processMessage(Message.TEST_MESSAGE);

		// Assert
		Mockito.verify(managerMock, Mockito.times(0)).registerUser(User.TEST_USER);
	}

}
