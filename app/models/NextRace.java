package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import play.i18n.Messages;

@Entity
@Table(name="ra_next_race")
public class NextRace extends Model {
		
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
	
	/**
	 * Get formatted race date (ex: 12/05/2012)
	 * @return
	 */
	public String getFormattedDate() {
		DateFormat df = new SimpleDateFormat(Messages.get("general.dateformat"));
		return df.format(this.date);
	}

}
