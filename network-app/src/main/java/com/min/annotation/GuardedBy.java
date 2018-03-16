package com.min.annotation;

public @interface GuardedBy {
	String value() default "";
}
