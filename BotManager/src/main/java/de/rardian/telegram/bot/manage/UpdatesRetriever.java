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
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Iterables;

import de.rardian.telegram.json.JSONObject;

/**
 * Now: -> getUpdates -> Add Messages to List -> Adjust UpdateID => In case of
 * error messages are lost! <BR>
 * New: -> getUpdates -> process messages -> adjust id on success => only update
 * id on success, in case of error, set id to last success.
 */
public class UpdatesRetriever implements Runnable {
	// store Bot-Credentials
	// store last update-id

	private int amount = 10;
	private TimeUnit timeUnit = TimeUnit.SECONDS;
	private ConcurrentLinkedQueue<Message> messagesContainer;
	private String botId;
	private long offset;

	/** Tells the UpdatesRetriever to look for updates every amount seconds. */
	public UpdatesRetriever everySeconds(int amount) {
		this.amount = amount;
		timeUnit = TimeUnit.SECONDS;
		return this;
	}

	public UpdatesRetriever forBot(String botId) {
		this.botId = botId;
		return this;
	}

	public ConcurrentLinkedQueue<Message> startGettingUpdates() {
		messagesContainer = new ConcurrentLinkedQueue<Message>();
		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(this, 0, amount, timeUnit);
		return messagesContainer;
	}

	@Override
	public void run() {
		// (re)calculate offset from the last retrieved updates
		// pay attention to instantiating an UpdateService with every call
		// use globally stored Bot-Credentials and update-id +1 
		String url = "https://api.telegram.org/bot" + botId + "/getUpdates?offset=" + offset;
		JSONObject json = readJsonFromUrl(url);
		//		System.out.println(json);

		if (jsonOkay(json)) {
			List<Message> newMessages = extractMessages(json);
			System.out.println("Alle neuen Nachrichten: " + newMessages);

			// remove duplicates
			Set<Message> set = new LinkedHashSet<Message>(newMessages);
			newMessages = new ArrayList<Message>(set);

			// order List by update-id
			Collections.sort(newMessages, new Comparator<Message>() {

				@Override
				public int compare(Message o1, Message o2) {
					return Long.compare(o1.getUpdate_id(), o2.getUpdate_id());
				}
			});

			// update update-id
			if (newMessages.size() > 0) {
				Message lastMessage = Iterables.getLast(newMessages);
				offset = lastMessage.getUpdate_id() + 1;

				messagesContainer.addAll(newMessages);
			}
			System.out.println("UpdatesRetriever meldet: Nicht verarbeitete Nachrichten: " + messagesContainer);
		} else {
			System.out.println("Json fehlerhaft:\n" + json);
			throw new RuntimeException("Ergebnis nicht okay.");
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
