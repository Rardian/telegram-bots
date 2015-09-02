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

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.command.MessageReply;

@RunWith(MockitoJUnitRunner.class)
public class CastleStatusActionTest {
	private static final String STATUS_TEST = "TESTSTATUS";
	@Mock
	private Castle castle;
	@Mock
	private MessageReply reply;
	@InjectMocks
	private CastleStatusAction underTest;

	@Test
	public void executeSendsCastleStatus() throws Exception {
		// Init
		when(castle.getStatusAsString()).thenReturn(STATUS_TEST);

		// Run
		underTest.execute();

		// Assert
		verify(reply).answer(STATUS_TEST, null);
	}
}
