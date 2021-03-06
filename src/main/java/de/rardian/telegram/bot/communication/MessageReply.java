package de.rardian.telegram.bot.communication;

import de.rardian.telegram.bot.model.Bot;
import de.rardian.telegram.bot.model.Message;
import de.rardian.telegram.bot.model.ReplyKeyboardMarkup;

public class MessageReply extends AbstractMessageSender {

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
		answer(answer, null);
	}

	public void answer(String answer, ReplyKeyboardMarkup keyboard) {
		long chatId = message.getChat().getId();

		super.sendMessage(bot.getId(), chatId, answer, keyboard);
		// HttpRequest request = Unirest//
		// .post("https://api.telegram.org/bot" + bot.getId() +
		// "/sendMessage")//
		// .queryString("text", answer)//
		// .queryString("chat_id", chatId);
		// if (keyboard != null) {
		// request.queryString("reply_markup", keyboard.asJson());
		// }
		// System.out.println(request.getUrl());
		// try {
		// request.asJson();
		// } catch (UnirestException e1) {
		// e1.printStackTrace();
		// // throw new RuntimeException(e1);
		// }
	}

}
