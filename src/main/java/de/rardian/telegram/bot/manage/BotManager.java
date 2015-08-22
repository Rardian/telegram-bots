package de.rardian.telegram.bot.manage;

import de.rardian.telegram.bot.castle.CastleBot;

public class BotManager {

	public static void main(String[] args) {
		new BotManager().start();
	}

	private void start() {
		CastleBot bot = new CastleBot();
		new UpdatesRetriever().forBot(bot).startGettingUpdates();
	}
}
