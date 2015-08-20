package de.rardian.telegram.bot.command;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import de.rardian.telegram.bot.manage.Message;

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
		underTest = new CommandParser();
		underTest.withCommands(commands);
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
	public void noCommandsSet() throws Exception {
		// Init
		thrown.expect(NullPointerException.class);

		// Run / Assert
		underTest.parse(message);
	}
}
