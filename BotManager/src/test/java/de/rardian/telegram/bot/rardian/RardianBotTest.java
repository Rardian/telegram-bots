package de.rardian.telegram.bot.rardian;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import de.rardian.telegram.bot.manage.Message;
import de.rardian.telegram.bot.manage.UserManager;
import de.rardian.telegram.bot.model.User;

@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class RardianBotTest {
	@Mock
	private MessageReply reply;
	@Mock
	private UserManager userManager;

	private RardianBot underTest;

	@Before
	public void setupBot() {
		underTest = new RardianBot();
		underTest.setMessageReply(reply);
		underTest.setUserManager(userManager);
	}

	@Test
	public void type() throws Exception {
		assertThat(RardianBot.class, notNullValue());
	}

	@Test
	public void instantiation() throws Exception {
		RardianBot target = new RardianBot();
		assertThat(target, notNullValue());
	}

	@Test
	public void testProcessMessage_newUser() {
		// Run
		underTest.processMessage(Message.TEST_MESSAGE);

		// Assert
		verify(userManager).registerUser(User.TEST_USER);
	}

	@Test
	public void testProcessMessage_registeredUser() {
		// Init
		when(userManager.isUserKnown(User.TEST_USER)).thenReturn(Boolean.TRUE);

		underTest.setUserManager(userManager);

		// Run
		underTest.processMessage(Message.TEST_MESSAGE);

		// Assert
		verify(userManager, times(0)).registerUser(User.TEST_USER);
	}

}
