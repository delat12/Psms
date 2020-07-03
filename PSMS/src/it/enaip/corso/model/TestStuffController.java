package it.enaip.corso.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestStuffController {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() throws SQLException, IOException, ServletException {
		StuffController servlet = new StuffController();
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		DbConnect.getInstance().getConnection();
		final String nm= "name";
		
	    when(request.getParameter(nm)).thenReturn("testName");
	    when(request.getParameter("description")).thenReturn("testDescription");
	    when(request.getParameter("quantity")).thenReturn("1");
	    when(request.getParameter("location")).thenReturn("testLocation");
	    servlet.insertStuff(request, response);
	}

	@Test
	public void testDoGetHttpServletRequestHttpServletResponse1() {
		fail("Not yet implemented");
	}

}
