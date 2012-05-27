package controllers.news;

import com.avaje.ebean.Ebean;

import controllers.Application;
import controllers.ConnectedInterceptor;
import models.User;
import models.UserContact;
import play.mvc.Result;
import play.mvc.With;
import views.html.news.index;

/**
 * News feed
 * @author antoine
 *
 */
@With(ConnectedInterceptor.class)
public class NewsController extends Application {
	
	/**
	 * Display user news feed
	 * @return
	 */
	public static Result index() {
		User user = Application.getConnectedUser();
		return ok(index.render(user.getNews()));	
	}

}