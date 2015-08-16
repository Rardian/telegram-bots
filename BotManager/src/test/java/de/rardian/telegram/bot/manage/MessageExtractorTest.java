package de.rardian.telegram.bot.manage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;

import de.rardian.telegram.bot.model.User;

public class MessageExtractorTest {

	private static final String JSON_CHAT_AND_FROM = "{\"id\":8039535,\"first_name\":\"Vorname\",\"last_name\":\"Nachname\",\"username\":\"Username\"}";

	@Test
	public void type() throws Exception {
		assertThat(MessageExtractor.class, notNullValue());
	}

	@Test
	public void instantiation() throws Exception {
		JSONObject json = null;
		MessageExtractor target = new MessageExtractor(json);
		assertThat(target, notNullValue());
	}

	@Test
	public void extractMessages_ExtractOneMessage() throws Exception {
		// Arrange
		String jsonText = "{\"ok\":true,\"result\":[{\"update_id\":40332882,\"message\":{\"message_id\":13,\"from\":" + JSON_CHAT_AND_FROM
				+ ",\"chat\":" + JSON_CHAT_AND_FROM + ",\"date\":1437335810,\"text\":\"Hallo Bot\"}}]}";
		JSONObject json = new JSONObject(jsonText);
		MessageExtractor target = new MessageExtractor(json);

		Message expectedMessage = new Message(40332882, 1437335810, "Hallo Bot", User.TEST_USER, User.TEST_USER, 13);

		// Act
		List<Message> actual = target.extractMessages();

		// Assert
		List<Message> expected = Arrays.asList(expectedMessage);
		assertThat(actual.toString(), is(equalTo(expected.toString())));
	}

}
