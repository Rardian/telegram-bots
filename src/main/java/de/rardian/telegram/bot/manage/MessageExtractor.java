package de.rardian.telegram.bot.manage;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class MessageExtractor {
	private JSONObject json;

	public MessageExtractor(JSONObject json) {
		this.json = json;
	}

	public List<Message> extractMessages() {
		ArrayList<Message> messages = new ArrayList<>();
		JSONArray results = json.getJSONArray("result");

		for (int i = 0; i < results.length(); i++) {
			JSONObject resultAsJson = (JSONObject) results.get(i);
			long update_id = resultAsJson.getLong("update_id");
			Message message = new Message();
			message.setUpdate_id(update_id);
			messages.add(message.fillWithJson(resultAsJson.getJSONObject("message")));
		}

		return messages;
	}

}
