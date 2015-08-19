package de.rardian.telegram.bot.manage;

import java.util.Comparator;

public class UpdateIdComparator implements Comparator<Message> {

	@Override
	public int compare(Message o1, Message o2) {
		return Long.compare(o1.getUpdate_id(), o2.getUpdate_id());
	}

}
