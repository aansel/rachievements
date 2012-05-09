package controllers.user;

import com.avaje.ebean.Ebean;

import controllers.Application;
import models.User;
import models.UserContact;
import play.mvc.Result;
import views.html.user.index;

public class UserController extends Application {
	
	
	/**
	 * User homepage
	 * @return
	 */
	public static Result index(String username) {
		User user = User.getByUsername(username);
		if (user == null) {
			return REDIRECT_HOME;
		} else {
			return ok(index.render(user, false, null));	
		}
	}

	
}