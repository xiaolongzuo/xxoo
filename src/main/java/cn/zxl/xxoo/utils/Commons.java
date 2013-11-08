package cn.zxl.xxoo.utils;


/**
 * 工具类
 * @author 左潇龙
 * @version 1.0.0
 * @since 1.0.0
 */
public class Commons {
	
	private Commons(){}
	
	/**
	 * 判断是否为空
	 * @param o
	 * @return boolean
	 */
	public static boolean isNull(Object o){
		return o == null;
	}
	
	/**
	 * 判断数组是否为空
	 * @param objects
	 * @return boolean
	 */
	public static boolean isEmpty(Object[] objects){
		return objects == null ? true : objects.length == 0 ? true : false; 
	}
	
}
