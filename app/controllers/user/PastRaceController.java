package controllers.user;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.avaje.ebean.Ebean;

import models.NextRace;
import models.PastRace;

import play.data.Form;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.mvc.Result;
import play.mvc.With;
import utils.StringUtils;
import views.html.user.pastRace;
import controllers.Application;
import controllers.ConnectedInterceptor;

/**
 * Add a past race
 * @author antoine
 *
 */
@With(ConnectedInterceptor.class)
public class PastRaceController extends Application {

	public static class PastRaceForm {
		@Constraints.Required
		@Constraints.MinLength(value=4)
		public String name;
		@Constraints.Required
		public String date;
		@Constraints.Required
		public String distance;
		@Constraints.Required
		public String hours;
		@Constraints.Required
		public String minutes;
		@Constraints.Required
		public String seconds;
	}

	private final static Form<PastRaceForm> pastRaceForm = form(PastRaceForm.class);

	/**
	 * Display form to add a past race
	 * 
	 * @return
	 */
	public static Result index() {
		return ok(pastRace.render(pastRaceForm));
	}

	/**
	 * Save past race
	 * @return
	 */
	public static Result save() {
		Form<PastRaceForm> form = form(PastRaceForm.class).bindFromRequest();
		
		// Check date format
		Date date = null;
		if (form.error("date") == null) {
			DateFormat df = new SimpleDateFormat(Messages.get("general.dateformat"));
			try {
				date = df.parse(form.field("date").value());
			} catch(ParseException p) {
				form.reject("date", "general.error.dateformat");
			}
		}
		
		// Check time
		String hours = form.field("hours").value();
		String minutes = form.field("minutes").value();
		String seconds = form.field("seconds").value();
		if (StringUtils.isEmpty(hours) || StringUtils.isEmpty(minutes) || StringUtils.isEmpty(seconds)){
			form.reject("time", "general.error.multiple");
		}
		
		int timeInSeconds = 0;
		try {
			timeInSeconds += Integer.parseInt(hours) * 3600;
			timeInSeconds += Integer.parseInt(minutes) * 60;
			timeInSeconds += Integer.parseInt(seconds);
		} catch(NumberFormatException n) {
			form.reject("time", "pastrace.error.time");
		}
		
		if (form.hasErrors()) {
			return badRequest(pastRace.render(form));
		} else {
			// Save past race
			PastRace pastRace = new PastRace();
			pastRace.name = form.field("name").value();
			pastRace.date = date;
			pastRace.user = Application.getConnectedUser();
			pastRace.distance = form.field("distance").value();
			pastRace.time = timeInSeconds;
			Ebean.save(pastRace);
			
			// Redirect to user homepage
			return redirect(controllers.user.routes.UserController.index(Application.getConnectedUser().username));
		}
	}

}