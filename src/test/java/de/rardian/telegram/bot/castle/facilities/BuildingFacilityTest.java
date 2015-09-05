package de.rardian.telegram.bot.castle.facilities;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Resources;
import de.rardian.telegram.bot.model.User;

@RunWith(MockitoJUnitRunner.class)
public class BuildingFacilityTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private static final int RESOURCES_CAPACITY = 5;
	private static final int RESOURCES_FULL = RESOURCES_CAPACITY;
	private static final int RESOURCES_EMPTY = 0;

	@Mock
	private User user;
	@Mock
	private Castle castle;

	private BuildingFacility underTest;

	@Test
	public void noBuildingWithoutResources() throws Exception {
		// Init
		underTest = new BuildingFacility(castle, new Resources(RESOURCES_EMPTY, RESOURCES_CAPACITY));
		for (int i = 0; i < 3; i++) {
			underTest.addMember(User.newUniqueTestUser(i));
		}

		// Run
		BuildingResult result = (BuildingResult) underTest.process();

		// Assert
		assertThat(result.getBuildingProgress(), is(RESOURCES_EMPTY));
	}

	@Test
	public void builderCountSmallerThanResourceCountShouldIncreaseBuildingProgressByBuilderCount() throws Exception {
		// Init
		underTest = new BuildingFacility(castle, new Resources(RESOURCES_FULL, RESOURCES_CAPACITY));
		final int builderCount = 3;
		for (int i = 0; i < builderCount; i++) {
			underTest.addMember(User.newUniqueTestUser(i));
		}

		// Run
		BuildingResult result = (BuildingResult) underTest.process();

		// Assert
		assertThat(result.getBuildingProgress(), is(builderCount));
	}

	@Test
	public void buildingCrewReachesGoalShouldIncreasesCapacity() throws Exception {
		// Init
		Resources testResources = new Resources(RESOURCES_FULL, RESOURCES_CAPACITY);
		underTest = new BuildingFacility(castle, testResources);
		final int builderCount = RESOURCES_FULL;
		for (int i = 0; i < builderCount; i++) {
			underTest.addMember(User.newUniqueTestUser(i));
		}
		// We need a building progress of (RESOURCES_CAPACITY + 1 )* 2
		underTest.process();
		// refill
		testResources.increase(RESOURCES_CAPACITY);
		underTest.process();
		// refill
		testResources.increase(RESOURCES_CAPACITY);

		// Run
		underTest.process();

		// Assert
		assertThat(testResources.getCapacity(), is(RESOURCES_CAPACITY + 1));
	}

}
