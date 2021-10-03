package project1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class EmployeeServlet
 */
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DAOHandler dao = new DAOHandler();
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String username = "";

		try {
			
			username = (String) session.getAttribute("username");

			if (session.getAttribute("username") == null) {}
			else {
				System.out.println("Login Success!");
				ServletContext context= getServletContext();
				
				ArrayList<Ticket> dataToSend = dao.getTicketsInDB(username);
				String jsonString = new Gson().toJson(dataToSend);
				
				if(dataToSend.size() == 0)
					jsonString = "No Tickets Found!";
				
				response.setContentType("application/json");
		        response.setCharacterEncoding("UTF-8");
		        out.print(jsonString);
		        //System.out.println(jsonString);
		        out.flush();   
				
		        //RequestDispatcher rd= context.getRequestDispatcher("/employee.html");
				//rd.forward(request, response);
				
				//session.invalidate();
			}
			
			
		} catch (NullPointerException e) {
			response.sendRedirect("/ReimbursementProject/login.html");
			//session.invalidate();
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
		//System.out.println("Are you here");
		
	}

}
