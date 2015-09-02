/**
 * 
 */
package de.rardian.telegram.bot.castle.commands.actions;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.command.MessageReply;
import de.rardian.telegram.bot.model.User;

@RunWith(MockitoJUnitRunner.class)
public class UserMovesInActionTest {
	private static final String OVERVIEW_TEST = "TESTOVERVIEW";
	@Mock
	private Castle castle;
	@Mock
	private User user;
	@Mock
	private MessageReply reply;
	@InjectMocks
	private UserMovesInAction underTest;

	@Test
	public void executeSendsCommandOverview() throws Exception {
		// Run
		underTest.execute();

		// Assert
		verify(reply).answer("Willkommen, neuer Burgbewohner. Tippe /help", null);
	}

	@Test
	public void executeAddsInhabitant() throws Exception {
		// Run
		underTest.execute();

		// Assert
		verify(castle).addInhabitant(user);
	}
}
