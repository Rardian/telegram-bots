package de.rardian.telegram.bot.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ReplyKeyboardMarkupTest {

	@Test
	public void type() throws Exception {
		assertThat(ReplyKeyboardMarkup.class, notNullValue());
	}

	@Test
	public void instantiation() throws Exception {
		ReplyMarkup target = new ReplyKeyboardMarkup();
		assertThat(target, notNullValue());
	}

	@Test
	public void asJson_fullObject() throws Exception {
		// Init
		ReplyKeyboardMarkup underTest = new ReplyKeyboardMarkup();
		underTest.addButtonRow("A", "B", "C");
		underTest.addButtonRow("D", "E", "F");

		// Run
		String result = underTest.asJson();

		// Assert
		assertThat(result, is(ReplyKeyboardMarkup.TEST_JSON));
	}
}
