package controllers.user;

import play.data.Form;
import play.mvc.Result;
import play.mvc.With;
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
		public String name;
		public String town;
		public String country;
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
		return ok(pastRace.render(pastRaceForm));
	}

}