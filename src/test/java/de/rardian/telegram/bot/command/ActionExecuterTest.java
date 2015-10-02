/**
 * 
 */
package de.rardian.telegram.bot.command;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import de.rardian.telegram.bot.model.Message;

@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class ActionExecuterTest {
	@Mock
	ActionInitializer actionInitializer;
	@Mock
	Action action;
	@Mock
	Message message;

	private ActionExecuter underTest;

	@Before
	public void initExecuter() {
		underTest = new ActionExecuter().withInitializer(actionInitializer);
	}

	@Test
	public void execute() throws Exception {
		// Init

		// Run
		underTest.execute(action, message);

		// Assert
		verify(actionInitializer).injectActionDependencies(action, message);
		verify(action).execute();
	}

}
