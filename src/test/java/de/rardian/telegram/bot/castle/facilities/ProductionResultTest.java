package de.rardian.telegram.bot.castle.facilities;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import de.rardian.telegram.bot.castle.facilities.ProductionResult;

@RunWith(MockitoJUnitRunner.class)
public class ProductionResultTest {
	private static final int TEST_INCREASE = 23;

	@Test
	public void getProductionIncrease() throws Exception {
		// Init
		ProductionResult productionResult = new ProductionResult(TEST_INCREASE, TEST_INCREASE);

		// Run
		int result = productionResult.getProductionIncrease();

		// Assert
		assertThat(result, is(TEST_INCREASE));
	}

	@Test
	public void testString() throws Exception {
		// Init
		ProductionResult productionResult = new ProductionResult(TEST_INCREASE, TEST_INCREASE);

		// Run
		String result = productionResult.toString();

		// Assert
		assertThat(result, is(TEST_INCREASE + " Einheiten produziert"));
	}

}
