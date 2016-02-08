package de.rardian.telegram.bot.castle.facilities;

import static de.rardian.telegram.bot.castle.facilities.CastleFacility.CATEGORY.BUILDING;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.castle.model.InhabitantTestFactory;
import de.rardian.telegram.bot.castle.model.ResourcesManager;
import de.rardian.telegram.bot.castle.model.ResourcesManager.TYPE;
import de.rardian.telegram.bot.model.Bot;
import de.rardian.telegram.bot.model.User;

@RunWith(MockitoJUnitRunner.class)
public class ProductionFacilityTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private static final int RESOURCES_EMPTY = 0;
	private static final int RESOURCES_CAPACITY = 5;
	private static final int RESOURCES_FIELDCOUNT = RESOURCES_CAPACITY;

	@Mock
	private User user;
	@Mock
	private Bot bot;
	@Mock
	private Castle castle;
	@Mock
	private ResourcesManager resourcesManager;

	@InjectMocks
	private ProductionFacility underTest;

	@Before
	public void initCastle() {
		when(resourcesManager.getCapacity(TYPE.WOOD)).thenReturn(RESOURCES_CAPACITY);
		when(resourcesManager.getAmount(TYPE.WOOD)).thenReturn(RESOURCES_EMPTY);
		when(resourcesManager.getResourceFieldCount(TYPE.WOOD)).thenReturn(RESOURCES_FIELDCOUNT);
		underTest.setCategory(BUILDING);
		underTest.serResourceType(TYPE.WOOD);
	}

	@Test
	public void produceShouldntIncreaseResourcesOverMax() throws Exception {
		// Init
		final int neededLevel = RESOURCES_CAPACITY + 1;
		final int spaceLeft = RESOURCES_CAPACITY - 2;
		when(resourcesManager.increaseIfPossible(TYPE.WOOD, neededLevel)).thenReturn(spaceLeft);

		Inhabitant seasonedBuilder = InhabitantTestFactory.newUniqueInhabitant(1);
		while (seasonedBuilder.getSkill(BUILDING) < neededLevel) {
			seasonedBuilder.increaseXp(BUILDING);
		}
		underTest.addMember(seasonedBuilder);

		// Run
		ProcessResult2 result = underTest.process();

		// Assert
		assertThat(result.getResultInteger(ProductionFacility.RESULT_RESOURCES_INCREASE), is(spaceLeft));
	}

}
