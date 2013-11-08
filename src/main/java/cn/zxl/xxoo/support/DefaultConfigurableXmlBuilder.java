package cn.zxl.xxoo.support;

import java.lang.reflect.Field;
import java.util.Date;

import cn.zxl.xxoo.annotation.DateAnnotationHandler;
import cn.zxl.xxoo.exception.XmlBuildException;
import cn.zxl.xxoo.utils.Commons;
import cn.zxl.xxoo.utils.Reflects;


/**
 * 默认的xml构建器实现
 * @author 左潇龙
 * @version 1.0.0
 * @since 1.0.0
 */
public class DefaultConfigurableXmlBuilder extends AbstractXmlBuilder{
	
	/**
	 * 默认的初始层次
	 */
	public static final int START_LEVEL = 1;
	
	public DefaultConfigurableXmlBuilder() {
		super();
	}

	public DefaultConfigurableXmlBuilder(Format format) {
		super(format);
	}

	public DefaultConfigurableXmlBuilder(String header, Format format) {
		super(header, format);
	}

	public DefaultConfigurableXmlBuilder(String header) {
		super(header);
	}

	public void appendHeader(final StringBuffer stringBuffer, final Object object) {
		stringBuffer.append(header).append(LINE);
    }

	public void appendBody(final StringBuffer stringBuffer, final Object object){
		if (object == null) {
			return;
		}
		try {
			appendRecursion(stringBuffer, object, object.getClass().getSimpleName(), START_LEVEL);
		} catch (IllegalArgumentException e) {
			throw new XmlBuildException("xml构建异常，此异常是由于采用反射获取属性中的值时发现参数不合法，请仔细检查你传入的对象。",e);
		} catch (IllegalAccessException e) {
			throw new XmlBuildException("xml构建异常，此异常是由于采用反射获取属性中的值时发现访问被禁止，请仔细检查你传入的对象的属性都可以正常访问。",e);
		}
	}
	
	void appendRecursion(final StringBuffer stringBuffer,Object object,String tagName,int level) throws IllegalArgumentException, IllegalAccessException{
		if (Commons.isNull(object)) {
			return;
		}
		final int currentLevel = level;
		Class<?> clazz = object.getClass();
		if (!Reflects.hasField(clazz)) {
			insertHeadTag(stringBuffer, tagName);
			if (needLine(format)) {
				insertLine(stringBuffer);
			}
			insertEndTag(stringBuffer, tagName);
		}else {
			Field[] fields = clazz.getDeclaredFields();
			insertHeadTag(stringBuffer, tagName);
			if (needLine(format)) {
				insertLine(stringBuffer);
			}
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				Object fieldValue = field.get(object);
				if (fieldValue == null) {
					continue;
				}
				if (needLine(format) && i != 0) {
					insertLine(stringBuffer);
				}
				if (needTab(format)) {
					insertTab(stringBuffer, currentLevel);
				}
				if (Reflects.isComplexType(field.getType()) && !Iterable.class.isAssignableFrom(field.getType())) {
					appendRecursion(stringBuffer,fieldValue,field.getName(),currentLevel + 1);
				}else if (Iterable.class.isAssignableFrom(field.getType())) {
					final int currentArrayLevel = currentLevel + 1;
					insertHeadTag(stringBuffer, field.getName());
					if (needLine(format)) {
						insertLine(stringBuffer);
					}
					Class<?> iterableGenericType = Reflects.getArrayGenericType(field);
					int count = 0;
					for (Object tempObject : ((Iterable)fieldValue)) {
						if (needLine(format) && count++ != 0) {
							insertLine(stringBuffer);
						}
						if (needTab(format)) {
							insertTab(stringBuffer, currentArrayLevel);
						}
						if (!Reflects.isComplexType(iterableGenericType)) {
							insertSimpleType(stringBuffer,iterableGenericType.getSimpleName(), field, tempObject);
						}else {
							appendRecursion(stringBuffer,tempObject,tempObject.getClass().getSimpleName(),currentArrayLevel + 1);
						}
					}
					if (needLine(format)) {
						insertLine(stringBuffer);
					}
					if (needTab(format)) {
						insertTab(stringBuffer, currentArrayLevel - 1);
					}
					insertEndTag(stringBuffer, field.getName());
				}else {
					insertSimpleType(stringBuffer,field.getName(), field, fieldValue);
				}
			}
			if (needLine(format)) {
				insertLine(stringBuffer);
			}
			if (needTab(format)) {
				insertTab(stringBuffer, currentLevel - 1);
			}
			insertEndTag(stringBuffer, tagName);
		}
	}
	
	void insertSimpleType(final StringBuffer stringBuffer,String tagName,Field field,Object fieldValue){
		stringBuffer.append(TAG_HEAD_PREFIX).append(tagName).append(TAG_SUFFIX);
		if (field.getType() == Date.class) {
			stringBuffer.append(DateAnnotationHandler.handle(field, fieldValue));
		}else {
			stringBuffer.append(fieldValue);
		}
		stringBuffer.append(TAG_END_PREFIX).append(tagName).append(TAG_SUFFIX);
	}
	
	void insertHeadTag(final StringBuffer stringBuffer,String tagName){
		stringBuffer.append(TAG_HEAD_PREFIX).append(tagName).append(TAG_SUFFIX);
	}
	
	void insertEndTag(final StringBuffer stringBuffer,String tagName){
		stringBuffer.append(TAG_END_PREFIX).append(tagName).append(TAG_SUFFIX);
	}
	
	void insertTab(final StringBuffer stringBuffer,int number){
		for (int i = 0; i < number; i++) {
			stringBuffer.append(TAB);
		}
	}
	
	void insertLine(final StringBuffer stringBuffer){
		stringBuffer.append(LINE);
	}
	
	boolean needLine(Format format){
		if (format.equals(Format.ONLY_LINE) || format.equals(Format.TAB_AND_LINE)) {
			return true;
		}
		return false;
	}
	
	boolean needTab(Format format){
		if (format.equals(Format.TAB_AND_LINE)) {
			return true;
		}
		return false;
	}
	
	public void prepareBuild() {}

	public void finishBuild() {}

}
