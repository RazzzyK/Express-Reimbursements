package project1;

import java.io.Serializable;
import java.sql.Timestamp;

import oracle.sql.BlobDBAccess;

public class Ticket implements Serializable{
	
	private String ID;
	private String amount;
	private String submitted;
	private String resolved;
	private String description;
	//private BlobDBAccess reciept;
	private String author;
	private String resolver;
	private int status;
	
	private String type;
	
	
	
	Ticket() {
		
	}
	
	public Ticket(String amount, String type, String description) {
		this.author = "";
		this.amount = amount;
		this.type = type;
		this.description = description;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSubmitted() {
		return submitted;
	}

	public void setSubmitted(String submitted) {
		this.submitted = submitted;
	}

	public String getResolved() {
		return resolved;
	}

	public void setResolved(String resolved) {
		this.resolved = resolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getResolver() {
		return resolver;
	}

	public void setResolver(String resolver) {
		this.resolver = resolver;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
