package cn.lm.interceptor;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import cn.lm.controller.JsonpController;

@ControllerAdvice(assignableTypes=JsonpController.class)
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
	public JsonpAdvice() {
		super("callback","jsonp");
	}
}
