package de.rardian.telegram.bot.castle.facilities;

import static de.rardian.telegram.bot.castle.facilities.CastleFacility.CATEGORY.BUILDING;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.rardian.telegram.bot.castle.facilities.CastleFacility.CATEGORY;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.castle.model.InhabitantTestFactory;
import de.rardian.telegram.bot.castle.model.ResourcesManager;
import de.rardian.telegram.bot.castle.model.ResourcesManager.TYPE;
import de.rardian.telegram.bot.model.Bot;
import de.rardian.telegram.bot.model.User;

@RunWith(MockitoJUnitRunner.class)
public class BuildingFacilityTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private static final int RESOURCES_CAPACITY = 5;
	private static final int RESOURCES_CAPACITY_HIGHER = 50;
	private static final int RESOURCES_FIELDCOUNT = RESOURCES_CAPACITY;
	private static final int RESOURCES_FULL = RESOURCES_CAPACITY;
	private static final int RESOURCES_EMPTY = 0;

	@Mock
	private Bot bot;
	@Mock
	private User user;
	@Mock
	private Castle castle;
	@Mock
	private ResourcesManager resourceManager;

	@InjectMocks
	private BuildingFacility underTest;

	@Test
	public void ShouldNotUseMoreResourcesThanNeededOnFinishing() throws Exception {
		// Init
		when(resourceManager.getCapacity(TYPE.WOOD)).thenReturn(RESOURCES_CAPACITY);
		when(resourceManager.getMaxCapacity(TYPE.WOOD)).thenReturn(RESOURCES_CAPACITY_HIGHER);
		when(resourceManager.getAmount(TYPE.WOOD)).thenReturn(RESOURCES_FULL);
		when(resourceManager.getResourceFieldCount(TYPE.WOOD)).thenReturn(RESOURCES_FIELDCOUNT);

		final int overallBuildingProgress = (RESOURCES_CAPACITY + 1) * 2;
		final int expectedReduce = 2;
		underTest.setProgress(overallBuildingProgress - expectedReduce);

		Inhabitant seasonedBuilder = InhabitantTestFactory.newUniqueInhabitant(1);
		final int neededLevel = 5;
		while (seasonedBuilder.getSkill(BUILDING) < neededLevel) {
			seasonedBuilder.increaseXp(BUILDING);
		}
		underTest.addMember(seasonedBuilder);

		// Run
		underTest.process();

		// Assert
		verify(resourceManager).reduce(TYPE.WOOD, expectedReduce);
	}

	@Test
	public void noBuildingWithoutResources() throws Exception {
		// Init
		when(resourceManager.getCapacity(ResourcesManager.TYPE.WOOD)).thenReturn(RESOURCES_CAPACITY);
		when(resourceManager.getMaxCapacity(ResourcesManager.TYPE.WOOD)).thenReturn(RESOURCES_CAPACITY_HIGHER);
		when(resourceManager.getAmount(ResourcesManager.TYPE.WOOD)).thenReturn(Integer.valueOf(RESOURCES_EMPTY));

		final Inhabitant builder = InhabitantTestFactory.newUniqueInhabitant(1);
		builder.increaseXp(CATEGORY.BUILDING);
		underTest.addMember(builder);

		// Run
		ProcessResult2 result = underTest.process();

		// Assert
		assertThat(result.getResultInteger(BuildingFacility.RESULT_BUILDING_PROGRESS), is(0));
	}

	@Test
	public void eachBuilderBuilds() throws Exception {
		// Init
		when(resourceManager.getCapacity(TYPE.WOOD)).thenReturn(RESOURCES_CAPACITY);
		when(resourceManager.getMaxCapacity(TYPE.WOOD)).thenReturn(RESOURCES_CAPACITY_HIGHER);
		when(resourceManager.getAmount(TYPE.WOOD)).thenReturn(RESOURCES_FULL);
		when(resourceManager.getResourceFieldCount(TYPE.WOOD)).thenReturn(RESOURCES_FIELDCOUNT);

		final int builderCount = 3;
		for (int i = 0; i < builderCount; i++) {
			underTest.addMember(InhabitantTestFactory.newUniqueInhabitant(i));
		}

		// Run
		underTest.process();

		// Assert
		verify(resourceManager, times(builderCount)).reduce(TYPE.WOOD, 1);
	}

	@Test
	public void buildingCrewReachesGoalShouldIncreaseCapacity() throws Exception {
		// Init
		when(resourceManager.getCapacity(TYPE.WOOD)).thenReturn(RESOURCES_CAPACITY);
		when(resourceManager.getMaxCapacity(TYPE.WOOD)).thenReturn(RESOURCES_CAPACITY_HIGHER);
		when(resourceManager.getAmount(TYPE.WOOD)).thenReturn(RESOURCES_FULL);
		when(resourceManager.getResourceFieldCount(TYPE.WOOD)).thenReturn(RESOURCES_FIELDCOUNT);

		final int overallBuildingProgress = (RESOURCES_CAPACITY + 1) * 2;
		underTest.setProgress(overallBuildingProgress - 1);

		Inhabitant builder = InhabitantTestFactory.newUniqueInhabitant(1);
		underTest.addMember(builder);

		// Run
		underTest.process();

		// Assert
		verify(resourceManager).increaseCapacity(ResourcesManager.TYPE.WOOD);
	}

}
