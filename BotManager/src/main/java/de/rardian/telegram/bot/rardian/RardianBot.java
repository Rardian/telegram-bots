package de.rardian.telegram.bot.rardian;

import java.io.IOException;
import java.util.AbstractQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.client.fluent.Request;

import de.rardian.telegram.bot.manage.Message;

public class RardianBot implements Runnable {

	public static final String ID = "123030600:AAHn8CC4Q7PMvvdEGOiqmFYCZVcgHam_8uo";

	private AbstractQueue<Message> messageContainer;

	public RardianBot(ConcurrentLinkedQueue<Message> messageContainer) {
		this.messageContainer = messageContainer;
	}

	public void start() {
		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(this, 0, 2, TimeUnit.SECONDS);
	}

	@Override
	public void run() {
		Message message = messageContainer.poll();
		if (message != null) {
			System.out.println("RardianBot meldet: Message '" + message + "' angekommen.");
			answerToMessage(message);
		} else {
			System.out.println("RardianBot meldet: Keine neuen Nachrichten empfangen.");
		}
	}

	private void answerToMessage(Message message) {
		String answer = "Das kam bei mir an: " + message.getText();
		String chatId = "8039535";
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
