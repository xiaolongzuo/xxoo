package cn.zxl.xxoo.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.zxl.xxoo.processor.XmlProcessor;
import cn.zxl.xxoo.utils.Annotations;

/**
 * 日期注解处理
 * @author 左潇龙
 * @version 1.0.0
 * @since 1.0.0
 */
public class DateAnnotationHandler {

	/**
	 * 处理构建xml过程中的注解
	 * @param field field
	 * @param fieldValue fieldValue
	 * @return String
	 */
	public static String handle(final Field field,final Object fieldValue){
		if (field.getType() != Date.class) {
			return fieldValue.toString();
		}
		Annotation annotation = Annotations.getAnnotation(field, ADate.class);
		if (annotation != null && annotation instanceof ADate) {
			ADate dateAnnotation = (ADate) annotation;
		    String format = dateAnnotation.format();
			if (format != null) {
				return new SimpleDateFormat(format).format((Date)fieldValue);
			}
		}
		return fieldValue.toString();
	}
	
	/**
	 * 处理解析xml过程中的注解
	 * @param field field
	 * @param fieldValue fieldValue
	 * @return Date
	 * @throws ParseException 解析日期字符串异常
	 */
	public static Date handle(final Field field,final String fieldValue) throws ParseException{
		if (field.getType() != Date.class) {
			return new SimpleDateFormat().parse(fieldValue);
		}
		Annotation annotation = Annotations.getAnnotation(field, ADate.class);
		if (annotation != null && annotation instanceof ADate) {
			ADate dateAnnotation = (ADate) annotation;
		    String format = dateAnnotation.format();
			if (format != null) {
				return new SimpleDateFormat(format).parse(fieldValue);
			}
		}
		return XmlProcessor.DEFAULT_DATE_FORMAT.parse(fieldValue);
	}
}
