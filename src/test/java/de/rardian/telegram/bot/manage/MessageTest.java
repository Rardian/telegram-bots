package de.rardian.telegram.bot.manage;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import de.rardian.telegram.bot.model.Chat;
import de.rardian.telegram.bot.model.User;

public class MessageTest {

	private static final String JSON_CHAT_AND_FROM = "{\"id\":8039535,\"first_name\":\"Vorname\",\"last_name\":\"Nachname\",\"username\":\"Username\"}";

	private Message underTest;

	@Before
	public void initMessage() {
		String jsonText = "{\"message_id\":13,\"from\":" + JSON_CHAT_AND_FROM + ",\"chat\":" + JSON_CHAT_AND_FROM
				+ ",\"date\":1437335810,\"text\":\"Hallo Bot\"}";
		JSONObject json = new JSONObject(jsonText);

		underTest = new Message();
		underTest.setUpdate_id(40332882);
		underTest.fillWithJson(json);
	}

	@Test
	public void getUpdate_id() throws Exception {
		// Run
		long result = underTest.getUpdate_id();

		// Assert
		long expected = 40332882;
		assertThat(result, is(expected));
	}

	@Test
	public void getDate() throws Exception {
		// Run
		long result = underTest.getDate();

		// Assert
		long expected = 1437335810;
		assertThat(result, is(expected));
	}

	@Test
	public void getText() throws Exception {
		// Run
		String result = underTest.getText();

		// Assert
		String expected = "Hallo Bot";
		assertThat(result, is(expected));
	}

	/**
	 * Test method for {@link de.rardian.telegram.bot.manage.Message#getChat()}.
	 */
	@Test
	public void getChat() throws Exception {
		// Run
		Chat result = underTest.getChat();

		// Assert
		long expected = 8039535;
		assertThat(result.getId(), is(expected));
	}

	/**
	 * Test method for {@link de.rardian.telegram.bot.manage.Message#getFrom()}.
	 */
	@Test
	public void getFrom() throws Exception {
		// Run
		User result = underTest.getFrom();

		// Assert
		long expected = 8039535;
		assertThat(result.getId(), is(expected));
	}

	/**
	 * Test method for
	 * {@link de.rardian.telegram.bot.manage.Message#getMessage_id()}.
	 */
	@Test
	public void getMessage_id() throws Exception {
		// Run
		long result = underTest.getMessage_id();

		// Assert
		long expected = 13;
		assertThat(result, is(expected));
	}

}
