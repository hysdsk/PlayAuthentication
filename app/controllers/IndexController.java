package controllers;

import javax.inject.Inject;
import javax.inject.Singleton;

import common.secure.AppAuthenticator;
import models.User;
import play.cache.CacheApi;
import play.mvc.Result;
import play.mvc.Security.Authenticated;

/**
 * ホーム画面コントローラー
 * @author hys_rabbit
 */
@Singleton
public class IndexController extends AppController {
	@Inject
	public IndexController(CacheApi cache) {
		super(cache);
	}

	@Authenticated(AppAuthenticator.class)
	@Override
    public Result get() {
		/*
		 * ユーザ情報でホーム画面を作成し返却する。
		 */
		User user = new SigninController(cache).getCacheUser();
        return ok(views.html.index.render(user));
    }

	@Authenticated(AppAuthenticator.class)
	@Override
	public Result post() {
		/*
		 * キャッシュからユーザ情報を消去し、
		 * サインイン画面にリダイレクトする。
		 */
		new SigninController(cache).clearCacheUser();
		return redirect(routes.SigninController.get());
	}

}
