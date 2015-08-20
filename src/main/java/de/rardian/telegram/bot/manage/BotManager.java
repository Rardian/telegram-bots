package de.rardian.telegram.bot.manage;

import de.rardian.telegram.bot.rardian.RardianBot;

public class BotManager {

	public static void main(String[] args) {
		new BotManager().start();
	}

	private void start() {
		RardianBot bot = new RardianBot();
		new UpdatesRetriever().forBot(bot).startGettingUpdates();
	}
}
