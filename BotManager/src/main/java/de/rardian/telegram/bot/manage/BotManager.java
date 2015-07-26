package de.rardian.telegram.bot.manage;

import java.util.concurrent.ConcurrentLinkedQueue;

import de.rardian.telegram.bot.rardian.RardianBot;

public class BotManager {

	public static void main(String[] args) {
		new BotManager().start();
	}

	private void start() {
		initBot();
		ConcurrentLinkedQueue<Message> messageContainer = new UpdatesRetriever().everySeconds(30).forBot(RardianBot.ID).startGettingUpdates();
		new RardianBot(messageContainer).start();
	}

	private void initBot() {
	}

}
