package de.rardian.telegram.bot.castle.model;

import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import de.rardian.telegram.bot.castle.model.ResourcesManager.TYPE;
import de.rardian.telegram.bot.domain.ResourceDepotRepository;

@RunWith(MockitoJUnitRunner.class)
public class ResourcesManagerTest {
	@Mock
	private ResourceDepotRepository resourceDepotRepository;
	@Mock
	private AutowireCapableBeanFactory beanFactory;
	@Mock
	private ResourceDepot resourceDepot;

	@InjectMocks
	private ResourcesManager underTest;

	@Before
	public void initResources() {
		underTest.initialize(0, 5, 1);
		Mockito.when(resourceDepotRepository.findOne(TYPE.WOOD)).thenReturn(Optional.of(resourceDepot));
	}

	@Test
	public void delegateMaxCapacity() throws Exception {
		// Run
		underTest.getMaxCapacity(TYPE.WOOD);

		// Assert
		verify(resourceDepot).getMaxCapacity();
	}

}
