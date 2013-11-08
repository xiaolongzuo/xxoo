package cn.zxl.xxoo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日期注解，处理日期格式问题
 * @author 左潇龙
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.FIELD)
public @interface ADate {

	/**
	 * 格式
	 * @return string
	 */
	String format();
	
}
