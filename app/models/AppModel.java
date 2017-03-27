package models;

import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

@MappedSuperclass
public abstract class AppModel extends Model {

	/** ID */
	@Id
	public Long id;
	
	/** 作成日時 */
	@CreatedTimestamp
	public LocalDateTime created;
	
	/** 最終更新日時 */
	@UpdatedTimestamp
	public LocalDateTime updated;
	
}
