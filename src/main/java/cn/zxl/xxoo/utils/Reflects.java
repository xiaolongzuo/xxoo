package cn.zxl.xxoo.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
/**
 * 反射工具类
 * @author 左潇龙
 * @version 1.0.0
 * @since 1.0.0
 */
public class Reflects {
	
	private Reflects(){}
	
	/**
	 * 判断是否复杂类型，版本1.0.0不支持java.lang java.util java.sql包中的实体类
	 * @param clazz
	 * @return boolean
	 */
	public static boolean isComplexType(Class<?> clazz){
		if ((clazz.getName().startsWith("java.lang.") || clazz.getName().startsWith("java.util.")
				|| clazz.getName().startsWith("java.sql.")) && !clazz.isArray() && !Iterable.class.isAssignableFrom(clazz) ) {
			return false;
		}
		return true;
	}
	
	/**
	 * 是否含有属性
	 * @param clazz
	 * @return boolean
	 */
	public static boolean hasField(Class<?> clazz){
		if (clazz.getDeclaredFields() == null || clazz.getDeclaredFields().length == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 返回一个默认构造器产生的实例
	 * @param clazz
	 * @return object
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T> T getInstance(Class<T> clazz) throws InstantiationException, IllegalAccessException{
		return clazz.newInstance();
	}
	
	/**
	 * 获取数组属性中所持有的类型
	 * @param field
	 * @return class
	 */
	public static <T> Class<T> getArrayGenericType(Field field){
		Type type = field.getGenericType();
		if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			return (Class<T>) parameterizedType.getActualTypeArguments()[0];
		}
		return null;
	}
	
}
