package de.rardian.telegram.bot.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import de.rardian.telegram.json.JSONArray;
import de.rardian.telegram.json.JSONObject;

public class MessageExtractor {
	private JSONObject json;

	public MessageExtractor(JSONObject json) {
		this.json = json;
	}

	public List<Message> extractMessages() {
		ArrayList<Message> messages = new ArrayList<Message>();
		JSONArray results = json.getJSONArray("result");

		results.forEach((result) -> {
			JSONObject resultAsJson = (JSONObject) result;

			final long update_id = resultAsJson.getLong("update_id");

			Function<JSONObject, Message> jsonToMessage = new Function<JSONObject, Message>() {

				public Message apply(JSONObject json) {
					Message message = new Message();
					message.setUpdate_id(update_id);

					return message.fillWithJson(json);
				}
			};
			JSONObject messageAsJson = resultAsJson.getJSONObject("message");
			messages.add(jsonToMessage.apply(messageAsJson));
		});
		return messages;
	}

}
