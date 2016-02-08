package de.rardian.telegram.bot.castle.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import de.rardian.telegram.bot.castle.CastleBot;
import de.rardian.telegram.bot.model.User;

//@RunWith(MockitoJUnitRunner.class)
public class CastleTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private CastleBot bot;
	@Mock
	private User user;

	private Castle underTest;

	@Before
	public void initCastle() {
		underTest = new Castle(bot, null);
	}

	/*
	@Test
	public void getStatusAsStringWithTwoInhabitants() throws Exception {
		// Init
		underTest.addInhabitant(User.newUniqueTestUser(1));
		underTest.addInhabitant(User.newUniqueTestUser(2));
	
		// Run
		String actual = underTest.getStatusAsString();
	
		// Assert
		String expected = "Die Burg ist in gutem Zustand.\n"//
				+ "Bewohner: 2 (Vorname1, Vorname2)\n"//
				+ "-> Produzenten: 0 ()\n"//
				+ "-> Baumeister: 0 ()\n"//
				+ "-> Scouts: 0 ()\n"//
				+ "Ressourcen: 0 (von 5)\n"//
				+ "Bauvorhaben: 0 (von 12)";
		// FIXME Text anpassen		assertThat(actual, is(expected));
	}
	*/

	/*
	@Test
	public void getStatusAsStringWithTwoProducers() throws Exception {
		// Init
		underTest.addWorkerFor(CastleFacility.CATEGORY.WOODCUTTING, InhabitantTestFactory.newUniqueInhabitant(1));
		underTest.addWorkerFor(CastleFacility.CATEGORY.WOODCUTTING, InhabitantTestFactory.newUniqueInhabitant(2));
	
		// Run
		String actual = underTest.getStatusAsString();
	
		// Assert
		String expected = "Die Burg ist in gutem Zustand.\n"//
				+ "Bewohner: 0 ()\n"//
				+ "-> Produzenten: 2 (Vorname1, Vorname2)\n"//
				+ "-> Baumeister: 0 ()\n"//
				+ "-> Scouts: 0 ()\n"//
				+ "Ressourcen: 0 (von 5)\n"//
				+ "Bauvorhaben: 0 (von 12)";
		// FIXME Text anpassen assertThat(actual, is(expected));
	}
	*/

	//	@Test
	//	public void addProducer() throws Exception {
	//		// Run
	//		underTest.addProducer(user);
	//
	//		// Assert
	//		assertThat(underTest.getProducerCount(), is(1));
	//	}

	//	@Test
	//	public void addProducerShouldStartProduction() throws Exception {
	//		// Run
	//		underTest.addProducer(user);
	//
	//		// Assert
	//		Mockito.verify(productionController).start();
	//	}
	//
	//	@Test
	//	public void shouldntAddProducerTwice() throws Exception {
	//		// Init
	//		underTest.addProducer(User.newIdentTestUser());
	//		thrown.expect(AlreadyAddedException.class);
	//		thrown.expectMessage("user is already producing");
	//
	//		// Run / Assert
	//		underTest.addProducer(User.newIdentTestUser());
	//	}
	//
	//	@Test
	//	public void produceShouldntIncreaseResourcesOverMax() throws Exception {
	//		// Init
	//		for (int i = 0; i < 6; i++) {
	//			underTest.addProducer(User.newUniqueTestUser(i));
	//		}
	//
	//		// Run
	//		ProductionResult result = underTest.produce();
	//
	//		// Assert
	//		assertThat(result.getResources(), is(5));
	//	}
	//
	//	@Test
	//	public void produceShouldIncreaseResourcesIfCapacityIsLeft() throws Exception {
	//		// Init
	//		for (int i = 0; i < 3; i++) {
	//			underTest.addProducer(User.newUniqueTestUser(i));
	//		}
	//
	//		// Run
	//		ProductionResult result = underTest.produce();
	//
	//		// Assert
	//		assertThat(result.getResources(), is(3));
	//	}
	//
	//	@Test
	//	public void produceShouldntIncreaseResourcesOverMaxIfThereAlreadyAreResources() throws Exception {
	//		// Init
	//		for (int i = 0; i < 3; i++) {
	//			underTest.addProducer(User.newUniqueTestUser(i));
	//		}
	//		underTest.produce();
	//
	//		// Run
	//		ProductionResult result = underTest.produce();
	//
	//		// Assert
	//		assertThat(result.getResources(), is(5));
	//	}

	/*
	@Test
	public void shouldAddUserAsInhabitant() throws Exception {
		// Init
		underTest.addInhabitant(user);
	
		// Run
		Inhabitant result = underTest.getInhabitantFor(user);
	
		// Assert
		assertNotNull(result);
	}
	*/
}
