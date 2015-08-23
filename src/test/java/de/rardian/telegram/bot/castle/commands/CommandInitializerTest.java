/**
 * 
 */
package de.rardian.telegram.bot.castle.commands;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.withSettings;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.rardian.telegram.bot.castle.commands.actions.CastleAware;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.BotAware;
import de.rardian.telegram.bot.command.MessageReply;
import de.rardian.telegram.bot.command.SendsAnswer;
import de.rardian.telegram.bot.command.UserAware;
import de.rardian.telegram.bot.manage.Message;
import de.rardian.telegram.bot.model.Bot;

@RunWith(MockitoJUnitRunner.class)
public class CommandInitializerTest {
	@Mock
	private Bot bot;
	@Mock
	private Castle castle;
	@InjectMocks
	private CommandInitializer commandInitializer;

	@Test
	public void injectActionDependencies_instanceOfCastleAware() throws Exception {
		// Init
		Action action = mock(Action.class, withSettings().extraInterfaces(CastleAware.class));

		// Run
		commandInitializer.injectActionDependencies(action, Message.TEST_MESSAGE);

		// Assert
		((CastleAware) verify(action)).setCastle(castle);
	}

	@Test
	public void injectActionDependencies_instanceOfSendsAnswer() throws Exception {
		// Init
		Action action = mock(Action.class, withSettings().extraInterfaces(SendsAnswer.class));

		// Run
		commandInitializer.injectActionDependencies(action, Message.TEST_MESSAGE);

		// Assert
		((SendsAnswer) verify(action)).setMessageReply(Mockito.any(MessageReply.class));
	}

	@Test
	public void injectActionDependencies_instanceOfUserAware() throws Exception {
		// Init
		Action action = mock(Action.class, withSettings().extraInterfaces(UserAware.class));

		// Run
		commandInitializer.injectActionDependencies(action, Message.TEST_MESSAGE);

		// Assert
		((UserAware) verify(action)).setUser(Message.TEST_MESSAGE.getFrom());
	}

	@Test
	public void injectActionDependencies_instanceOfBotAware() throws Exception {
		// Init
		Action action = mock(Action.class, withSettings().extraInterfaces(BotAware.class));

		// Run
		commandInitializer.injectActionDependencies(action, Message.TEST_MESSAGE);

		// Assert
		((BotAware) verify(action)).setBot(bot);
	}

}
