package project1;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class JUnitTestCase {
	
	//======================================User Object Tests======================================
	User u = new User();
	
	@Test
	public void testUserObject()
	{	
		assertNotNull(u.getClass());
	}
	
	@Test
	public void testUserConstructor()
	{	User t = new User("user", "pass");
	
		assertTrue(t.getUsername().equals("user") && t.getPassword().equals("pass"));
		//assertNotNull(u.getClass());
	}
	
	@Test
	public void testUserSetId()
	{	
		u.setId("1");
		assertTrue(u.getId().equals("1"));
	}
	
	@Test
	public void testUserSetUsername()
	{	
		u.setUsername("1");
		assertTrue(u.getUsername().equals("1"));
	}
	
	@Test
	public void testUserSetFirsname()
	{	
		u.setFirstname("1");
		assertTrue(u.getFirstname().equals("1"));
	}
	
	@Test
	public void testUserSetLastname()
	{	
		u.setLastname("1");
		assertTrue(u.getLastname().equals("1"));
	}
	
	@Test
	public void testUserSerEmail()
	{	
		u.setEmail("1");
		assertTrue(u.getEmail().equals("1"));
	}
	
	@Test
	public void testUserSetRole()
	{	
		u.setRole("1");
		assertTrue(u.getRole().equals("1"));
	}
	
	@Test
	public void testUserSetPassword()
	{	
		u.setPassword("1");
		assertTrue(u.getPassword().equals("1"));
	}
	
	//======================================User Object Tests======================================
	
	Ticket t = new Ticket();
	@Test
	public void testTicketObject()
	{
		Ticket t = new Ticket();
		
		assertNotNull(t.getClass());
	}
	
	@Test
	public void testTicketConstructor()
	{	
		Ticket t = new Ticket("amt", "type", "descript");
		assertTrue(t.getAmount().equals("amt") && t.getType().equals("type") && t.getDescription().equals("descript"));
	}
	
	@Test
	public void testTicketSetId()
	{	
		t.setID("1");
		assertTrue(t.getID().equals("1"));
	}
	
	@Test
	public void testTicketSetAmount()
	{	
		t.setAmount("1");
		assertTrue(t.getAmount().equals("1"));
	}
	
	@Test
	public void testTicketSetResolved()
	{	
		t.setResolved("1");
		assertTrue(t.getResolved().equals("1"));
	}
	
	@Test
	public void testTicketSetDescription()
	{	
		t.setDescription("1");
		assertTrue(t.getDescription().equals("1"));
	}
	
	@Test
	public void testTicketSetAuthor()
	{	
		t.setAuthor("1");
		assertTrue(t.getAuthor().equals("1"));
	}
	
	@Test
	public void testTicketSetResolver()
	{	
		t.setResolver("1");
		assertTrue(t.getResolver().equals("1"));
	}
	
	@Test
	public void testTicketSetStatus()
	{	
		t.setStatus(1);
		assertTrue(t.getStatus() == 1);
	}
	
	@Test
	public void testTicketSetType()
	{	
		t.setType("1");
		assertTrue(t.getType().equals("1"));
	}
	
	@Test
	public void testTicketSetSubmitted()
	{	
		t.setSubmitted("1");
		assertTrue(t.getSubmitted().equals("1"));
	}
	
	//======================================DAOHandler Tests======================================
	DAOHandler d = new DAOHandler();
	
	@Test
	public void testConn()
	{
		DAOHandler check = new DAOHandler();
		
		assertNotNull(check.conn);
	}
	
	@Test
	public void testLogin() {
		assertTrue(d.validateLogin("Admin", "admin").equals("Manager"));
	}
	
	@Test
	public void testGetUser() {
		assertNotNull(d.getCurrentUser("Admin"));
	}
	
	@Test
	public void testAddReimb() {
		Ticket newTicket = new Ticket("test", "test", "test");
		newTicket.setAuthor("testcase");
		d.addToReimbursementTable(newTicket);
		
		Ticket check = d.getReimb("testcase");
		
		d.approveReimbursement(check.getID(), check.getAuthor());
		d.denyReimbursement(check.getID(), check.getAuthor());
		
		d.removeReimb("testcase");
		
		assertNotNull(check.getAuthor().equals("testcase"));
	}
	
	@Test
	public void testGetTickets() {
		assertNotNull(d.getTicketsInDB("Razenok"));
	}
	
	@Test
	public void testCheckForUser() {
		assertTrue(d.checkForUser("Admin"));
	}
	
	@Test
	public void testApprove() {
		
	}
	
	@Test
	public void testDeny() {
		
	}
	
	@Test
	public void testGetAllEmployees() {
		assertNotNull(d.getAllEmployees());
	}
}

