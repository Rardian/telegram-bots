package de.rardian.telegram.bot.castle.facilities;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.model.Inhabitant;

public interface CastleFacility {
	public enum CATEGORY {
		/** How well you build, repair and craft */
		BUILDING, //
		/** How well you can survive in the wilderness and find hidden spots */
		SCOUTING, //
		WOODCUTTING, //
		QUARRYING, //
		MINING
	};

	//	/** How well you produce resources */
	//	PRODUCING, //

	public void addMember(Inhabitant inhabitant) throws AlreadyAddedException;

	public void removeMember(Inhabitant inhabitant);

	public int getMemberCount();

	public String getMemberListByName();

	/** You need to define a category for your facility. */
	public CATEGORY getCategory();

	public ProcessResult2 process();

}
