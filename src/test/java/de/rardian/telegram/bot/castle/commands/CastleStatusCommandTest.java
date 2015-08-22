/**
 * 
 */
package de.rardian.telegram.bot.castle.commands;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Test;

import de.rardian.telegram.bot.castle.commands.CastleStatusCommand;
import de.rardian.telegram.bot.castle.commands.actions.CastleStatusAction;
import de.rardian.telegram.bot.command.Action;

/**
 * @author Rardian
 *
 */
public class CastleStatusCommandTest {

	/**
	 * Test method for
	 * {@link de.rardian.telegram.bot.castle.commands.CastleStatusCommand#executeWithParams(java.lang.String)}
	 * .
	 */
	@Test
	public void executeWithParams() throws Exception {
		// Init / Run
		Collection<Action> result = new CastleStatusCommand().executeWithParams("");

		// Assert
		assertThat(result.size(), is(1));
		assertThat(result.iterator().next(), instanceOf(CastleStatusAction.class));
	}

}
