package cn.lm.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


/** 
* 程序名：ParamValidateUtil.java<br>
* 作成者：ReadyGo-PF <br>
* 日期：2017年9月18日 下午8:30:53 <br>
* 修改履历 <br>
* 项目名       状态      作成者          日期 <br>
* ------- --------------------- <br>
* ITPMS    新规        PF    2017年9月18日 下午8:30:53 <br>
* ----------------------------- <br>
*/

public class ParamValidateUtil {
	private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
	
	public static <T> List<String> validate(T t, Class<?>... mclass){
		Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, mclass);
        List<String> messageList = new ArrayList<>(constraintViolations.size());
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            messageList.add(constraintViolation.getMessage());
        }
        return messageList;
	}

}
