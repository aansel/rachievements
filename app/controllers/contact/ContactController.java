package controllers.contact;

import play.mvc.Result;
import views.html.contact.index;
import controllers.Application;

public class ContactController extends Application {
	
	/**
	 * Display contact page
	 * @return
	 */
	public static Result index() {
		return ok(index.render());
	}
	
	
	
}
