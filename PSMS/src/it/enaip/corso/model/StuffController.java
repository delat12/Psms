package it.enaip.corso.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StuffController")
public class StuffController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DaoStuff stuffDao = DaoStuff.getInstance();
	private static final Logger LOGGER = Logger.getLogger(StuffController.class.getName());

	 public StuffController() {
	        super();
	        // TODO Auto-generated constructor stub
	    }
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String op = req.getParameter("op");

		try {

			switch (op) {
			case "new":
				showNewForm(req, resp);
				break;
				
			case "list":
				listStuff(req, resp);
				break;
			case "insert":
				insertStuff(req, resp);
				break;
			case "delete":
				deleteStuff(req, resp);
				break;
			case "edit":
				showEditForm(req, resp);
				break;
			case "update":
				updateStuff(req, resp);
				break;
			default:
				listStuff(req, resp);
				break;
			}
		} catch (SQLException e) {
			// For simplicity just Log the Exceptions
			LOGGER.log(Level.SEVERE, "SQL Error", e);
		}
	}

	private void updateStuff(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		String location = req.getParameter("location");

		Stuff stuff = new Stuff(id, name, description, quantity, location);
		stuffDao.update(stuff);
		resp.sendRedirect("StuffList.jsp");
	}

	private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException, ServletException {
		String id = req.getParameter("id");
		Optional<Stuff> existingStuff = stuffDao.find(id);
		RequestDispatcher dispatcher = req.getRequestDispatcher("StuffForm.jsp");
		existingStuff.ifPresent(s -> req.setAttribute("stuff", s));
		dispatcher.forward(req, resp);
	}

	private void deleteStuff(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(req.getParameter("id"));

		Stuff stuff = new Stuff(id);
		stuffDao.delete(stuff);
		resp.sendRedirect("StuffList.jsp");
	}

	public void insertStuff(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException, ServletException {
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		String location = req.getParameter("location");

		Stuff newStuff = new Stuff(name, description, quantity, location);
		stuffDao.save(newStuff);
		resp.sendRedirect("StuffForm.jsp");
	}

	private void showNewForm(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException, ServletException {

		RequestDispatcher dispatcher = req.getRequestDispatcher("StuffForm.jsp");
		dispatcher.forward(req, resp);
	}

	private void listStuff(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException, ServletException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("/StuffList.jsp");

		List<Stuff> listStuff = stuffDao.findAll();
		req.setAttribute("liststuff", listStuff);

		dispatcher.forward(req, resp);
	}
}