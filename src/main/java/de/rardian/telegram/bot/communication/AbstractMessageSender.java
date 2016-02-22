package de.rardian.telegram.bot.communication;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

import de.rardian.telegram.bot.model.ReplyKeyboardMarkup;

public abstract class AbstractMessageSender {

	protected void sendMessage(String asBotId, long toChatId, String message, ReplyKeyboardMarkup keyboard) {

		HttpRequest request = Unirest//
				.post("https://api.telegram.org/bot" + asBotId + "/sendMessage")//
				.queryString("text", message)//
				.queryString("chat_id", toChatId);
		System.out.println(request.getUrl());

		if (keyboard != null) {
			request.queryString("reply_markup", keyboard.asJson());
		}

		try {
			request.asJson();
		} catch (UnirestException e1) {
			e1.printStackTrace();
			// throw new RuntimeException(e1);
		}
	}
}
