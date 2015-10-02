package de.rardian.telegram.bot.castle.commands;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Test;

import de.rardian.telegram.bot.castle.commands.actions.SetInhabitantToWorkAction;
import de.rardian.telegram.bot.command.action.Action;

public class InhabitantProduceCommandTest {

	@Test
	public void testExecuteWithParams() throws Exception {
		// Init / Run
		Collection<Action> result = new InhabitantProduceCommand().executeWithParams("");

		// Assert
		assertThat(result.size(), is(2));
		assertThat(result.iterator().next(), instanceOf(SetInhabitantToWorkAction.class));
	}

}
