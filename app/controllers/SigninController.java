package controllers;

import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.avaje.ebean.Ebean;

import forms.SigninForm;
import models.User;
import play.cache.CacheApi;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;

/**
 * サインイン画面コントローラー
 * @author hys_rabbit
 */
@Singleton
public class SigninController extends AppController {
	@Inject
	private FormFactory formFactory;
	/** ユーザ情報キー */
	public static final String USER_KEY = "USER";
	
	@Inject
	public SigninController(CacheApi cache) {
		super(cache);
	}

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
		
		try{
			/*
			 * ユーザのログイン日時を更新する。
			 */
			Ebean.beginTransaction();
			User user = Ebean.find(User.class).where().eq("email", form.get().email).findUnique();
			user.logined = LocalDateTime.now();
			Ebean.update(user);
			Ebean.commitTransaction();
			setCacheUser(user);
		}catch(Exception e){
			Ebean.rollbackTransaction();
			return badRequest(views.html.signin.render(form)); 
		}finally {
			Ebean.endTransaction();
		}
	
		return redirect(routes.IndexController.get());
	}
	
	/**
	 * キャッシュにユーザ情報保存
	 * @param user ユーザ情報
	 */
	public void setCacheUser(User user){
		setCache(USER_KEY, user);
		this.cache.set((USER_KEY + user.id), getClientId(), savetime);
	}
	
	/**
	 * キャッシュからユーザ情報取得
	 * @return ユーザ情報
	 */
	public User getCacheUser(){
		Object objectUser = getCache(USER_KEY);
		if(objectUser == null) return null;
		
		/*
		 * ユーザ情報を保存したプラウザのセッションUUIDと
		 * 現在アクセスしているセッションのUUIDを比較し、
		 * 異なる場合、ユーザ情報を取得させない。
		 */
		User user = User.class.cast(objectUser);
		String uuid = this.cache.get(USER_KEY + user.id).toString();
		if(!uuid.equals(getClientId())) return null;
		
		return user;
	}
	
	/**
	 * キャッシュのユーザ情報消去
	 */
	public void clearCacheUser(){
		clearCache(USER_KEY);
	}
	
}
