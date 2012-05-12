package controllers.connection;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.User;
import play.data.Form;
import play.data.validation.Constraints;
import play.mvc.Result;
import utils.StringUtils;
import views.html.connection.signIn;
import views.html.connection.signUp;

import com.avaje.ebean.Ebean;

import controllers.Application;


/**
 * Sign in and sign up controllers
 * @author antoine
 *
 */
public class ConnectionController extends Application {
	
	
	public static class SignInForm {
		@Constraints.Required
		public String username;
		@Constraints.Required
		public String password;
	}
  
	final static Form<User> userForm = form(User.class);
	final static Form<SignInForm> signInForm = form(SignInForm.class);

	/**
	 * Display signIn form
	 * @return
	 */
	public static Result signInForm() {
		return ok(signIn.render(signInForm));
	}
	
	/**
	 * Sign in user
	 * @return
	 */
	public static Result signIn() {
		Form<SignInForm> filledForm = form(SignInForm.class).bindFromRequest();
		
		String username = filledForm.field("username").value();
		String password = filledForm.field("password").value();
		
		// Check that credentials are correct
		if(!filledForm.hasErrors()) {
			User user = User.getByUsername(username);
			if (user == null || !user.password.equals(password)) {
				filledForm.reject("username", "");
				filledForm.reject("password", "user.signin.error.invalid");
			}
		}
		
        if(filledForm.hasErrors()) {
            return badRequest(signIn.render(filledForm));
        } else {
        	Application.login(username);
        	return redirect(controllers.user.routes.UserController.index(username));
        }
	}
	
	/**
	 * Display sign up form
	 * @return
	 */
	public static Result signUpForm() {
		return ok(signUp.render(userForm));
	}
	
	/**
	 * Sign up
	 * @return
	 */
	private static Pattern USERNAME_PATTERN = Pattern.compile("[0-9a-zA-Z]{3}[0-9a-zA-Z\\._]+");
	public static Result signUp() {
		Form<User> filledForm = form(User.class).bindFromRequest();
		
		// Check password confirmation
		String password = filledForm.field("password").value();
		String passwordConfirmation = filledForm.field("passwordConfirmation").value();
		if (StringUtils.isEmpty(passwordConfirmation)) {
			filledForm.reject("passwordConfirmation", "general.error.fieldrequired");
		} else if (!password.equals(passwordConfirmation)){
			filledForm.reject("passwordConfirmation", "user.signup.passwordconfirmation.incorrect");
		}
		
		// Check username format
		String username = filledForm.field("username").value();
		if (!StringUtils.isEmpty(username)) {
			Matcher m = USERNAME_PATTERN.matcher(username);
			if (!m.matches()) {
				filledForm.reject("username", "user.signup.username.incorrect");	
			}
		}

		if(filledForm.hasErrors()) {
			return badRequest(signUp.render(filledForm));
		} else {
			// Persist user
			User user = filledForm.get();
			user.dateCreation = new Date();
			Ebean.save(user);
			// Log in user and redirect to user homepage
			login(username);
			return redirect(controllers.user.routes.UserController.index(user.username));
		}
	}
	
	/**
	 * Sign out
	 * @return
	 */
	public static Result signOut() {
		Application.logout();
		return REDIRECT_HOME;
	}
	
}