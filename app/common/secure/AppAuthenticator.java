package common.secure;

import javax.inject.Inject;

import controllers.SigninController;
import controllers.routes;
import play.cache.CacheApi;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticator;

public class AppAuthenticator extends Authenticator {
	
	private CacheApi cache;
	@Inject
	public AppAuthenticator(CacheApi cache){
		this.cache = cache;
	}
	
	@Override
	public String getUsername(Context ctx) {
		SigninController signinController = new SigninController(cache);
		String email = signinController.getCacheEmail();
		if(email != null) signinController.setCacheEmail(email);
		return email;
	}
	
	@Override
	public Result onUnauthorized(Context ctx) {
		return redirect(routes.SigninController.get());
	}
}
