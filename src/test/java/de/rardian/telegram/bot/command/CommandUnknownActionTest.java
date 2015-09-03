package de.rardian.telegram.bot.command;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommandUnknownActionTest {
	@Mock
	private MessageReply messageReply;
	@InjectMocks
	private CommandUnknownAction underTest;

	@Test
	public void execute() throws Exception {
		// Init

		// Run
		underTest.execute();

		// Assert
		verify(messageReply).answer("Kommando unbekannt", null);
	}

}
