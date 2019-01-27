package common.global;

import java.lang.reflect.Method;
import java.util.concurrent.CompletionStage;

import play.http.ActionCreator;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.Request;
import play.mvc.Result;

/**
 * アクション作成
 * @author hys_rabbit
 */
public class AppActionCreator implements ActionCreator {
	/** UUID セッションキー */
	public static final String UUID = "UUID";
	
	@Override
	public Action<?> createAction(Request arg0, Method arg1) {
        return new Action.Simple() {
            @Override
            public CompletionStage<Result> call(Http.Context ctx) {
            	/*
            	 * セッションにUUIDが存在しない場合、
            	 * UUIDを生成しセッションに設定する。
            	 */
                if(ctx.session().get(UUID) == null){
                	String uuid = java.util.UUID.randomUUID().toString();
                	ctx.session().put(UUID, uuid);
                }
                return delegate.call(ctx);
            }
        };
	}

}
