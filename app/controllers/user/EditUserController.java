package controllers.user;

import models.User;
import play.Logger;
import play.data.Form;
import play.mvc.Result;
import play.mvc.With;
import views.html.user.index;

import com.avaje.ebean.Ebean;

import controllers.Application;
import controllers.ConnectedInterceptor;

/**
 * Edit user personal information
 * @author antoine
 *
 */
@With(ConnectedInterceptor.class)
public class EditUserController extends Application {

	public static class UserDetailForm {
		public String name;
		public String town;
		public String country;
	}

	final static Form<UserDetailForm> userDetailForm = form(UserDetailForm.class);

	/**
	 * Display user homepage in edit mode
	 * 
	 * @return
	 */
	public static Result index(String username) {
		User user = User.getByUsername(username);
		User connectedUser = Application.getConnectedUser();
		if (user == null) {
			return REDIRECT_HOME;
		} else if (!user.equals(connectedUser) && !connectedUser.isAdmin) {
			return forbidden();
		} else {
			return ok(index.render(user, true, userDetailForm));
		}
	}

	/**
	 * Save personal details of a user
	 * 
	 * @return
	 */
	public static Result save(String username) {
		User user = User.getByUsername(username);
		User connectedUser = Application.getConnectedUser();
		if (user == null) {
			return REDIRECT_HOME;
		} else if (!user.equals(connectedUser) && !connectedUser.isAdmin) {
			return forbidden();
		} else {
			Form<UserDetailForm> filledForm = form(UserDetailForm.class).bindFromRequest();
			user.fullname = filledForm.field("fullname").value();
			user.town = filledForm.field("town").value();
			user.country = filledForm.field("country").value();
			Ebean.save(user);
			return redirect(controllers.user.routes.UserController.index(username));
		}
	}

	/**
	 * Follow a user
	 * 
	 * @param username
	 * @return
	 */
	public static Result follow(String username) {
		User connectedUser = getConnectedUser();
		User followee = User.getByUsername(username);
		if (followee == null || connectedUser.equals(followee) || connectedUser.isFollowing(followee)) {
			return forbidden();
		} else {
			// Add relation and redirect to followee page
			connectedUser.follow(followee);
			Logger.info(connectedUser.username + " now follows " + username);
			return redirect(controllers.user.routes.UserController.index(username));	
		}
	}

	/**
	 * Unfollow a user
	 * 
	 * @param username
	 * @return
	 */
	public static Result unfollow(String username) {
		User connectedUser = getConnectedUser();
		User followee = User.getByUsername(username);
		if (followee == null || connectedUser.equals(followee) || !connectedUser.isFollowing(followee)) {
			return forbidden();
		} else {
			// Delete relation and redirect to followee page
			connectedUser.unfollow(followee);
			Logger.info(connectedUser.username + " now unfollows " + username);
			return redirect(controllers.user.routes.UserController.index(username));	
		}
	}
}