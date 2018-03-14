package cn.lm.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import cn.lm.validate.IdCard.Type;

public class IdCardValidator implements ConstraintValidator<IdCard, String> {
	private IdCard.Type type;

	@Override
	public void initialize(IdCard idCard) {
		type = idCard.type();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null){
			return true;
		}
		
		if(type == Type.ID) {
			cn.lm.utils.IdCardValidator validator = new cn.lm.utils.IdCardValidator();
			return validator.isValidatedAllIdcard(value);
		} else {
			return "/^[a-zA-Z0-9]{3,21}$/   /^(P\\d{7})|(G\\d{8})$/".matches(value);
		}
	}

}
