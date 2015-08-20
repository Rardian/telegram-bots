package de.rardian.telegram.bot.manage;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

import de.rardian.telegram.bot.model.Bot;

/**
 * new Strategy:<BR>
 * - We have a single task, that polls messages via longpolling<BR>
 * - For every received message bot.processMessage() is called<BR>
 * - After all messages are processed the longpolling starts again<BR>
 * - Multi-threading for processing the message seems a bad idea since the bot
 * would need to reorder the messages
 * 
 */
public class UpdatesRetriever implements Runnable {
	private static final String BASE_URL = "https://api.telegram.org/bot";
	private String urlForPolling;

	/** Timeout for longpolling */
	private int timeout = 60;
	private long offset = 0;
	private Bot bot;

	public UpdatesRetriever forBot(Bot bot) {
		this.bot = bot;
		urlForPolling = BASE_URL + bot.getId() + "/getUpdates";
		return this;
	}

	public void startGettingUpdates() {
		final ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(this);
	}

	@Override
	public void run() {
		while (true) {
			HttpRequest request = Unirest.get(urlForPolling)//
					.queryString("timeout", timeout)//
					.queryString("offset", offset);

			try {
				JSONObject json = request.asJson().getBody().getObject();
				System.out.println(json);

				if (jsonOkay(json)) {
					List<Message> newMessages = extractMessages(json);
					System.out.println("Alle neuen Nachrichten: " + newMessages);

					//					// remove duplicates
					//					Set<Message> set = new LinkedHashSet<>(newMessages);
					//					newMessages = new ArrayList<>(set);
					//					
					//					Collections.sort(newMessages, new UpdateIdComparator());

					for (Message message : newMessages) {
						long updateId = message.getUpdate_id();
						bot.processMessage(message);
						// only after successful processing may the offset be increased
						offset = updateId + 1;
					}
				} else {
					System.out.println("Json fehlerhaft:\n" + json.toString(2));
					throw new RuntimeException("Ergebnis nicht okay.");
				}
			} catch (UnirestException e) {
				e.printStackTrace();
				throw new RuntimeException("Ergebnis nicht okay.", e);
			}

		}
	}

	private List<Message> extractMessages(JSONObject json) {
		List<Message> messages = new MessageExtractor(json).extractMessages();
		return messages;
	}

	private boolean jsonOkay(JSONObject json) {
		return json.getBoolean("ok");
	}

}
