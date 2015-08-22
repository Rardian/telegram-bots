package de.rardian.telegram.bot.rardian.commands;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Test;

import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.rardian.commands.actions.MoveInhabitantToProductionAction;

public class InhabitantProduceCommandTest {

	@Test
	public void testExecuteWithParams() throws Exception {
		// Init / Run
		Collection<Action> result = new InhabitantProduceCommand().executeWithParams("");

		// Assert
		assertThat(result.size(), is(1));
		assertThat(result.iterator().next(), instanceOf(MoveInhabitantToProductionAction.class));
	}

}
