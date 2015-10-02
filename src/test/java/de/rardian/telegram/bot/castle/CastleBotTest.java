package de.rardian.telegram.bot.castle;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import de.rardian.telegram.bot.command.CommandParser;
import de.rardian.telegram.bot.command.action.Action;
import de.rardian.telegram.bot.command.action.ActionExecuter;
import de.rardian.telegram.bot.manage.UserManager;
import de.rardian.telegram.bot.model.Message;
import de.rardian.telegram.bot.model.User;

@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class CastleBotTest {
	@Mock
	private UserManager userManager;
	@Mock
	private CommandParser commandParser;
	@Mock
	private Action action;
	@Mock
	private ActionExecuter actionExecuter;

	private CastleBot underTest;

	@Before
	public void setupBot() {
		underTest = new CastleBot();
		underTest.setUserManager(userManager);
		underTest.setActionExecuter(actionExecuter);
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
		when(userManager.isUserKnown(User.newIdentTestUser())).thenReturn(Boolean.TRUE);
		underTest.setUserManager(userManager);

		when(commandParser.parse(Message.TEST_MESSAGE)).thenReturn(Arrays.asList(action));
		underTest.setCommandParser(commandParser);

		// Run
		underTest.processMessage(Message.TEST_MESSAGE);

		// Assert
		verify(actionExecuter).execute(action, Message.TEST_MESSAGE);
	}

	@Test
	public void getCommandOverview() throws Exception {
		// Init / Run
		String result = underTest.getCommandOverview();

		// Assert
		String expected = "Folgende Kommandos stehen dir zur Verfügung.\n" //
				+ "/bau - Erweitere das Ressourcenlager der Burg\n"//
				+ "/burg - Zeigt den Status der Burg\n"//
				+ "/char - Zeigt den Status deines Burgbewohners\n"//
				+ "/help - Diese Übersicht\n" //
				+ "/prod - Produziere Güter für die Burg\n" //
				+ "/scout - Erkunde das Umland\n";
		//		assertThat(result, is(expected));
	}

	// Need to mock action.execute() in CastleBot in cases like this
	@Test
	public void processMessage_newUser() {
		// Init
		User fromUser = Message.TEST_MESSAGE.getFrom();

		when(userManager.isUserKnown(fromUser)).thenReturn(Boolean.FALSE);
		underTest.setUserManager(userManager);

		when(commandParser.parse(Message.TEST_MESSAGE)).thenReturn(CollectionUtils.emptyCollection());
		underTest.setCommandParser(commandParser);

		// Run
		underTest.processMessage(Message.TEST_MESSAGE);

		// Assert
		verify(userManager).registerUser(fromUser);
	}

	@Test
	public void processMessage_registeredUser() {
		// Init
		User sameUser = User.newIdentTestUser();

		when(userManager.isUserKnown(sameUser)).thenReturn(Boolean.TRUE);
		underTest.setUserManager(userManager);

		when(commandParser.parse(Message.TEST_MESSAGE)).thenReturn(Arrays.asList(action));
		underTest.setCommandParser(commandParser);

		// Run
		underTest.processMessage(Message.TEST_MESSAGE);

		// Assert
		verify(userManager, times(0)).registerUser(sameUser);
	}

}
