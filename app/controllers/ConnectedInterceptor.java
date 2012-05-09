package controllers;

import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;

/**
 * Check that user is connected
 * @author antoine
 *
 */
public class ConnectedInterceptor extends Action.Simple {

	@Override
	public Result call(Context ctx) throws Throwable {
		if (ctx.session().get("user") == null) {
			return forbidden();
		} else {
			return delegate.call(ctx);
		}
	}

}
