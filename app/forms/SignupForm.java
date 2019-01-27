package forms;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;

import models.User;
import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;

/**
 * サインアップ画面フォーム
 * @author hys_rabbit
 */
public class SignupForm extends AppForm {
	
	/** 名前 */
	@Required
	public String name;
	
	/** メールアドレス */
	@Required
	public String email;
	
	/** パスワード */
	@Required
	public String password;
	
	@Override
	public List<ValidationError> validate() {
		List<ValidationError> list = new ArrayList<>();
		/*
		 * 入力されたメールアドレスが現在使用中がどうかチェックする。
		 */
		User user = Ebean.find(User.class).where().eq("email", this.email).findUnique();
		if(user != null){
			list.add(new ValidationError("email", "すでに使用されているメールアドレスです。"));
		}
		return list;
	}
	
}
