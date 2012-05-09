package controllers;

import models.User;
import play.Logger;
import play.api.mvc.Session;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {
	
	
	public static Result REDIRECT_HOME = redirect(controllers.routes.Application.index());
	
	/**
	 * Home page
	 * @return
	 */
	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}

	/**
	 * Connect a user
	 * @param username
	 */
	public static void login(String username) {
		Logger.info("User " + username + " connected");
		session("user", username);
		
	}
	
	/**
	 * Disonnect a user
	 * @param username
	 */
	public static void logout() {
		User user = getConnectedUser();
		if (user != null) {
			Logger.info("User " + user.username + " disconnected");
			session().remove("user");
		}
	}
	
	/**
	 * Get connected user
	 * @return
	 */
	public static User getConnectedUser() {
		String username = session("user");
		return User.getByUsername(username);
	}
}