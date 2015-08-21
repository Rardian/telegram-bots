package de.rardian.telegram.bot.rardian;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.CommandParser;
import de.rardian.telegram.bot.command.MessageReply;
import de.rardian.telegram.bot.manage.Message;
import de.rardian.telegram.bot.manage.UserManager;

@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class RardianBotTest {
	@Mock
	private MessageReply reply;
	@Mock
	private UserManager userManager;
	@Mock
	private CommandParser commandParser;
	@Mock
	private Message message;
	@Mock
	private Action action;

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
		assertThat(underTest, notNullValue());
	}

	@Test
	public void processMessage_actionExecuted() {
		// Init
		underTest.setCommandParser(commandParser);
		Mockito.when(commandParser.parse(message)).thenReturn(Arrays.asList(action));

		// Run
		underTest.processMessage(message);

		// Assert
		Mockito.verify(action).execute();
	}

	// @Test
	// public void testProcessMessage_newUser() {
	// // Run
	// underTest.processMessage(Message.TEST_MESSAGE);
	//
	// // Assert
	// verify(userManager).registerUser(User.TEST_USER);
	// }

	// @Test
	// public void testProcessMessage_registeredUser() {
	// // Init
	// when(userManager.isUserKnown(User.TEST_USER)).thenReturn(Boolean.TRUE);
	//
	// underTest.setUserManager(userManager);
	//
	// // Run
	// underTest.processMessage(Message.TEST_MESSAGE);
	//
	// // Assert
	// verify(userManager, times(0)).registerUser(User.TEST_USER);
	// }

}
