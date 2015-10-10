package de.rardian.telegram.bot.castle.facilities;

import static de.rardian.telegram.bot.castle.facilities.CastleFacility.CATEGORY.BUILDING;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.castle.model.InhabitantTestFactory;
import de.rardian.telegram.bot.castle.model.Resources;
import de.rardian.telegram.bot.model.Bot;
import de.rardian.telegram.bot.model.User;

@RunWith(MockitoJUnitRunner.class)
public class BuildingFacilityTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private static final int RESOURCES_CAPACITY = 5;
	private static final int RESOURCES_FIELDCOUNT = RESOURCES_CAPACITY;
	private static final int RESOURCES_FULL = RESOURCES_CAPACITY;
	private static final int RESOURCES_EMPTY = 0;

	@Mock
	private Bot bot;
	@Mock
	private User user;
	@Mock
	private Castle castle;

	private BuildingFacility underTest;

	@Test
	public void ShouldNotUseMoreResourcesThanNeededOnFinishing() throws Exception {
		// Init
		Resources testResources = new Resources(RESOURCES_FULL, RESOURCES_CAPACITY, RESOURCES_FIELDCOUNT);
		underTest = new BuildingFacility(castle, testResources, bot);
		Inhabitant seasonedBuilder = InhabitantTestFactory.newUniqueInhabitant(1);

		// levelup
		final int neededLevel = 5;
		while (seasonedBuilder.getSkill(BUILDING) < neededLevel) {
			seasonedBuilder.increaseXp(BUILDING);
		}
		underTest.addMember(seasonedBuilder);
		System.out.println(seasonedBuilder.getSkill(BUILDING));

		// We need to finish the building needing (RESOURCES_CAPACITY + 1) * 2 building steps
		underTest.process();
		// refill
		testResources.increaseIfPossible(RESOURCES_CAPACITY);
		underTest.process();
		// refill
		testResources.increaseIfPossible(RESOURCES_CAPACITY);

		// Run
		underTest.process();

		// Assert that for the last build step only 2 resources are used
		assertThat(testResources.getActual(), is(3));
	}

	@Test
	public void noBuildingWithoutResources() throws Exception {
		// Init
		underTest = new BuildingFacility(castle, new Resources(RESOURCES_EMPTY, RESOURCES_CAPACITY, RESOURCES_FIELDCOUNT), bot);
		for (int i = 0; i < 3; i++) {
			underTest.addMember(InhabitantTestFactory.newUniqueInhabitant(i));
		}

		// Run
		ProcessResult2 result = underTest.process();

		// Assert
		assertThat(result.getResultInteger(BuildingFacility.RESULT_BUILDING_PROGRESS), is(0));
	}

	@Test
	public void builderCountSmallerThanResourceCountShouldIncreaseBuildingProgressByBuilderCount() throws Exception {
		// Init
		underTest = new BuildingFacility(castle, new Resources(RESOURCES_FULL, RESOURCES_CAPACITY, RESOURCES_FIELDCOUNT), bot);
		final int builderCount = 3;
		for (int i = 0; i < builderCount; i++) {
			underTest.addMember(InhabitantTestFactory.newUniqueInhabitant(i));
		}

		// Run
		ProcessResult2 result = underTest.process();

		// Assert
		assertThat(result.getResultInteger(BuildingFacility.RESULT_BUILDING_PROGRESS), is(builderCount));
	}

	@Test
	public void buildingCrewReachesGoalShouldIncreaseCapacity() throws Exception {
		// Init
		Resources testResources = new Resources(RESOURCES_FULL, RESOURCES_CAPACITY, RESOURCES_FIELDCOUNT);
		underTest = new BuildingFacility(castle, testResources, bot);
		final int builderCount = RESOURCES_FULL;
		for (int i = 0; i < builderCount; i++) {
			underTest.addMember(InhabitantTestFactory.newUniqueInhabitant(i));
		}
		// We need a building progress of (RESOURCES_CAPACITY + 1) * 2
		underTest.process();
		// refill
		testResources.increaseIfPossible(RESOURCES_CAPACITY);
		underTest.process();
		// refill
		testResources.increaseIfPossible(RESOURCES_CAPACITY);

		// Run
		underTest.process();

		// Assert
		assertThat(testResources.getCapacity(), is(RESOURCES_CAPACITY + 1));
	}

}
