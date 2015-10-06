package de.rardian.telegram.bot.castle.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ResourcesTest {

	private Resources underTest;

	@Before
	public void initResources() {
		underTest = new Resources(0, 5, 1);
	}

	@Test
	public void maxCapacityShouldBeTenTimesResourceFieldCount() throws Exception {
		// Run
		int result = underTest.getMaxCapacity();

		// Assert
		assertThat(result, is(10));
	}

	@Test
	public void maxCapacityShouldBeTenTimesResourceFieldCountAfterIncrease() throws Exception {
		// Init
		underTest.increaseResourceFieldCount();

		// Run
		int result = underTest.getMaxCapacity();

		// Assert
		assertThat(result, is(10));
	}

	@Test
	public void tenResourceFieldIncreasesShouldIncreaseMaxCapacity() throws Exception {
		// Init
		for (int i = 0; i < 10; i++) {
			underTest.increaseResourceFieldCount();
		}

		// Run
		int result = underTest.getMaxCapacity();

		// Assert
		assertThat(result, is(20));
	}

}
