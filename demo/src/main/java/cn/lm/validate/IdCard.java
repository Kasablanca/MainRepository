package cn.lm.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=IdCardValidator.class)
public @interface IdCard {
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	String message() default "无效的身份证号或护照号";
	Type type() default Type.ID;
	
	enum Type {
		ID,PASSPORT
	}
}
