package de.rardian.telegram.bot.manage;

import org.json.JSONObject;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;

import de.rardian.telegram.bot.model.Chat;
import de.rardian.telegram.bot.model.User;

public class Message {
	private long updateId;
	private long date;
	private String text;
	/** GroupChat or User, so far. */
	private Chat chat;
	private User from;
	private long messageId;
	//		"chat": {"id":8039535,"first_name":"Bjoern","username":"BjoernO"},
	//		"message_id":5,
	//		"from":{"id":8039535,"first_name":"Bjoern","username":"BjoernO"},
	//		"text":"Hallo, wie gehts?"

	public static final Message TEST_MESSAGE = new Message(40332882, 1437335810, "Hallo Bot", User.newIdentTestUser(), User.newIdentTestUser(), 13);

	public Message() {
	}

	@VisibleForTesting
	Message(long updateId, long date, String text, User chat, User from, long messageId) {
		this.updateId = updateId;
		this.date = date;
		this.text = text;
		this.chat = chat;
		this.from = from;
		this.messageId = messageId;
	}

	public Message fillWithJson(JSONObject json) {
		//		System.out.println(json.toString(2));

		User from = new User();
		from.fillWithJson(json.getJSONObject("from"));
		setFrom(from);

		User chat = new User();
		chat.fillWithJson(json.getJSONObject("chat"));
		setChat(chat);

		setDate(json.getLong("date"));
		setMessage_id(json.getLong("message_id"));
		setText(json.getString("text"));

		return this;
	}

	public long getUpdate_id() {
		return updateId;
	}

	public void setUpdate_id(long update_id) {
		this.updateId = update_id;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public long getMessage_id() {
		return messageId;
	}

	public void setMessage_id(long message_id) {
		this.messageId = message_id;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).addValue(date).addValue(messageId).addValue(updateId).addValue(text).addValue(chat).addValue(from)
				.toString();
	}
}
