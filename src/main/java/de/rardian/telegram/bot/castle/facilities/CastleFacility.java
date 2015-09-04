package de.rardian.telegram.bot.castle.facilities;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.model.User;

public interface CastleFacility {
	public void addMember(User newMember) throws AlreadyAddedException;

	public void removeMember(User user);

	public int getMemberCount();

	public String getMemberListByFirstname();

	public ProcessResult process();

}
