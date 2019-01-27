package controllers;

import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mindrot.jbcrypt.BCrypt;

import com.avaje.ebean.Ebean;

import forms.SignupForm;
import models.User;
import play.cache.CacheApi;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;

/**
 * サインアップ画面コントローラー
 * @author hys_rabbit
 */
@Singleton
public class SignupController extends AppController {
	@Inject
	public SignupController(CacheApi cache) {
		super(cache);
	}
	@Inject
	private FormFactory formFactory;
	
	@Override
	public Result get() {
		Form<SignupForm> form = formFactory.form(SignupForm.class);
		return ok(views.html.signup.render(form));
	}

	@Override
	public Result post() {
		Form<SignupForm> form = formFactory.form(SignupForm.class).bindFromRequest();
		if(form.hasErrors()){
			return badRequest(views.html.signup.render(form));
		}
		
		try{
			/*
			 * ユーザ情報を新規登録する。
			 */
			Ebean.beginTransaction();
			User user = new User();
			user.name = form.get().name;
			user.email = form.get().email;
			user.password = BCrypt.hashpw(form.get().password, BCrypt.gensalt());
			user.logined = LocalDateTime.now();
			Ebean.insert(user);
			Ebean.commitTransaction();
			new SigninController(cache).setCacheUser(user);
		}catch(Exception e){
			Ebean.rollbackTransaction();
			return badRequest(views.html.signup.render(form));
		}finally {
			Ebean.endTransaction();
		}
		
		return redirect(routes.IndexController.get());
	}

}
