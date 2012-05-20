package controllers.contact;

import play.mvc.Result;
import views.html.index;
import controllers.Application;

public class ContactController extends Application {
	
	/**
	 * Display contact form
	 * @return
	 */
	public static Result index() {
		return ok(index.render());
	}
	
	
	/**
	 * Submit contact form
	 * @return
	 */
	public static Result send() {
		return ok(index.render());
	}
	
}
