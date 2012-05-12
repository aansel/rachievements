package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Constraint;

import com.avaje.ebean.Ebean;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
@Table(name="ra_past_race")
public class PastRace extends Model {
		
	@Id
	public Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id") 
	public User user;
	 
	@Constraints.Required
	@Constraints.MinLength(value=4)
	public String name;
	
	@Constraints.Required
	public Date date;

}
