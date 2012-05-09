package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name="ra_user_contact")
public class UserContact extends Model {
		
	@Id
	public Long id;
	
	@ManyToOne
	@JoinColumn(name="follower_id") 
	public User follower;
	
	@ManyToOne
	@JoinColumn(name="followee_id")
	public User followee;
	
	
	public UserContact(User follower, User followee) {
		this.follower = follower;
		this.followee = followee;
	}
	
	public static Finder<Long, UserContact> find = new Finder<Long, UserContact>(
			Long.class, UserContact.class
	);
	
	public static UserContact get(User follower, User followee) {
		return find.where().eq("follower", follower).eq("followee", followee).query().findUnique();
	}
	
	
	
	
}
