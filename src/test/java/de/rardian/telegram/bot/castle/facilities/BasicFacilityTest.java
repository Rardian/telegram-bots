package de.rardian.telegram.bot.castle.facilities;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Resources;
import de.rardian.telegram.bot.model.User;

@RunWith(MockitoJUnitRunner.class)
public class BasicFacilityTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private static final int RESOURCES_CAPACITY = 5;

	@Mock
	private User user;
	@Mock
	private Castle castle;
	@Mock
	private Resources resources;

	private BasicFacility underTest;

	@Before
	public void initFacility() {
		resources = new Resources(0, RESOURCES_CAPACITY);
		underTest = Mockito.spy(new TestFacility(castle, resources));
	}

	@Test
	public void addProducerShouldStartProduction() throws Exception {
		// Run
		underTest.addMember(user);

		// Assert
		Mockito.verify(underTest).start();
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

	private class TestFacility extends BasicFacility {

		public TestFacility(Castle castle, Resources resources) {
			super(castle, resources);
		}

		@Override
		public ProcessResult process() {
			return null;
		}

		@Override
		protected void start() {
		}

	}
}
