package de.rardian.telegram.bot.rardian;

import java.io.IOException;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.client.fluent.Request;

import de.rardian.telegram.bot.manage.Message;
import de.rardian.telegram.bot.model.Bot;

public class MessageReply {

	private Message message;
	private Bot bot;

	public MessageReply toMessage(Message message) {
		this.message = message;
		return this;
	}

	public MessageReply asBot(Bot bot) {
		this.bot = bot;
		return this;
	}

	public void answer(String answer) {
		long chatId = message.getChat().getId();

		try {
			String answerEncoded = new URLCodec().encode(answer);
			String url = "https://api.telegram.org/bot" + bot.getId() + "/sendMessage?text=" + answerEncoded + "&chat_id=" + chatId;
			System.out.println(url);
			Request.Get(url).execute().returnContent();
		} catch (IOException | EncoderException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
