package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import controllers.user.routes;

import play.i18n.Messages;

public class News implements Comparable<News> {
	
	public String date;
	
	public String label;
	
	/**
	 * Create a news from a past race
	 * @param pastRace
	 */
	public News(PastRace pastRace) {
		DateFormat df = new SimpleDateFormat(Messages.get("general.dateformat"));
		this.date = df.format(pastRace.dateCreation);
		this.label = Messages.get("news.pastrace", routes.UserController.index(pastRace.user.username).url(), pastRace.user.getDisplayName(), pastRace.name, pastRace.getFormattedTime());
	}
	
	/**
	 * Create a news from a next race
	 * @param pastRace
	 */
	public News(NextRace nextRace) {
		DateFormat df = new SimpleDateFormat(Messages.get("general.dateformat"));
		this.date = df.format(nextRace.dateCreation);
		this.label = Messages.get("news.nextrace", routes.UserController.index(nextRace.user.username).url(), nextRace.user.getDisplayName(), nextRace.name, nextRace.getFormattedDate());
	}

	/**
	 * News are compared by date
	 */
	@Override
	public int compareTo(News news) {
		return news.date.compareTo(this.date);
	}
	

}
