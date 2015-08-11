package de.rardian.telegram.bot.rardian;

import java.io.IOException;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.client.fluent.Request;

import de.rardian.telegram.bot.manage.Message;
import de.rardian.telegram.bot.manage.UserManager;
import de.rardian.telegram.bot.model.User;

public class RardianBot {

	public final String ID = "123030600:AAHn8CC4Q7PMvvdEGOiqmFYCZVcgHam_8uo";
	private UserManager userManager;

	public void setUserManager(UserManager manager) {
		this.userManager = manager;

	}

	public void processMessage(Message message) {
		User user = message.getFrom();

		if (userManager.isUserKnown(user)) {
			//			new MessageReply(message).answerToMessage("Willkommen zurück!");
		} else {
			userManager.registerUser(user);
			//			new MessageReply(message).answerToMessage("Herzlich Willkommen!");
		}

	}

	private void answerToMessage(Message message) {
		String answer = "Das kam bei mir an: " + message.getText();
		long chatId = message.getChat().getId();

		try {
			String answerEncoded = new URLCodec().encode(answer);
			String url = "https://api.telegram.org/bot" + ID + "/sendMessage?text=" + answerEncoded + "&chat_id=" + chatId;
			System.out.println(url);
			Request.Get(url).execute().returnContent();
		} catch (IOException | EncoderException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
