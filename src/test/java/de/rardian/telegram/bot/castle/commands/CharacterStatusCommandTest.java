package de.rardian.telegram.bot.castle.commands;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Test;

import de.rardian.telegram.bot.castle.commands.actions.CharacterStatusAction;
import de.rardian.telegram.bot.command.action.Action;

public class CharacterStatusCommandTest {
	@Test
	public void executeWithParams() throws Exception {
		// Init / Run
		Collection<Action> result = new CharacterStatusCommand().executeWithParams("");

		// Assert
		assertThat(result.size(), is(1));
		assertThat(result.iterator().next(), instanceOf(CharacterStatusAction.class));
	}

}
