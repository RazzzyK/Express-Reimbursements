package project1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class ManagerServlet
 */
public class ManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response, String input) throws ServletException, IOException {
		DAOHandler dao = new DAOHandler();
		HttpSession session = request.getSession(false);
		RequestDispatcher rd;
		
		String username = input;
		try {
			System.out.println("Username: " + session.getAttribute("username"));
			if(session.getAttribute("username") == null)
			{
				System.out.println("Here");
				rd = request.getRequestDispatcher("LoginServlet");
				rd.forward(request, response);
			}
		} catch(NullPointerException e) {
			rd = request.getRequestDispatcher("login.html");
			return;
			//rd.forward(request, response);
		}
		
		if(username.equals("GETEMPLOYEES"))
		{
			ArrayList<User> allEmployees = dao.getAllEmployees();
			String jsonString = new Gson().toJson(allEmployees);
			
			if(allEmployees.size() == 0)
				jsonString = "No Users Found!";
			//System.out.println(jsonString);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        out.print(jsonString);
	        out.flush();   
		
		}
		else
		{
			try {
			System.out.println(username);
			System.out.println(dao.checkForUser(username));
			if(dao.checkForUser(username))
			{
				ArrayList<Ticket> userTickets = dao.getTicketsInDB(username);
				
				String jsonString = new Gson().toJson(userTickets);
				
				if(userTickets.size() == 0)
					jsonString = "No Tickets Found!";
				//System.out.println(jsonString);
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
		        response.setCharacterEncoding("UTF-8");
		        out.print(jsonString);
		        out.flush();   
			}
			
			} catch (NullPointerException e) {
				System.out.println("NULL POINTER");
				//response.sendRedirect("/ReimbursementProject/login.html");
			}
		}

		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DAOHandler dao = new DAOHandler();
		HttpSession session = request.getSession(false);
		String input = request.getReader().readLine();
		//System.out.println("YO CHECK IT " + input);
		
		String[] myArr = input.split(",");
		
		try {
			if(myArr[1].equals("APPROVE"))
			{
				ArrayList<Ticket> userTickets = dao.approveReimbursement(myArr[0], (String) session.getAttribute("username"));
				doGet(request, response, input);
			}
				
			else if(myArr[1].equals("DENY"))
			{
				ArrayList<Ticket> userTickets = dao.denyReimbursement(myArr[0], (String) session.getAttribute("username"));
				doGet(request, response, input);
			}
			
		} catch (ArrayIndexOutOfBoundsException e) {
			doGet(request, response, input);
		}

	}
}
