package models;

import java.time.LocalDateTime;
import javax.persistence.Entity;

@Entity
public class User extends AppModel {
	
	/** 名前 */
	public String name;

	/** メールアドレス */
	public String email;

	/** パスワード */
	public String password;
	
	/** 最終ログイン日時 */
	public LocalDateTime logined;
	
}
