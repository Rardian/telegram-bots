package de.rardian.telegram.bot.castle.commands;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.rardian.telegram.bot.castle.commands.actions.SetInhabitantToWorkAction;
import de.rardian.telegram.bot.command.action.Action;
import de.rardian.telegram.bot.command.action.SendMessageToUserAction;
import de.rardian.telegram.bot.model.Message;

@RunWith(MockitoJUnitRunner.class)
public class InhabitantProduceCommandTest {
	@Mock
	Message message;

	@InjectMocks
	InhabitantProduceCommand underTest;

	@Test
	public void shouldStripParamsFromBlanks() throws Exception {
		// Init / Run
		Collection<Action> result = underTest.executeWithParams("   ");

		// Assert
		assertThat(result.size(), is(1));
		assertThat(result.iterator().next(), instanceOf(SendMessageToUserAction.class));

	}

	@Test
	public void shouldSendInfoMessageOnUnknownParams() throws Exception {
		// Init
		String params = "unbekannt";

		// Run
		Collection<Action> result = underTest.executeWithParams(params);

		// Assert
		assertThat(result.size(), is(1));
		assertThat(result.iterator().next(), instanceOf(SendMessageToUserAction.class));
	}

	@Test
	public void shouldProduceOnKnownParams() throws Exception {
		// Init
		for (String knownParam : Arrays.asList("holz", "eisen", "stein")) {

			// Run
			Collection<Action> result = underTest.executeWithParams(knownParam);

			// Assert
			String assertInfo = "param '" + knownParam + "' should be known";
			assertThat(assertInfo, result.size(), is(2));
			assertThat(assertInfo, result.iterator().next(), instanceOf(SetInhabitantToWorkAction.class));
		}

	}
}
