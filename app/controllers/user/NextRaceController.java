package controllers.user;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.avaje.ebean.Ebean;

import models.NextRace;
import models.PastRace;
import models.User;
import play.Logger;
import play.data.Form;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.mvc.Result;
import play.mvc.With;
import utils.StringUtils;
import views.html.user.nextRace;
import controllers.Application;
import controllers.ConnectedInterceptor;
import controllers.user.PastRaceController.PastRaceForm;

/**
 * Add next race
 * @author antoine
 *
 */
@With(ConnectedInterceptor.class)
public class NextRaceController extends Application {

	public static class NextRaceForm {
		
		public String id;
		
		@Constraints.Required
		@Constraints.MinLength(value=4)
		public String name;
		
		@Constraints.Required
		public String date;
		
		public NextRaceForm() {
		}
		
		public NextRaceForm(NextRace nextRace) {
			this.id = String.valueOf(nextRace.id);
			this.name = nextRace.name;
			
			DateFormat df = new SimpleDateFormat(Messages.get("general.dateformat"));
			this.date = df.format(nextRace.date);
		}
	}

	private final static Form<NextRaceForm> nextRaceForm = form(NextRaceForm.class);

	/**
	 * Display form to add a next race
	 * 
	 * @return
	 */
	public static Result index() {
		User user = Application.getConnectedUser();
		return ok(nextRace.render(nextRaceForm, true));
	}

	/**
	 * Save next race
	 * @return
	 */
	public static Result save() {
		
		Form<NextRaceForm> form = form(NextRaceForm.class).bindFromRequest();
		// Is this an update or a creation?
		NextRace nextRace = null;
		boolean isnew = false;
		String raceId = form.field("id").value();
		if (!StringUtils.isEmpty(raceId)) {
			try {
				nextRace = NextRace.find.byId(Long.valueOf(raceId));
			} catch(NumberFormatException n) {
			}			
			if (nextRace == null || !nextRace.user.equals(getConnectedUser())) {
				Logger.error("Next race save forbidden");
				return forbidden();
			}
		} else {
			isnew = true;
			nextRace = new NextRace();
		}
		
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
		
		if (form.hasErrors()) {
			return badRequest(views.html.user.nextRace.render(form, isnew));
		} else {
			// Save next race
			nextRace.name = form.field("name").value();
			nextRace.date = date;
			nextRace.user = Application.getConnectedUser();
			Ebean.save(nextRace);
			
			// Redirect to user homepage
			return redirect(controllers.user.routes.UserController.index(Application.getConnectedUser().username));
		}
	}
	
	/**
	 * Show Past race edit form
	 * @return
	 */
	public static Result edit(Long raceId) {
		NextRace nextRace = NextRace.find.byId(Long.valueOf(raceId));
		if (nextRace == null || !nextRace.user.equals(getConnectedUser())) {
			Logger.error("Past race save forbidden");
			return forbidden();
		} else {
			Form<NextRaceForm> form = form(NextRaceForm.class).fill(new NextRaceForm(nextRace));
			return ok(views.html.user.nextRace.render(form, false));	
		}
		
		
		
	}
	
	/**
	 * Delete past race
	 * @return
	 */
	public static Result delete(Long raceId) {
		
		// Delete race
		NextRace nextRace = NextRace.find.byId(Long.valueOf(raceId));
		if (nextRace == null || !nextRace.user.equals(getConnectedUser())) {
			Logger.error("Past race save forbidden");
			return forbidden();
		} else {
			nextRace.delete();
		}
		
		// Redirect to user homepage
		return redirect(controllers.user.routes.UserController.index(Application.getConnectedUser().username));
	}
}