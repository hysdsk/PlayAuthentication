package forms;

import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.avaje.ebean.Ebean;

import models.User;
import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;

public class SigninForm extends AppForm {
	/** メールアドレス */
	@Required
	public String email;
	/** パスワード */
	@Required
	public String password;
	
	@Override
	public List<ValidationError> validate() {
		List<ValidationError> list = new ArrayList<>();
		User user = Ebean.find(User.class).where().eq("email", this.email).findUnique();
		if(user == null ? true : !BCrypt.checkpw(this.password, user.password)){
			list.add(new ValidationError("email", "正しくありません"));
			list.add(new ValidationError("password", "正しくありません"));
		}
		return list;
	}
}
