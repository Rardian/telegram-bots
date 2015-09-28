package de.rardian.telegram.bot.castle.facilities;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.model.Inhabitant;

public interface CastleFacility {
	public void addMember(Inhabitant inhabitant) throws AlreadyAddedException;

	public void removeMember(Inhabitant inhabitant);

	public int getMemberCount();

	public String getMemberListByFirstname();

	public ProcessResult process();

}
