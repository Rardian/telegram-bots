/**
 * 
 */
package de.rardian.telegram.bot.castle.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResourceDepotTest {

	@InjectMocks
	ResourceDepot underTest;

	/**
	 * Test method for {@link de.rardian.telegram.bot.castle.model.ResourceDepot#increaseIfPossible(int)}.
	 */
	@Test
	public void increaseIfPossible() throws Exception {
		//		throw new RuntimeException("not yet implemented");
		// Init

		// Run

		// Assert

	}

	@Test
	public void resourceFieldCountIsStoredMultipliedByTenButReturnedAsSet() throws Exception {
		// Run
		underTest.setResourceFieldCount(1);

		// Assert
		assertThat(underTest.getResourceFieldCount(), is(1));

	}

	@Test
	public void maxCapacityShouldBeTenTimesResourceFieldCountAfterIncrease() throws Exception {
		//		throw new RuntimeException("not yet implemented");
		//		// Init
		//		underTest.increaseResourceFieldCount(TYPE.WOOD);
		//
		//		// Run
		//		int result = underTest.getMaxCapacity(TYPE.WOOD);
		//
		//		// Assert
		//		assertThat(result, is(10));
	}

	@Test
	public void tenResourceFieldIncreasesShouldIncreaseMaxCapacity() throws Exception {
		//		throw new RuntimeException("not yet implemented");
		//		// Init
		//		for (int i = 0; i < 10; i++) {
		//			underTest.increaseResourceFieldCount(TYPE.WOOD);
		//		}
		//
		//		// Run
		//		int result = underTest.getMaxCapacity(TYPE.WOOD);
		//
		//		// Assert
		//		assertThat(result, is(15));
	}

}
