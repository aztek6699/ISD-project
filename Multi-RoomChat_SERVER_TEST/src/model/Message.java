package model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "message")
public class Message implements Serializable {

	private static final long serialVersionUID = -8022230341683471764L;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "sender_id")
	private User senderId;
	
	@Id
	@ManyToOne 
	@JoinColumn(name = "receiver_id")
	private User recieverId;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "sent_time")
	private Timestamp sentTime;
	
	public Message() {
		
	}
	
	public Message(User senderId, User recieverId, String message) {
		super();
		this.senderId = senderId;
		this.recieverId = recieverId;
		this.message = message;
		this.sentTime = new Timestamp(System.currentTimeMillis());
	}

	public User getSenderId() {
		return senderId;
	}

	public User getRecieverId() {
		return recieverId;
	}

	public String getMessage() {
		return message;
	}

	public Timestamp getSentTime() {
		return sentTime;
	}
}