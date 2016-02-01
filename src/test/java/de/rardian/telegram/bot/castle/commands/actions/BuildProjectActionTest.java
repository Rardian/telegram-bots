package de.rardian.telegram.bot.castle.commands.actions;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.facilities.CastleFacility;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.communication.MessageReply;

@RunWith(MockitoJUnitRunner.class)
public class BuildProjectActionTest {
	@Mock
	private Castle castle;
	@Mock
	private MessageReply messageReply;
	@Mock
	private Inhabitant inhabitant;
	@InjectMocks
	private BuildProjectAction underTest;

	@Test
	public void executeWithProjectActiveAndNoParamAndNotAdded() throws Exception {
		// Init
		when(castle.isProjectInProgress()).thenReturn(Boolean.TRUE);
		underTest.setProjectName(null);

		// Run
		underTest.execute();

		// Assert
		verify(messageReply).answer("Du wirst Teil des Bauprojekts.");
		verify(castle).addWorkerFor(CastleFacility.CATEGORY.BUILDING, inhabitant);
	}

	@Test
	public void executeWithProjectActiveAndNoParamAndAlreadyAdded() throws Exception {
		// Init
		underTest.setProjectName(null);
		when(castle.isProjectInProgress()).thenReturn(Boolean.TRUE);
		doThrow(AlreadyAddedException.class).when(castle).addWorkerFor(CastleFacility.CATEGORY.BUILDING, inhabitant);

		// Run
		underTest.execute();

		// Assert
		verify(messageReply).answer("Du bist bereits Teil des Bauprojekts.");
	}

	@Test
	public void executeWithNoProjectActiveAndNoParam() throws Exception {
		// Init
		final Collection<String> projectIds = Arrays.asList("Eins", "Zwei");

		underTest.setProjectName(null);
		when(castle.isProjectInProgress()).thenReturn(Boolean.FALSE);
		when(castle.getProjectIds()).thenReturn(projectIds);

		// Run
		underTest.execute();

		// Assert
		verify(messageReply).answer("Kein Bauprojekt aktiv. Starte ein Projekt mit /bau <" + StringUtils.join(projectIds, "|") + ">");
	}

	@Test
	public void executeWithProjectActiveAndValidParam() throws Exception {
		// Init
		final String PROJECTNAME = "VALID";

		underTest.setProjectName(PROJECTNAME);
		when(castle.isProjectValid(PROJECTNAME)).thenReturn(Boolean.TRUE);
		when(castle.isProjectInProgress()).thenReturn(Boolean.TRUE);

		// Run
		underTest.execute();

		// Assert
		verify(messageReply).answer("Es läuft bereits ein Bauprojekt. Nimm daran teil mit: /bau");
	}

	@Test
	public void executeWithNoProjectActiveAndValidParam() throws Exception {
		// Init
		final String PROJECTNAME = "VALID";

		underTest.setProjectName(PROJECTNAME);
		when(castle.isProjectValid(PROJECTNAME)).thenReturn(Boolean.TRUE);
		when(castle.isProjectInProgress()).thenReturn(Boolean.FALSE);

		// Run
		underTest.execute();

		// Assert
		verify(castle).startProject(PROJECTNAME);
		verify(messageReply).answer("Du startest das Bauprojekt " + castle.getProjectName(PROJECTNAME));
	}

	@Test
	public void executeWithInValidParam() throws Exception {
		// Init
		final String PROJECTNAME = "VALID";

		underTest.setProjectName(PROJECTNAME);
		when(castle.isProjectValid(PROJECTNAME)).thenReturn(Boolean.FALSE);

		// Run
		underTest.execute();

		// Assert
		verify(messageReply).answer("Bitte wähle ein gültiges Bauprojekt aus.");
	}
}
