package controllers;

import javax.inject.Inject;

import play.cache.CacheApi;
import play.mvc.Controller;
import play.mvc.Result;

public abstract class AppController extends Controller {
	
	protected CacheApi cache;
	
	protected int timeout = 60 * 15;
	
	protected String keyEmail = "KEY_EMAIL";
	
    @Inject
    public AppController(CacheApi cache) {
        this.cache = cache;
    }
	
	public static final String UUID = common.global.AppActionCreator.UUID;
	
	public abstract Result get();
	
	public abstract Result post();
	
	public static void setClientId(String uuid){
		session(UUID, uuid);
	}
	
	public static String getClientId(){
		return session(UUID);
	}
	
	protected void setCache(String keyword, Object object){
		this.cache.set(getClientId() + keyword, object, timeout);
	}
	
	protected Object getCache(String keyword){
		return this.cache.get(getClientId() + keyword);
	}
	
	protected void clearCache(String keyword){
		this.cache.remove(getClientId() + keyword);
	}
		
}
