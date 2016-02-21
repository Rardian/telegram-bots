package de.rardian.telegram.bot.castle.facilities;

import static de.rardian.telegram.bot.castle.facilities.CastleFacility.CATEGORY.BUILDING;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.common.collect.ImmutableMap;

import de.rardian.telegram.bot.castle.facilities.building.Blueprint;
import de.rardian.telegram.bot.castle.facilities.building.LagerProject;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.castle.model.Project;
import de.rardian.telegram.bot.castle.model.ResourceAmount;
import de.rardian.telegram.bot.castle.model.ResourcesManager;
import de.rardian.telegram.bot.domain.ProjectRepository;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectManagerTest {
	private @Autowired AutowireCapableBeanFactory beanFactory;

	@Mock
	private ApplicationContext appContext;
	@Mock
	private ProjectRepository projectRepository;
	@Mock
	private ResourcesManager resourcesManager;
	@Mock
	private Inhabitant inhabitant;
	@Mock
	private Project project;

	@InjectMocks
	private ProjectManager projectManager;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void projectInProgress_notInProgress() throws Exception {
		// Init
		when(projectRepository.count())//
				.thenReturn(Long.valueOf(0));

		// Run
		boolean result = projectManager.projectInProgress();

		// Assert
		assertFalse(result);
	}

	@Test
	public void projectInProgress_inProgress() throws Exception {
		// Init
		when(projectRepository.count())//
				.thenReturn(Long.valueOf(1));

		// Run
		boolean result = projectManager.projectInProgress();

		// Assert
		assertTrue(result);
	}

	@Test
	public void workOnCurrentProject_NoCurrentProjectReturnsFalse() throws Exception {
		// Run
		boolean result = projectManager.workOnCurrentProject(resourcesManager, inhabitant);

		// Assert
		assertFalse(result);
	}

	@Test
	public void workOnCurrentProject_projectFinished() throws Exception {
		// Init
		Blueprint lagerProject = new LagerProject();

		final ResourcesManager.TYPE resourceType = ResourcesManager.TYPE.WOOD;
		final int testWithAmountOfFive = 5;

		when(project.getBlueprint())//
				.thenReturn(lagerProject.getId());
		when(project.getResourcesToFinish())//
				.thenReturn(Arrays.asList(ResourceAmount.create(resourceType, testWithAmountOfFive)));
		when(projectRepository.findAll())//
				.thenReturn(Arrays.asList(project));
		when(appContext.getBeansOfType(Blueprint.class))//
				.thenReturn(new ImmutableMap.Builder<String, Blueprint>().put("name", lagerProject).build());
		when(inhabitant.getSkill(BUILDING))//
				.thenReturn(Integer.valueOf(testWithAmountOfFive));
		when(resourcesManager.getAmount(resourceType))//
				.thenReturn(Integer.valueOf(testWithAmountOfFive));

		// Run
		boolean result = projectManager.workOnCurrentProject(resourcesManager, inhabitant);

		// Assert
		assertTrue(result);
		verify(projectRepository).delete(project);
	}

	@Test
	public void workOnCurrentProject_projectNotFinished() throws Exception {
		// Init
		Blueprint lagerProject = new LagerProject();

		final ResourcesManager.TYPE resourceType = ResourcesManager.TYPE.WOOD;
		final int testWithAmountOfFive = 5;
		final int skillIsLowerThanFive = 4;

		when(project.getBlueprint())//
				.thenReturn(lagerProject.getId());
		when(project.getResourcesToFinish())//
				.thenReturn(Arrays.asList(ResourceAmount.create(resourceType, testWithAmountOfFive)));
		when(projectRepository.findAll())//
				.thenReturn(Arrays.asList(project));
		when(appContext.getBeansOfType(Blueprint.class))//
				.thenReturn(new ImmutableMap.Builder<String, Blueprint>().put("name", lagerProject).build());
		when(inhabitant.getSkill(BUILDING))//
				.thenReturn(Integer.valueOf(skillIsLowerThanFive));
		when(resourcesManager.getAmount(resourceType))//
				.thenReturn(Integer.valueOf(testWithAmountOfFive));

		// Run
		boolean result = projectManager.workOnCurrentProject(resourcesManager, inhabitant);

		// Assert
		assertTrue(result);
		verify(projectRepository).save(project);
	}

	@Test
	public void workOnCurrentProject_WorkerNotWorked() throws Exception {
		// Init
		Blueprint lagerProject = new LagerProject();

		final ResourcesManager.TYPE resourceType = ResourcesManager.TYPE.WOOD;
		final int testWithAmountOfFive = 5;
		final int noResourcesLeftForBuilding = 0;

		when(project.getBlueprint())//
				.thenReturn(lagerProject.getId());
		when(project.getResourcesToFinish())//
				.thenReturn(Arrays.asList(ResourceAmount.create(resourceType, testWithAmountOfFive)));
		when(projectRepository.findAll())//
				.thenReturn(Arrays.asList(project));
		when(appContext.getBeansOfType(Blueprint.class))//
				.thenReturn(new ImmutableMap.Builder<String, Blueprint>().put("name", lagerProject).build());
		when(inhabitant.getSkill(BUILDING))//
				.thenReturn(Integer.valueOf(testWithAmountOfFive));
		when(resourcesManager.getAmount(resourceType))//
				.thenReturn(Integer.valueOf(noResourcesLeftForBuilding));

		// Run
		boolean result = projectManager.workOnCurrentProject(resourcesManager, inhabitant);

		// Assert
		assertFalse(result);
		verify(projectRepository).save(project);
	}

	@Test
	public void workOnCurrentProject_skipFinishedResource() throws Exception {
		// Init
		Blueprint lagerProject = new LagerProject();

		final int noWoodLeft = 0;
		final int testWithAmountOfFive = 5;
		final ResourceAmount ironAmount = ResourceAmount.create(ResourcesManager.TYPE.IRON, testWithAmountOfFive);

		when(project.getBlueprint())//
				.thenReturn(lagerProject.getId());
		when(project.getResourcesToFinish())//
				.thenReturn(Arrays.asList(//
						ResourceAmount.create(ResourcesManager.TYPE.WOOD, noWoodLeft), //
						ironAmount));
		when(resourcesManager.getAmount(ResourcesManager.TYPE.IRON))//
				.thenReturn(Integer.valueOf(testWithAmountOfFive));
		when(projectRepository.findAll())//
				.thenReturn(Arrays.asList(project));
		when(appContext.getBeansOfType(Blueprint.class))//
				.thenReturn(new ImmutableMap.Builder<String, Blueprint>().put("name", lagerProject).build());
		when(inhabitant.getSkill(BUILDING))//
				.thenReturn(Integer.valueOf(testWithAmountOfFive));

		// Run
		projectManager.workOnCurrentProject(resourcesManager, inhabitant);

		// Assert
		verify(resourcesManager, Mockito.times(0)).getAmount(ResourcesManager.TYPE.WOOD);
		assertThat(ironAmount.getAmount(), is(0));
	}

}
