package de.rardian.telegram.bot.communication;

import de.rardian.telegram.bot.model.Bot;
import de.rardian.telegram.bot.model.ReplyKeyboardMarkup;
import de.rardian.telegram.bot.model.User;

public class MessageSender extends AbstractMessageSender {

	private Bot bot;

	public MessageSender asBot(Bot bot) {
		this.bot = bot;
		return this;
	}

	public void sendMessage(User user, String message) {
		long chatId = user.getId();
		ReplyKeyboardMarkup noKeyboard = null;

		super.sendMessage(bot.getId(), chatId, message, noKeyboard);
		// HttpRequest request = Unirest//
		// .post("https://api.telegram.org/bot" + bot.getId() +
		// "/sendMessage")//
		// .queryString("text", message)//
		// .queryString("chat_id", chatId);
		// System.out.println(request.getUrl());
		//
		// try {
		// request.asJson();
		// } catch (UnirestException e1) {
		// e1.printStackTrace();
		// // throw new RuntimeException(e1);
		// }
	}

	// public void broadcast(Channel channel) {
	//
	// }
}
