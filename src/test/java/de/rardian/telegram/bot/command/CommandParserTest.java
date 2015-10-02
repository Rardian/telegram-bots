package de.rardian.telegram.bot.command;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.rardian.telegram.bot.castle.commands.Test1Command;
import de.rardian.telegram.bot.command.action.Action;
import de.rardian.telegram.bot.command.action.CommandUnknownAction;
import de.rardian.telegram.bot.model.Message;

@RunWith(MockitoJUnitRunner.class)
public class CommandParserTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private Map<String, Command> commands;
	@Mock
	private Message message;

	private CommandParser underTest;

	@Before
	public void setupBot() {
		underTest = new CommandParser().withCommands(commands);
	}

	@Test
	public void type() throws Exception {
		assertThat(CommandParser.class, notNullValue());
	}

	@Test
	public void instantiation() throws Exception {
		CommandParser target = new CommandParser();
		assertThat(target, notNullValue());
	}

	@Test
	public void parse_noCommandsSet() throws Exception {
		// Init
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("command set must not be null");
		underTest.withCommands(null);

		// Run / Assert
		underTest.parse(message);
	}

	@Test
	public void parse_commandOnly() throws Exception {
		// Init
		final String commandTrigger = "/command1";

		when(message.getText()).thenReturn(commandTrigger);
		when(commands.get(commandTrigger)).thenReturn(new Test1Command());

		// Run
		Collection<Action> result = underTest.parse(message);

		// Assert
		assertThat(result, hasItem(Test1Command.TEST_ACTION));
	}

	@Test
	public void parse_commandWithParams() throws Exception {
		// Init
		final String commandTrigger = "/command1";

		when(message.getText()).thenReturn(commandTrigger + " eins zwei");
		when(commands.get(commandTrigger)).thenReturn(new Test1Command());

		// Run
		Collection<Action> result = underTest.parse(message);

		// Assert
		assertThat(result, hasItem(Test1Command.TEST_ACTION));
	}

	@Test
	public void parse_noCommandSent() throws Exception {
		// Init
		final String noCommandTrigger = "noCommand";

		when(message.getText()).thenReturn(noCommandTrigger);

		// Run
		Collection<Action> result = underTest.parse(message);

		// Assert
		assertThat(result.iterator().next().getClass(), is(sameInstance(CommandUnknownAction.class)));
	}

	@Test
	public void parse_wrongCommandSent() throws Exception {
		// Init
		final String unknownCommandTrigger = "/unknown";

		when(message.getText()).thenReturn(unknownCommandTrigger);
		when(commands.get(unknownCommandTrigger)).thenReturn(null);

		// Run
		Collection<Action> result = underTest.parse(message);

		// Assert
		assertThat(result.iterator().next().getClass(), is(sameInstance(CommandUnknownAction.class)));
	}
}
