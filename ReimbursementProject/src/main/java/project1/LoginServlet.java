package project1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Servlet implementation class JSONExample
 */
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	/*
	 * 
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /*
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
        
        PrintWriter writer = response.getWriter();
//        System.out.println("Session ID: " + session.getId());
//        System.out.println("Creation Time: " + new Date(session.getCreationTime()));
//        System.out.println("Last Accessed Time: " + new Date(session.getLastAccessedTime()));
        
        
    	
//    	Cookie cook = new Cookie("name", "Adam");
//    	cook.setMaxAge(24*60*60);
//    	
//    	response.addCookie(cook);
//    	Cookie cookies[] = request.getCookies();
//    	
//    	for(Cookie c: cookies)
//    	{
//    		System.out.println("Name: " + c.getName() + "Value: " + c.getValue());
//    	}
//    	
//    	HttpSession session = request.getSession();
//    	
//    	session.setAttribute("key", "username");
//    	session.getAttribute("key");
//    	long lastAccessed = session.getLastAccessedTime();
//    	Date lastAccessedDate = new Date(lastAccessed);
//    	
//    	session.getId();//returns a unique id
//    	session.getCreationTime(); //can be used with cookie age?
//    	session.getLastAccessedTime(); //gets last time it was accessed
//    	session.getMaxInactiveInterval(); //checks how long the session is being used so this can be used as a timeout
//    	session.invalidate(); //this is the timeout or can be used out log out
//    	session.setMaxInactiveInterval(60); //After 60 seconds of no activity the session will be invalidated automatically
    	
    	
    	//URL Rewriting
    	//String n = request.getParameter("username");
    	//you can now out.println("Welcome " + n); now you can some html stuff
    	//out.println("<a href=/"SecondServlet?uname="+n+">Visit</a>")
    	//now in the second servlet
    	//String n = request.getParameter(uname);
    	//System.out.println(request);
    	String jsonString = request.getReader().readLine();
    	ObjectMapper obj = new ObjectMapper();
    	User u = obj.readValue(jsonString, User.class);
    	
    	
    	
    	//String jsonString = obj.writeValueAsString(request.getParameter("user"));
    	
    	//System.out.println("Username: " + u.getUsername());
    	//System.out.println("Password: " + u.getPassword());
    	
    	//String hidden = request.getParameter("hidden");
    	//System.out.println(hidden);
    	
    	
    	//String username = request.getParameter("username");
        //String password = request.getParameter("password");
        //System.out.println("Username: " + u.getUsername() + " " + "Password: " + u.getPassword() + " HERE IN SERVLET");
        DAOHandler dao = new DAOHandler();
        String status = dao.validateLogin(u.getUsername(), u.getPassword());
        //System.out.println(status);
        if(status.equals("INVALID"))
        {
        	response.sendRedirect("/ReimbursementProject");
        	
        }
        else if(status.equals("Employee"))
        {
        	session.setAttribute("username", u.getUsername());
        	response.sendRedirect("/ReimbursementProject/employee.html");
        }
        else
        {
        	session.setAttribute("username", u.getUsername());
        	response.sendRedirect("/ReimbursementProject/manager.html");
        	
        }
        //User us = new User("Steve", "Rogers", "Good");
         //converting java objectto JSON string 

        //User u1 = obj.readValue(jsonString, User.class); //reading JSON from client and convert it to java object

        //response.getWriter().write(new ObjectMapper().writeValueAsString(new User("Jonah","Idk what Im doing", "blah!")));

        //response.sendRedirect("/employee.html");
     
        //RequestDispatcher rd = request.getRequestDispatcher("/employee.html");
//        rd.forward(request, response);
        //rd.include(request, response);
    //    response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /*
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
