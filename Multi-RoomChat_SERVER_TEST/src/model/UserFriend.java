package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_friend")
public class UserFriend implements Serializable {
	
	private static final long serialVersionUID = 3511669162572616506L;

	@Id
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "friend_id")
	private User friend;
	
	public UserFriend() {
		
	}

	public UserFriend(User user, User friend) {
		super();
		this.user = user;
		this.friend = friend;
	}

	public User getUser() {
		return user;
	}

	public User getFriend() {
		return friend;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setFriend(User friend) {
		this.friend = friend;
	}
	
}
