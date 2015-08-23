package de.rardian.telegram.bot.castle;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.CommandParser;
import de.rardian.telegram.bot.manage.Message;
import de.rardian.telegram.bot.manage.UserManager;

@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class CastleBotTest {
	@Mock
	private UserManager userManager;
	@Mock
	private CommandParser commandParser;
	@Mock
	private Message message;
	@Mock
	private Action action;

	private CastleBot underTest;

	@Before
	public void setupBot() {
		underTest = new CastleBot();
		underTest.setUserManager(userManager);
	}

	@Test
	public void type() throws Exception {
		assertThat(CastleBot.class, notNullValue());
	}

	@Test
	public void instantiation() throws Exception {
		assertThat(underTest, notNullValue());
	}

	@Test
	public void processMessage_actionExecuted() {
		// Init
		underTest.setCommandParser(commandParser);
		when(commandParser.parse(message)).thenReturn(Arrays.asList(action));

		// Run
		underTest.processMessage(message);

		// Assert
		verify(action).execute();
	}

	@Test
	public void getCommandOverview() throws Exception {

		// Run
		String result = underTest.getCommandOverview();
		System.out.println(result);

		String expected = "Willkommen bei CastleBot. Werde Teil einer wachsenden und florierenden Burggemeinschaft. Folgende Kommandos stehen dir zur Verfügung.\n" //
				+ "help, hilfe: Diese Übersicht\n" //
				+ "prod, produzieren, produce: Produziere Güter für die Burg\n" //
				+ "stat, stats, status: Zeigt den Status der Burg\n";
		// Assert
		assertThat(result, is(expected));
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
