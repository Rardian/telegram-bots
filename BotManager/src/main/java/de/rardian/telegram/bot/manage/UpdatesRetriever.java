package de.rardian.telegram.bot.manage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.rardian.telegram.bot.model.Bot;
import de.rardian.telegram.json.JSONObject;

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

	/** Timeout for longpolling */
	private int timeout = 20;
	private long offset;
	private Bot bot;

	public UpdatesRetriever forBot(Bot bot) {
		this.bot = bot;
		return this;
	}

	public void startGettingUpdates() {
		final ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(this);
	}

	@Override
	public void run() {

		while (true) {
			String url = "https://api.telegram.org/bot" + bot.getId() + "/getUpdates?offset=" + offset + "&timeout=" + timeout;
			JSONObject json = readJsonFromUrl(url);
			System.out.println(json);

			if (jsonOkay(json)) {
				List<Message> newMessages = extractMessages(json);
				System.out.println("Alle neuen Nachrichten: " + newMessages);

				// remove duplicates
				Set<Message> set = new LinkedHashSet<Message>(newMessages);
				newMessages = new ArrayList<Message>(set);

				Collections.sort(newMessages, new UpdateIdComparator());

				for (Message message : newMessages) {
					long updateId = message.getUpdate_id();
					bot.processMessage(message);
					offset = updateId + 1;
				}
			} else {
				System.out.println("Json fehlerhaft:\n" + json);
				throw new RuntimeException("Ergebnis nicht okay.");
			}
		}
	}

	private List<Message> extractMessages(JSONObject json) {
		List<Message> messages = new MessageExtractor(json).extractMessages();
		return messages;
	}

	private boolean jsonOkay(JSONObject json) {
		// TODO Auto-generated method stub
		return true;
	}

	public JSONObject readJsonFromUrl(String url) {
		InputStream is = null;
		try {
			is = new URL(url).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			System.out.println(jsonText);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} catch (IOException e) {
			e.printStackTrace();
			throw new UncheckedIOException(e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new UncheckedIOException(e);
			}
		}
	}

	private String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

}
