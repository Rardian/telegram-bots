package de.rardian.telegram.bot.rardian;

import de.rardian.telegram.bot.manage.Message;
import de.rardian.telegram.bot.manage.UserManager;
import de.rardian.telegram.bot.model.Bot;
import de.rardian.telegram.bot.model.User;

public class RardianBot implements Bot {

	private static final String ID = "123030600:AAHn8CC4Q7PMvvdEGOiqmFYCZVcgHam_8uo";
	private UserManager userManager;
	private MessageReply reply;

	public void setUserManager(UserManager manager) {
		this.userManager = manager;
	}

	public void setMessageReply(MessageReply mr) {
		reply = mr;
	}

	@Override
	public String getId() {
		return ID;
	}

	public void processMessage(Message message) {
		User user = message.getFrom();

		if (userManager.isUserKnown(user)) {
			getMessageReply(message).answer("Willkommen zurück, " + user.getFirstName() + "!");
		} else {
			userManager.registerUser(user);
			getMessageReply(message).answer("Herzlich Willkommen, " + user.getFirstName() + ", schön dich kennenzulernen!");
		}
	}

	private MessageReply getMessageReply(Message message) {
		if (reply == null) {
			reply = new MessageReply().asBot(this).toMessage(message);
		}
		return reply;
	}
}
