package com.min.mina.statemachine;

public @interface Transition {
	String on() default "*";
	String in();
	String next() default "_self_";
	int weight() default 0;
}
