/**
 * 
 */
package de.rardian.telegram.bot.castle.commands.actions;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.rardian.telegram.bot.communication.MessageReply;
import de.rardian.telegram.bot.model.Bot;

@RunWith(MockitoJUnitRunner.class)
public class PrintHelpActionTest {
	private static final String OVERVIEW_TEST = "TESTOVERVIEW";
	@Mock
	private Bot bot;
	@Mock
	private MessageReply reply;
	@InjectMocks
	private PrintHelpAction underTest;

	@Test
	public void executeSendsCommandOverview() throws Exception {
		// Init
		when(bot.getCommandOverview()).thenReturn(OVERVIEW_TEST);

		// Run
		underTest.execute();

		// Assert
		verify(reply).answer(OVERVIEW_TEST, null);
	}
}
