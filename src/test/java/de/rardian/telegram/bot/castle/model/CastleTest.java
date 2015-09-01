package de.rardian.telegram.bot.castle.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.model.User;

@RunWith(MockitoJUnitRunner.class)
public class CastleTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private User user;
	@Mock
	private ProductionController productionController;

	private Castle underTest;

	@Before
	public void initCastle() {
		underTest = new Castle();
		underTest.setProduction(productionController);
	}

	//	@Test
	//	public void getStatusAsString() throws Exception {
	//		throw new RuntimeException("not yet implemented");
	//		// Init
	//
	//		// Run
	//
	//		// Assert
	//
	//	}

	@Test
	public void addProducer() throws Exception {
		// Run
		underTest.addProducer(user);

		// Assert
		assertThat(underTest.getProducerCount(), is(1));
	}

	@Test
	public void addProducerShouldStartProduction() throws Exception {
		// Run
		underTest.addProducer(user);

		// Assert
		Mockito.verify(productionController).start();
	}

	@Test
	public void shouldntAddProducerTwice() throws Exception {
		// Init
		underTest.addProducer(User.newTestUser());
		thrown.expect(AlreadyAddedException.class);
		thrown.expectMessage("user is already producing");

		// Run / Assert
		underTest.addProducer(User.newTestUser());
	}

	//	@Test
	//	public void addInhabitant() throws Exception {
	//		throw new RuntimeException("not yet implemented");
	//		// Init
	//
	//		// Run
	//
	//		// Assert
	//
	//	}

}
