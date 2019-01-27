package common.secure;

import javax.inject.Inject;

import controllers.SigninController;
import controllers.routes;
import models.User;
import play.cache.CacheApi;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticator;

/**
 * 認証コード
 * @author hys_rabbit
 */
public class AppAuthenticator extends Authenticator {
	/** キャッシュ */
	private CacheApi cache;
	@Inject
	public AppAuthenticator(CacheApi cache){
		this.cache = cache;
	}
	
	@Override
	public String getUsername(Context ctx) {
		/*
		 * キャッシュからユーザ情報を取得する。
		 * ユーザ情報が存在すれば認証中としアクセスを許可する。
		 */
		SigninController signinController = new SigninController(cache);
		User user = signinController.getCacheUser();
		if(user != null){
			signinController.setCacheUser(user);
			return user.toString();
		}else{
			return null;
		}
	}
	
	@Override
	public Result onUnauthorized(Context ctx) {
		/*
		 * アクセスが許可されなかった場合、
		 * サインイン画面にリダイレクトする。
		 */
		return redirect(routes.SigninController.get());
	}
}
