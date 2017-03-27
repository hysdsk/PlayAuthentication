package controllers;

import javax.inject.Inject;

import com.avaje.ebean.Ebean;

import common.secure.AppAuthenticator;
import models.User;
import play.cache.CacheApi;
import play.mvc.Result;
import play.mvc.Security.Authenticated;

public class IndexController extends AppController {
	@Inject
	public IndexController(CacheApi cache) {
		super(cache);
	}

	@Authenticated(AppAuthenticator.class)
	@Override
    public Result get() {
		String email = new SigninController(cache).getCacheEmail();
		User user = Ebean.find(User.class).where().eq("email", email).findUnique();
        return ok(views.html.index.render(user));
    }

	@Authenticated(AppAuthenticator.class)
	@Override
	public Result post() {
		new SigninController(cache).clearCacheEmail();
		return redirect(routes.SigninController.get());
	}

}
