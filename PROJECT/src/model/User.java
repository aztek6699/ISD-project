package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javafx.scene.image.Image;

@Entity
@Table(name = "user")
public class User implements Serializable {
	
	private static final long serialVersionUID = 6078372901189206579L;

	@Id
	@Column(name = "userId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
	List<UserFriend> friendList = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "senderId")
	List<Message> messageList = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "recieverId")
	List<Message> messageList2 = new ArrayList<>();

	@Column(name = "name")
	private String name;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "dateOfBirth")
	private String dateOfBirth;
	
//	@Column(name = "profilePicture")
//	private Image profilePicture;
	
	public User() {
		
	}

	public User(String name, String username, String password, String email, String dateOfBirth) {
		
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
//		this.profilePicture = profilePicture;
		this.dateOfBirth = dateOfBirth;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

//	public Image getProfilePicture() {
//		return profilePicture;
//	}
//
//	public void setProfilePicture(Image profilePicture) {
//		this.profilePicture = profilePicture;
//	}
	
	public List<UserFriend> getFriendList() {
		return friendList;
	}
	
	public void setFriendList(List<UserFriend> friendList) {
		this.friendList = friendList;
	}
	
	public List<Message> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
	}

	public List<Message> getMessageList2() {
		return messageList2;
	}

	public void setMessageList2(List<Message> messageList2) {
		this.messageList2 = messageList2;
	}
	
}
