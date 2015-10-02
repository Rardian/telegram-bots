/**
 * 
 */
package de.rardian.telegram.bot.castle.commands.actions;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.communication.MessageReply;

@RunWith(MockitoJUnitRunner.class)
public class MoveInhabitantToProductionActionTest {
	@Mock
	private Castle castle;
	@Mock
	private Inhabitant inhabitant;
	@Mock
	private MessageReply reply;
	@InjectMocks
	private MoveInhabitantToProductionAction underTest;

	@Test
	public void executeAddsProducer() throws Exception {
		// Run
		underTest.execute();

		// Assert
		verify(castle).addProducer(inhabitant);
	}

	@Test
	public void executeSendsFeedbackToUser() throws Exception {
		// Run
		underTest.execute();

		// Assert
		verify(reply).answer("Du bist jetzt Teil der Produktionsmannschaft.", null);
	}

	@Test
	public void executeCannotAddProducerTwice() throws Exception {
		// Init
		Mockito.doThrow(AlreadyAddedException.class).when(castle).addProducer(inhabitant);

		// Run
		underTest.execute();

		// Assert
		verify(reply).answer("Du bist bereits Teil der Produktionsmannschaft.", null);
	}

}
