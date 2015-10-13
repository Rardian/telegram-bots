package de.rardian.telegram.bot.castle.facilities;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.InhabitantTestFactory;
import de.rardian.telegram.bot.castle.model.ResourcesManager;
import de.rardian.telegram.bot.model.Bot;
import de.rardian.telegram.bot.model.User;

@RunWith(MockitoJUnitRunner.class)
public class ProductionFacilityTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private static final int RESOURCES_CAPACITY = 5;
	private static final int RESOURCES_FIELDCOUNT = RESOURCES_CAPACITY;

	@Mock
	private User user;
	@Mock
	private Bot bot;
	@Mock
	private Castle castle;

	private ProductionFacility underTest;

	@Before
	public void initCastle() {
		underTest = new ProductionFacility(bot, castle, new ResourcesManager(0, RESOURCES_CAPACITY, RESOURCES_FIELDCOUNT),
				ResourcesManager.TYPE.WOOD);
	}

	@Test
	public void produceShouldntIncreaseResourcesOverMax() throws Exception {
		// Init
		for (int i = 0; i < 6; i++) {
			underTest.addMember(InhabitantTestFactory.newUniqueInhabitant(i));
		}

		// Run
		ProcessResult2 result = underTest.process();

		// Assert
		assertThat(result.getResultInteger(ProductionFacility.RESULT_RESOURCES_ACTUAL), is(RESOURCES_CAPACITY));
	}

	@Test
	public void produceShouldIncreaseResourcesIfCapacityIsLeft() throws Exception {
		// Init
		for (int i = 0; i < 3; i++) {
			underTest.addMember(InhabitantTestFactory.newUniqueInhabitant(i));
		}

		// Run
		ProcessResult2 result = underTest.process();

		// Assert
		assertThat(result.getResultInteger(ProductionFacility.RESULT_RESOURCES_ACTUAL), is(3));
	}

	@Test
	public void produceShouldntIncreaseResourcesOverMaxIfThereAlreadyAreResources() throws Exception {
		// Init
		for (int i = 0; i < 3; i++) {
			underTest.addMember(InhabitantTestFactory.newUniqueInhabitant(i));
		}
		underTest.process();

		// Run
		ProcessResult2 result = underTest.process();

		// Assert
		assertThat(result.getResultInteger(ProductionFacility.RESULT_RESOURCES_ACTUAL), is(RESOURCES_CAPACITY));
	}

}
