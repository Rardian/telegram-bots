package de.rardian.telegram.bot.castle.facilities;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Resources;
import de.rardian.telegram.bot.castle.facilities.ProductionFacility;
import de.rardian.telegram.bot.model.User;

@RunWith(MockitoJUnitRunner.class)
public class ProductionFacilityTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private static final int RESOURCES_CAPACITY = 5;

	@Mock
	private User user;
	@Mock
	private Castle castle;

	private ProductionFacility underTest;

	@Before
	public void initCastle() {
		underTest = new ProductionFacility();
		underTest.forCastle(castle);
		underTest.withResources(new Resources(0, RESOURCES_CAPACITY));
	}

	@Test
	public void addProducerShouldStartProduction() throws Exception {
		// Run
		underTest.addMember(user);

		// Assert
		assertNotNull(underTest.getExecutorService());
	}

	@Test
	public void shouldntAddProducerTwice() throws Exception {
		// Init
		underTest.addMember(User.newIdentTestUser());
		thrown.expect(AlreadyAddedException.class);
		thrown.expectMessage("user is already producing");

		// Run / Assert
		underTest.addMember(User.newIdentTestUser());
	}

	@Test
	public void produceShouldntIncreaseResourcesOverMax() throws Exception {
		// Init
		for (int i = 0; i < 6; i++) {
			underTest.addMember(User.newUniqueTestUser(i));
		}

		// Run
		ProductionResult result = (ProductionResult) underTest.process();

		// Assert
		assertThat(result.getResources(), is(RESOURCES_CAPACITY));
	}

	@Test
	public void produceShouldIncreaseResourcesIfCapacityIsLeft() throws Exception {
		// Init
		for (int i = 0; i < 3; i++) {
			underTest.addMember(User.newUniqueTestUser(i));
		}

		// Run
		ProductionResult result = (ProductionResult) underTest.process();

		// Assert
		assertThat(result.getResources(), is(3));
	}

	@Test
	public void produceShouldntIncreaseResourcesOverMaxIfThereAlreadyAreResources() throws Exception {
		// Init
		for (int i = 0; i < 3; i++) {
			underTest.addMember(User.newUniqueTestUser(i));
		}
		underTest.process();

		// Run
		ProductionResult result = (ProductionResult) underTest.process();

		// Assert
		assertThat(result.getResources(), is(RESOURCES_CAPACITY));
	}

}
