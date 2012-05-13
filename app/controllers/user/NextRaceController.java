package controllers.user;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.avaje.ebean.Ebean;

import models.NextRace;
import models.User;
import play.data.Form;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.mvc.Result;
import play.mvc.With;
import views.html.user.nextRace;
import controllers.Application;
import controllers.ConnectedInterceptor;

/**
 * Add next race
 * @author antoine
 *
 */
@With(ConnectedInterceptor.class)
public class NextRaceController extends Application {

	public static class NextRaceForm {
		@Constraints.Required
		@Constraints.MinLength(value=4)
		public String name;
		
		@Constraints.Required
		public String date;
	}

	private final static Form<NextRaceForm> nextRaceForm = form(NextRaceForm.class);

	/**
	 * Display form to add a next race
	 * 
	 * @return
	 */
	public static Result index() {
		User user = Application.getConnectedUser();
		return ok(nextRace.render(nextRaceForm));
	}

	/**
	 * Save next race
	 * @return
	 */
	public static Result save() {
		Form<NextRaceForm> form = form(NextRaceForm.class).bindFromRequest();
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
			return badRequest(nextRace.render(form));
		} else {
			// Save next race
			NextRace nextRace = new NextRace();
			nextRace.name = form.field("name").value();
			nextRace.date = date;
			nextRace.user = Application.getConnectedUser();
			Ebean.save(nextRace);
			
			// Redirect to user homepage
			return redirect(controllers.user.routes.UserController.index(Application.getConnectedUser().username));
		}
	}
}