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
public class MoveInhabitantToProductionActionTest {
	@Mock
	private Castle castle;
	@Mock
	private User user;
	@Mock
	private MessageReply reply;
	@InjectMocks
	private MoveInhabitantToProductionAction underTest;

	@Test
	public void execute() throws Exception {
		// Run
		underTest.execute();

		// Assert
		verify(castle).addProducer(user);
	}

}
