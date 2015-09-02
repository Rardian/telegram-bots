package de.rardian.telegram.bot.model;

import java.util.Arrays;

import org.json.JSONObject;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;

/**
 * Store User-Objeccts from Json like that:
 * {\"id\":8039535,\"first_name\":\"Bjoern\",\"username\":\"BjoernO\"}
 * 
 * @author Rardian
 *
 */
public class User implements Chat {
	public static final String JSON_ID = "id";
	public static final String JSON_FIRSTNAME = "first_name";
	public static final String JSON_LASTNAME = "last_name";
	public static final String JSON_USERNAME = "username";

	private long id;
	private String firstName;
	private String lastName;
	private String userName;

	public User() {

	}

	@VisibleForTesting
	User(long id, String firstName, String lastName, String userName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
	}

	@VisibleForTesting
	public static User newIdentTestUser() {
		return new User(8039535, "Vorname", "Nachname", "Username");
	}

	public User fillWithJson(JSONObject json) {
		setId(json.getLong(JSON_ID));
		setFirstName(json.getString(JSON_FIRSTNAME));

		String[] keys = JSONObject.getNames(json);

		if (Arrays.asList(keys).contains(JSON_LASTNAME)) {
			setLastName(json.getString(JSON_LASTNAME));
		}
		if (Arrays.asList(keys).contains(JSON_USERNAME)) {
			setUserName(json.getString(JSON_USERNAME));
		}

		return this;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).addValue(id).addValue(firstName).addValue(lastName).addValue(userName).toString();
	}
}
