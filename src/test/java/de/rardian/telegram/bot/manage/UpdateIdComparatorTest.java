package de.rardian.telegram.bot.manage;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class UpdateIdComparatorTest {

	private static final Long GREATER = Long.valueOf(99);
	private static final Long SMALLER = Long.valueOf(1);
	private static final Long EQUAL = Long.valueOf(50);

	@Test
	public void type() throws Exception {
		assertThat(UpdateIdComparator.class, notNullValue());
	}

	@Test
	public void instantiation() throws Exception {
		UpdateIdComparator target = new UpdateIdComparator();
		assertThat(target, notNullValue());
	}

	@Test
	public void compare_MessageGreaterMessage() throws Exception {
		// Arrange
		Message m = mock(Message.class);
		when(m.getUpdate_id()).thenReturn(GREATER);
		Message other = mock(Message.class);
		when(other.getUpdate_id()).thenReturn(SMALLER);

		// Act
		int result = new UpdateIdComparator().compare(m, other);

		// Assert
		assertThat(1, is(result));
	}

	@Test
	public void compare_MessageLessMessage() throws Exception {
		// Arrange
		Message m = mock(Message.class);
		when(m.getUpdate_id()).thenReturn(SMALLER);
		Message other = mock(Message.class);
		when(other.getUpdate_id()).thenReturn(GREATER);

		// Act
		int result = new UpdateIdComparator().compare(m, other);

		// Assert
		assertThat(-1, is(result));
	}

	@Test
	public void compare_MessageEqualsMessage() throws Exception {
		// Arrange
		Message m = mock(Message.class);
		when(m.getUpdate_id()).thenReturn(EQUAL);
		Message other = mock(Message.class);
		when(other.getUpdate_id()).thenReturn(EQUAL);

		// Act
		int result = new UpdateIdComparator().compare(m, other);

		// Assert
		assertThat(0, is(result));
	}

}
