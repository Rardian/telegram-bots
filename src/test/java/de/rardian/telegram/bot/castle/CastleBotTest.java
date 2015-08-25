package de.rardian.telegram.bot.castle;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
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
import de.rardian.telegram.bot.model.User;

@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class CastleBotTest {
	@Mock
	private UserManager userManager;
	@Mock
	private CommandParser commandParser;
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
		when(userManager.isUserKnown(User.TEST_USER)).thenReturn(Boolean.TRUE);
		underTest.setUserManager(userManager);
		when(commandParser.parse(Message.TEST_MESSAGE)).thenReturn(Arrays.asList(action));
		underTest.setCommandParser(commandParser);

		// Run
		underTest.processMessage(Message.TEST_MESSAGE);

		// Assert
		verify(action).execute();
	}

	@Test
	public void getCommandOverview() throws Exception {
		// Init / Run
		String result = underTest.getCommandOverview();

		// Assert
		String expected = "Willkommen bei CastleBot. Werde Teil einer wachsenden und florierenden Burggemeinschaft. Folgende Kommandos stehen dir zur Verfügung.\n" //
				+ "help, hilfe: Diese Übersicht\n" //
				+ "prod, produzieren, produce: Produziere Güter für die Burg\n" //
				+ "stat, stats, status: Zeigt den Status der Burg\n";
		assertThat(result, is(expected));
	}

	// Need to mock action.execute() in CastleBot in cases like this
	//	@Test
	//	public void processMessage_newUser() {
	//		// Init
	//		when(userManager.isUserKnown(User.TEST_USER)).thenReturn(Boolean.FALSE);
	//		underTest.setUserManager(userManager);
	//		// Init
	//		when(commandParser.parse(Message.TEST_MESSAGE)).thenReturn(CollectionUtils.emptyCollection());
	//		underTest.setCommandParser(commandParser);
	//
	//		// Run
	//		underTest.processMessage(Message.TEST_MESSAGE);
	//
	//		// Assert
	//		verify(userManager).registerUser(User.TEST_USER);
	//	}

	@Test
	public void processMessage_registeredUser() {
		// Init
		when(userManager.isUserKnown(User.TEST_USER)).thenReturn(Boolean.TRUE);
		underTest.setUserManager(userManager);
		when(commandParser.parse(Message.TEST_MESSAGE)).thenReturn(Arrays.asList(action));
		underTest.setCommandParser(commandParser);

		// Run
		underTest.processMessage(Message.TEST_MESSAGE);

		// Assert
		verify(userManager, times(0)).registerUser(User.TEST_USER);
	}

}
