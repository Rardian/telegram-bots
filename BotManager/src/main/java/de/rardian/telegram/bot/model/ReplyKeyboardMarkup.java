package de.rardian.telegram.bot.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import de.rardian.telegram.json.JSONArray;

public class ReplyKeyboardMarkup implements ReplyMarkup {
	public static final String TEST_JSON = "[[\"A\",\"B\",\"C\"],[\"D\",\"E\",\"F\"]]";
	// Array of button rows, each represented by an Array of Strings
	private Collection<Collection<String>> buttonLayout = new ArrayList<Collection<String>>();

	public void addButtonRow(String... buttons) {
		buttonLayout.add(Arrays.asList(buttons));
	}

	public String asJson() {
		Collection<String> buttonRowStrings = new ArrayList<String>();
		for (Collection<String> buttonRows : buttonLayout) {
			JSONArray buttonsAsJson = new JSONArray(buttonRows.toArray());
			buttonRowStrings.add(buttonsAsJson.toString());
		}
		return "[" + StringUtils.join(buttonRowStrings, ",") + "]";
	}
}
