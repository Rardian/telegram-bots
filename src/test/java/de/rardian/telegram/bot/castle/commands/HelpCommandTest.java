/**
 * 
 */
package de.rardian.telegram.bot.castle.commands;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Test;

import de.rardian.telegram.bot.castle.commands.actions.PrintHelpAction;
import de.rardian.telegram.bot.command.Action;

/**
 *
 */
public class HelpCommandTest {

	/**
	 * Test method for
	 * {@link de.rardian.telegram.bot.castle.commands.HelpCommand#executeWithParams(java.lang.String)}
	 * .
	 */
	@Test
	public void executeWithParams() throws Exception {
		// Init / Run
		Collection<Action> result = new HelpCommand().executeWithParams("");

		// Assert
		assertThat(result.size(), is(1));
		assertThat(result.iterator().next(), instanceOf(PrintHelpAction.class));
	}

}
