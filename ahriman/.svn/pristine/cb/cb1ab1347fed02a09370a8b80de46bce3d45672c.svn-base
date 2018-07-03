package com.syhd.ahriman.service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * cron任务注解,方法级别，项目启动时也会执行，
 * 每个方法只能有一个该注解，但同一个类可以有多个方法有该注解
 * @author MIN.LEE
 *
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CronTask {
	/**
	 * 默认为"0 0 0 * * ?"，即每天0点执行
	 */
	String value() default "0 0 0 * * ?";
}
