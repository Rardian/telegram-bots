package de.rardian.telegram.bot.castle.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ResourcesManagerTest {

	private ResourcesManager underTest;

	@Before
	public void initResources() {
		underTest = new ResourcesManager(0, 5, 1);
	}

	@Test
	public void maxCapacityShouldBeTenTimesResourceFieldCount() throws Exception {
		// Run
		int result = underTest.getMaxCapacity(ResourcesManager.TYPE.WOOD);

		// Assert
		assertThat(result, is(10));
	}

	@Test
	public void maxCapacityShouldBeTenTimesResourceFieldCountAfterIncrease() throws Exception {
		// Init
		underTest.increaseResourceFieldCount(ResourcesManager.TYPE.WOOD);

		// Run
		int result = underTest.getMaxCapacity(ResourcesManager.TYPE.WOOD);

		// Assert
		assertThat(result, is(10));
	}

	@Test
	public void tenResourceFieldIncreasesShouldIncreaseMaxCapacity() throws Exception {
		// Init
		for (int i = 0; i < 10; i++) {
			underTest.increaseResourceFieldCount(ResourcesManager.TYPE.WOOD);
		}

		// Run
		int result = underTest.getMaxCapacity(ResourcesManager.TYPE.WOOD);

		// Assert
		assertThat(result, is(15));
	}

}
