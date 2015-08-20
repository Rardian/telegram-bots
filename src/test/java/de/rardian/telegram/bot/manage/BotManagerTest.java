package de.rardian.telegram.bot.manage;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class BotManagerTest {
	private BotManager underTest;

	@Before
	public void setupBot() {
		underTest = new BotManager();
	}

	@Test
	public void type() throws Exception {
		assertThat(BotManager.class, notNullValue());
	}

	@Test
	public void instantiation() throws Exception {
		assertThat(underTest, notNullValue());
	}
}
