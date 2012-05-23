package models;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.i18n.Messages;

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

	@Constraints.Required
	public String distance;
	
	@Constraints.Required
	public int time;
	
	@Constraints.Required
	@Column(name="date_creation")
	public Date dateCreation;
	
	public static Finder<Long, PastRace> find = new Finder<Long, PastRace>(
			Long.class, PastRace.class
	);
	
	/**
	 * Get formatted race date (ex: 12/05/2012)
	 * @return
	 */
	public String getFormattedDate() {
		DateFormat df = new SimpleDateFormat(Messages.get("general.dateformat"));
		return df.format(this.date);
	}
	
	/**
	 * Get day number of past race
	 * @return
	 */
	public int getDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.date);
		return cal.get(Calendar.DATE);
	}
	
	/**
	 * Get month of past race
	 * @return
	 */
	public int getMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.date);
		return cal.get(Calendar.MONTH);
	}
	
	/**
	 * Get year of past race
	 * @return
	 */
	public int getYear() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.date);
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * Get formatted race time (ex: 1h 12min 30s)
	 * @return
	 */
	public String getFormattedTime() {
		NumberFormat nf = new DecimalFormat("00");
		int hours = time / 3600;
		int minutes = (time - 3600 * hours) / 60;
		int seconds = time - 3600 * hours - 60 * minutes;
		return nf.format(hours) + ":" + nf.format(minutes) + ":" + nf.format(seconds);
	}
	
	/**
	 * Get formatted distance
	 * @return
	 */
	public String getFormattedDistance() {
		return DISTANCE.lookup(distance).getLabel();
	}
	
	/**
	 * Get average speed on this race (unit is km/h)
	 * TODO unit used must depend on user language 
	 * @return
	 */
	public String getSpeed() {
		double speed = (double) DISTANCE.lookup(this.distance).getMeters() / this.time * 3.6;
		return Messages.get("distance.speed", speed);
	}
	
	
}
