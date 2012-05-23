package controllers.invite;

import play.mvc.Result;
import views.html.invite.index;
import controllers.Application;

public class InviteController extends Application {
	
	/**
	 * Display invite page
	 * @return
	 */
	public static Result index() {
		return ok(index.render());
	}


}
