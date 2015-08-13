package de.rardian.telegram.bot.model;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import de.rardian.telegram.json.JSONObject;

public class UserTest {

	@Test
	public void type() throws Exception {
		assertThat(User.class, notNullValue());
	}

	@Test
	public void instantiation() throws Exception {
		User target = new User();
		assertThat(target, notNullValue());
	}

	@Test
	public void fillWithJson_A$JSONObject() throws Exception {
		// TODO auto-generated by JUnit Helper.
		User target = new User();
		// given
		JSONObject json = mock(JSONObject.class);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		//		User actual = target.fillWithJson(json);
		//		// then
		//		// e.g. : verify(mocked).called();
		//		User expected = null;
		//		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void toString_A$() throws Exception {
		// TODO auto-generated by JUnit Helper.
		User target = new User();
		// given
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		String actual = target.toString();
		// then
		// e.g. : verify(mocked).called();
		String expected = null;
		//		assertThat(actual, is(equalTo(expected)));
	}

}
