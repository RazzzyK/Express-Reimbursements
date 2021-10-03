package project1;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import oracle.*;

public class DAOHandler {
	
	private final String host = "jdbc:oracle:thin:@java-react-free-tier.csedppwlopoz.us-east-1.rds.amazonaws.com:1521:ORCL";
	private final String user = "Admin";                //Save in a flat file
	private final String password = "12345678";
	
	Connection conn;
	
	DAOHandler()
	{

		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(host, user, password);
			//System.out.println("HIT!");
			
		}catch (ClassNotFoundException e)
		{
			System.out.println("Unable to load driver class");
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public String validateLogin(String user, String pass)
	{
		System.out.println("Checking Database for Existing User");
		String safeQuery = "Select * from EXPRESS.ERS_USERS where ers_username=?";

		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(safeQuery);
			
			ps.setString(1, user);
			
			ResultSet rs = ps.executeQuery();

			if(!rs.next())
				return "INVALID";
			else if(rs.getString(3).equals(pass))
			{
				return(rs.getString(7));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "INVALID";
	}
	
	public User getCurrentUser(String userN)
	{
		System.out.println(userN);
		User u = new User();
		
		String safeQuery = "Select * from EXPRESS.ERS_USERS where ers_username=?";
		
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(safeQuery);
			
			ps.setString(1, userN);
			
			ResultSet rs = ps.executeQuery();

			if(!rs.next())
				return u;
			else
			{
				u.setId(rs.getInt(1) + "");
				u.setUsername(rs.getString(2));
				u.setPassword(rs.getString(3));
				u.setFirstname(rs.getString(4));
				u.setLastname(rs.getString(5));
				u.setEmail(rs.getString(6));
				u.setRole(rs.getString(7));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}
	
	public void addToReimbursementTable(Ticket t)
	{
		Random r = new Random();
		int low = 10;
		int high = 100000000;
		int result = 0;
		
		
		String safeQuery = "Select * from EXPRESS.ERS_REIMBURSEMENT where reimb_id=?";
		
		PreparedStatement ps;
		ResultSet rs;
		
		try {
			do {
				result = r.nextInt(high-low) + low;
				ps = conn.prepareStatement(safeQuery);
				
				ps.setInt(1, result);
				
				rs = ps.executeQuery();
	
				if(!rs.next())
					break;
			} while(rs.next());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String safeInsert = "INSERT INTO EXPRESS.ERS_REIMBURSEMENT VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		PreparedStatement push;
		
		try {
			push = conn.prepareStatement(safeInsert);
			
			push.setString(1, result + "");
			push.setString(2, t.getAmount());		  
			push.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			push.setTimestamp(4, null);
			push.setString(5, t.getDescription());
			push.setString(6, null);
			push.setString(7, t.getAuthor());
			push.setString(8, "UNRESOLVED");
			push.setInt(9, 0);
			push.setString(10, t.getType());
			
			push.executeUpdate();
			
			System.out.println("\tSucessfully added new ticket!");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public ArrayList<Ticket> getTicketsInDB(String userN)
	{
		ArrayList<Ticket> userTickets = new ArrayList<Ticket>();
		
		String safeQuery = "Select * from EXPRESS.ERS_REIMBURSEMENT where reimb_author=?";
		
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(safeQuery);
			
			ps.setString(1, userN);
			
			ResultSet rs = ps.executeQuery();

			if(!rs.next())
				return userTickets;
			else
			{
				 do {
					Ticket add = new Ticket();
					add.setID(rs.getString(1));
					add.setAmount(rs.getString(2));
					add.setSubmitted(rs.getTimestamp(3) + "");
					add.setResolved(rs.getTimestamp(4) + "");
					add.setDescription(rs.getString(5));
					//BLOB
					add.setAuthor(rs.getString(7));
					add.setResolver(rs.getString(8));
					add.setStatus(rs.getInt(9));
					add.setType(rs.getString(10));
		
					userTickets.add(add);
				}while(rs.next());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userTickets;
	}
	
	public Boolean checkForUser(String user)
	{
		System.out.println("Checking Database for Existing User");
		String safeQuery = "Select * from EXPRESS.ERS_USERS where ers_username=?";

		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(safeQuery);
			
			ps.setString(1, user);
			
			ResultSet rs = ps.executeQuery();

			if(!rs.next())
				return false;
			else
			{
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public ArrayList<Ticket> approveReimbursement(String id, String userN)
	{	
		String safeUpdate = "UPDATE EXPRESS.ERS_REIMBURSEMENT SET REIMB_RESOLVED =?, REIMB_RESOLVER=?, REIMB_STATUS_ID =? WHERE REIMB_ID=?";
		
		PreparedStatement push;
		try {
			push = conn.prepareStatement(safeUpdate);
			
			push.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			push.setString(2, userN);
			push.setInt(3, 1);
			push.setString(4, id);
			
			push.executeUpdate();
			
			System.out.println("\tSucessfully Updated");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return getTicketsInDB(userN);
	}
	
	public ArrayList<Ticket> denyReimbursement(String id, String userN)
	{	
		String safeUpdate = "UPDATE EXPRESS.ERS_REIMBURSEMENT SET REIMB_RESOLVED =?, REIMB_RESOLVER=?, REIMB_STATUS_ID =? WHERE REIMB_ID=?";
		
		PreparedStatement push;
		try {
			push = conn.prepareStatement(safeUpdate);
			
			push.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			push.setString(2, userN);
			push.setInt(3, -1);
			push.setString(4, id);
			
			push.executeUpdate();
			
			System.out.println("\tSucessfully Updated");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return getTicketsInDB(userN);
	}
	
	public ArrayList<User> getAllEmployees()
	{
		ArrayList<User> userTickets = new ArrayList<User>();
		
		String safeQuery = "Select * from EXPRESS.ERS_USERS WHERE user_role=?";
		
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(safeQuery);
			
			ps.setString(1, "Employee");
			
			ResultSet rs = ps.executeQuery();

			if(!rs.next())
				return userTickets;
			else
			{
				 do {
					User u = new User();
					u.setId(rs.getInt(1) + "");
					u.setUsername(rs.getString(2));
					u.setPassword(rs.getString(3));
					u.setFirstname(rs.getString(4));
					u.setLastname(rs.getString(5));
					u.setEmail(rs.getString(6));
					u.setRole(rs.getString(7));
		
					userTickets.add(u);
				}while(rs.next());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userTickets;
	}
	
	public Ticket getReimb(String id)
	{
		Ticket t = new Ticket();
		
		String safeQuery = "Select * from EXPRESS.ERS_REIMBURSEMENT where reimb_author=?";
		
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(safeQuery);
			
			ps.setString(1, id);
			
			ResultSet rs = ps.executeQuery();

			if(!rs.next())
				return t;
			else
			{
				t.setID(rs.getString(1));
				t.setAmount(rs.getString(2));
				t.setSubmitted(rs.getTimestamp(3) + "");
				t.setResolved(rs.getTimestamp(4) + "");
				t.setDescription(rs.getString(5));
				//BLOB
				t.setAuthor(rs.getString(7));
				t.setResolver(rs.getString(8));
				t.setStatus(rs.getInt(9));
				t.setType(rs.getString(10));				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	public void removeReimb(String author)
	{
		String updateQuery = "DELETE FROM EXPRESS.ERS_REIMBURSEMENT WHERE reimb_author=?";
		
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(updateQuery);
			
			ps.setString(1, author);
			
			ResultSet rs = ps.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}