package controllers;

import javax.inject.Inject;
import javax.inject.Singleton;

import forms.SigninForm;
import play.cache.CacheApi;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;

@Singleton
public class SigninController extends AppController {
	@Inject
	public SigninController(CacheApi cache) {
		super(cache);
	}
	@Inject
	private FormFactory formFactory;
	
	@Override
	public Result get() {
		Form<SigninForm> form = formFactory.form(SigninForm.class);
		return ok(views.html.signin.render(form));
	}

	@Override
	public Result post() {
		Form<SigninForm> form = formFactory.form(SigninForm.class).bindFromRequest();
		if(form.hasErrors()){
			return badRequest(views.html.signin.render(form)); 
		}
		
		setCacheEmail(form.get().email);
		return redirect(routes.IndexController.get());
	}
	
	public void setCacheEmail(String email){
		setCache(keyEmail, email);
		this.cache.set(email, getClientId(), timeout);
	}
	
	public String getCacheEmail(){
		Object objectEmail = getCache(keyEmail);
		if(objectEmail == null) return null;
		
		String email = String.valueOf(objectEmail);
		String uuid = this.cache.get(email).toString();
		if(!uuid.equals(getClientId())) return null;
		
		return email;
	}
	
	public void clearCacheEmail(){
		clearCache(keyEmail);
	}
	
}
