package forms;

import java.util.List;

import play.data.validation.ValidationError;

public abstract class AppForm {
	
	public abstract List<ValidationError> validate();
}
