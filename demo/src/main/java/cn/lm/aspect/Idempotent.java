package cn.lm.aspect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * marker annotation<br>
 * @author MIN.LEE<br>
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
}
