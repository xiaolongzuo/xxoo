package cn.zxl.xxoo.container;


/**
 * 容器父接口，定义了容器的标准
 * @author 左潇龙
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Container {

	/**
	 * 获取容器中的xml数组
	 * @return xml数组
	 */
    String[] getXmlArray();
    
    /**
     * 获取容器中的object数组
     * @return object数组
     */
    <T> T[] getObjectArray();
    
    /**
     * 默认返回容器中第一个xml
     * @return xml
     */
    String getXml();
    
    /**
     * 默认返回容器中第一个object
     * @return object
     */
    <T> T getObject();
    
    /**
     * 返回容器中指定索引的xml
     * @param index 索引
     * @return xml
     */
    String getXml(int index);
    
    /**
     * 返回容器中指定索引的object
     * @param index 索引
     * @return object
     */
    <T> T getObject(int index);
    
    /**
     * 向容器中加入一个xml，该动作会同时向object数组中也加入一个object。
     * @param xml xml
     * @return 是否添加成功
     */
    boolean add(String xml);
    
    /**
     * 向容器中加入一个object，该动作会同时向xml数组中也加入一个xml。
     * @param object object
     * @return 是否添加成功
     */
    <T> boolean add(T object);
    
    /**
     * 判断容器中是否包含一个xml
     * @param xml xml
     * @return boolean
     */
    boolean contains(String xml);

    /**
     * 判断容器中是否包含一个object
     * @param object object
     * @return boolean
     */
    <T> boolean contains(T object);
	
    /**
     * 从容器中移除一个xml，该动作会同时从object数组中也移除一个object。
     * @param xml xml
     * @return 是否移除成功
     */
	boolean remove(String xml);
	
	/**
     * 从容器中移除一个object，该动作会同时从xml数组中也移除一个xml。
     * @param object object
     * @return 是否移除成功
     */
	<T> boolean remove(T object);

	/**
	 * 判断容器中是否包含一个xml数组
	 * @param xmlArray xml数组
	 * @return boolean
	 */
	boolean containsAll(String[] xmlArray);
	
	/**
	 * 判断容器中是否包含一个object数组
	 * @param objectArray object数组
	 * @return boolean
	 */
	<T> boolean containsAll(T[] objectArray);

	/**
	 * 向容器中加入一个xml数组，该动作会同时从object数组中也加入相同数量的object。
	 * @param xmlArray xml数组
	 * @return 是否添加成功
	 */
	boolean addAll(String[] xmlArray);
	
	/**
	 * 向容器中加入一个object数组，该动作会同时从xml数组中也加入相同数量的xml。
	 * @param objectArray object数组
	 * @return 是否添加成功
	 */
	<T> boolean addAll(T[] objectArray);

	/**
	 * 从容器中移除一个xml数组，该动作会同时从object数组中也移除相同数量的object。
	 * @param xmlArray xml数组
	 * @return 是否移除成功
	 */
	boolean removeAll(String[] xmlArray);
	
	/**
	 * 从容器中移除一个object数组，该动作会同时从xml数组中也移除相同数量的xml。
	 * @param objectArray object数组
	 * @return 是否移除成功
	 */
	<T> boolean removeAll(T[] objectArray);

	/**
	 * 使容器中仅保留参数数组中的元素
	 * @param xmlArray xml数组
	 * @return 是否成功
	 */
	boolean retainAll(String[] xmlArray);
	
	/**
	 * 使容器中仅保留参数数组中的元素
	 * @param objectArray object数组
	 * @return boolean
	 */
	<T> boolean retainAll(T[] objectArray);
	
	/**
	 * 容器是否为空
	 * @return boolean
	 */
	boolean isEmpty();
	
	/**
	 * 容器大小
	 * @return int
	 */
	int size();
	
	/**
	 * 清除空值
	 */
	<T> void trim();

	/**
	 * 清空容器
	 */
	void clear() ;

}
