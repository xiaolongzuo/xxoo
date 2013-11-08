package cn.zxl.xxoo.support;

import cn.zxl.xxoo.container.Container;
import cn.zxl.xxoo.utils.Commons;

/**
 * 容器抽象类，实现了一个容器的基本特性，该抽象容器将维护两个数量保持一致的xml数组和object数组，并且每当向其中添加任何一方，都将自动填充另外一个数组方法使用请参照容器接口方法说明。
 * @author 左潇龙
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbstractContainer implements Container{
	
	/**
	 * 默认长度
	 */
	public static final int DEFAULT_LENGTH = 10;
	
	/**
	 * 默认扩展长度
	 */
	public static final int DEFAULT_EXPAND_LENGTH = 5;
	
	/**
	 * 默认扩展基数
	 */
	public static final int DEFAULT_EXPAND_TIMES = 1;

	/**
	 * xml数组
	 */
	protected String[] xmlArray;
	
	/**
	 * object数组
	 */
	protected Object[] objectArray;
	
	/**
	 * 容器大小
	 */
	protected int size;
	
	/**
	 * 容器基类型
	 */
	protected Class<?> clazz;
	
	public <T> AbstractContainer(Class<T> clazz){
		init(this.size, DEFAULT_LENGTH, clazz);
	}
	
	public <T> AbstractContainer(int length,Class<T> clazz){
		if (length <= 0) {
			length = DEFAULT_LENGTH;
		}
		init(this.size, length, clazz);
	}
	
	public <T> AbstractContainer(String xml,Class<T> clazz){
		this(new String[]{xml}, clazz);
	}
	
	public <T> AbstractContainer(T object,Class<T> clazz){
		init(1, 1, clazz);
		this.objectArray[0] = object;
		this.xmlArray[0] = convertObject2Xml(object);
	}
	
	public <T> AbstractContainer(String[] xmlArray,Class<T> clazz){
		if (Commons.isEmpty(xmlArray)) {
			return;
		}
		this.xmlArray = xmlArray;
		this.size = xmlArray.length;
		this.objectArray = (T[]) new Object[xmlArray.length];
		for (int i = 0; i < xmlArray.length; i++) {
			T object = convertXml2Object(xmlArray[i]);
			this.objectArray[i] = object;
		}
	}
	
	public <T> AbstractContainer(T[] objectArray,Class<T> clazz){
		if (Commons.isEmpty(objectArray)) {
			return;
		}
		this.objectArray = objectArray;
		this.size = objectArray.length;
		this.xmlArray = new String[objectArray.length];
		for (int i = 0; i < objectArray.length; i++) {
			String xml = convertObject2Xml(objectArray[i]);
			this.xmlArray[i] = xml;
		}
	}
	
	final <T> void init(int size,int length,Class<T> clazz){
		this.clazz = clazz;
		this.size = size;
		this.objectArray = (T[]) new Object[length];
		this.xmlArray = new String[length];
	}

	public String[] getXmlArray() {
		return xmlArray;
	}

	public <T> T[] getObjectArray() {
		return (T[]) objectArray;
	}
	
	public String getXml() {
		if (size > 0) {
			return this.xmlArray[0];
		}
		return null;
	}

	public <T> T getObject() {
		if (size > 0) {
			return (T) this.objectArray[0];
		}
		return null;
	}
	
	public String getXml(int index) {
		return this.xmlArray[index];
	}

	public <T> T getObject(int index) {
		return (T) this.objectArray[index];
	}

	public boolean add(String xml) {
		if (Commons.isNull(xml)) {
			return false;
		}
		if (this.xmlArray.length == size) {
			expand(DEFAULT_EXPAND_TIMES);
		}
		this.xmlArray[size] = xml;
		this.objectArray[size] = convertXml2Object(xml);
		size++;
		return true;
	}
	
	public <T> boolean add(T object) {
		if (Commons.isNull(object)) {
			return false;
		}
		if (this.objectArray.length == size) {
			expand(DEFAULT_EXPAND_TIMES);
		}
		this.objectArray[size] = object;
		this.xmlArray[size] = convertObject2Xml(object);
		size++;
		return true;
	}

	public boolean contains(String xml) {
        for (int i = 0; i < this.xmlArray.length; i++) {
			if ((xml == null && this.xmlArray[i] == null) || this.xmlArray[i].equals(xml)) {
				return true;
			}
		}
		return false;
	}

	public <T> boolean contains(T object) {
		for (int i = 0; i < this.objectArray.length; i++) {
			if ((object == null && this.objectArray[i] == null) || this.objectArray[i].equals(object)) {
				return true;
			}
		}
		return false;
	}

	public boolean remove(String xml) {
		if (Commons.isNull(xml)) {
			trim();
			return true;
		}
		for (int i = 0; i < this.xmlArray.length; i++) {
			if (this.xmlArray[i].equals(xml)) {
				this.xmlArray[i] = null;
				this.objectArray[i] = null;
			}
		}
		trim();
		return true;
	}

	public <T> boolean remove(T object) {
		if (Commons.isNull(object)) {
			trim();
			return true;
		}
		for (int i = 0; i < this.objectArray.length; i++) {
			if (this.objectArray[i].equals(object)) {
				this.objectArray[i] = null;
				this.xmlArray[i] = null;
			}
		}
		remove(convertObject2Xml(object));
		trim();
		return true;
	}

	public boolean containsAll(String[] xmlArray) {
		if (xmlArray.length > this.xmlArray.length) {
			return false;
		}
		for (int i = 0; i < xmlArray.length ; i++) {
			if (!contains(xmlArray[i])) {
				return false;
			}
		}
		return true;
	}
	
	public <T> boolean containsAll(T[] objectArray) {
		if (objectArray.length > this.objectArray.length) {
			return false;
		}
		for (int i = 0; i < objectArray.length ; i++) {
			if (!contains(objectArray[i])) {
				return false;
			}
		}
		return true;
	}

	public boolean addAll(String[] xmlArray) {
		if (Commons.isEmpty(xmlArray)) {
			return true;
		}
		int times = xmlArray.length % DEFAULT_EXPAND_LENGTH + 1;
		expand(times);
		for (int i = 0; i < xmlArray.length; i++) {
			this.xmlArray[size++] = xmlArray[i];
			this.objectArray[size++] = convertXml2Object(xmlArray[i]);
		}
		return true;
	}
	
	public <T> boolean addAll(T[] objectArray) {
		if (Commons.isEmpty(objectArray)) {
			return true;
		}
		int times = objectArray.length % DEFAULT_EXPAND_LENGTH + 1;
		expand(times);
		for (int i = 0; i < objectArray.length; i++) {
			this.objectArray[size++] = objectArray[i];
			this.xmlArray[size++] = convertObject2Xml(objectArray[i]);
		}
		return true;
	}

	public boolean removeAll(String[] xmlArray) {
		if (Commons.isEmpty(xmlArray)) {
			return true;
		}
		for (int i = 0; i < xmlArray.length; i++) {
			if (!Commons.isNull(xmlArray[i])) {
				for (int j = 0; j < this.xmlArray.length; j++) {
					if (this.xmlArray[j].equals(xmlArray[i])) {
						this.xmlArray[j] = null;
						this.objectArray[j] = null;
					}
				}
			}
		}
		trim();
		return true;
	}
	
	public <T> boolean removeAll(T[] objectArray) {
		if (Commons.isEmpty(objectArray)) {
			return true;
		}
		for (int i = 0; i < objectArray.length; i++) {
			if (!Commons.isNull(objectArray[i])) {
				for (int j = 0; j < this.objectArray.length; j++) {
					if (this.objectArray[j].equals(objectArray[i])) {
						this.objectArray[j] = null;
						this.xmlArray[j] = null;
					}
				}
			}
		}
		trim();
		return true;
	}

	public boolean retainAll(String[] xmlArray) {
		for (int i = 0; i < this.xmlArray.length; i++) {
			boolean contains = false;
			for (int j = 0; j < xmlArray.length; j++) {
				if (xmlArray[j].equals(this.xmlArray[i])) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				remove(this.xmlArray[i]);
			}
		}
		trim();
		return true;
	}
	
	public <T> boolean retainAll(T[] objectArray) {
		for (int i = 0; i < this.objectArray.length; i++) {
			boolean contains = false;
			for (int j = 0; j < objectArray.length; j++) {
				if (objectArray[j].equals(this.objectArray[i])) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				remove(this.objectArray[i]);
			}
		}
		trim();
		return true;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public int size() {
		return this.size;
	}
	
	public final <T> void trim(){
		if (size == 0) {
			return;
		}
		int tempSize = 0;
		String[] tempXmlArray = new String[this.xmlArray.length];
		T[] tempObjectArray = (T[]) new Object[this.objectArray.length];
		for (int i = 0, j = 0; i < this.xmlArray.length; i++) {
			if (!Commons.isNull(this.xmlArray[i])) {
				tempXmlArray[j++] = this.xmlArray[i];
				tempSize++;
			}
		}
		for (int i = 0, j = 0; i < this.objectArray.length; i++) {
			if (!Commons.isNull(this.objectArray[i])) {
				tempObjectArray[j++] = (T) this.objectArray[i];
			}
		}
		this.xmlArray = tempXmlArray;
		this.objectArray = tempObjectArray;
		this.size = tempSize;
	}

	public void clear() {
		init(0, DEFAULT_LENGTH, null);
	}
	
	final <T> void expand(int times){
		String[] tempXmlArray = new String[size + DEFAULT_EXPAND_LENGTH * times];
		T[] tempObjectArray = (T[]) new Object[size + DEFAULT_EXPAND_LENGTH * times];
		System.arraycopy(this.xmlArray, 0, tempXmlArray, 0, this.xmlArray.length);
		System.arraycopy(this.objectArray, 0, tempObjectArray, 0, this.objectArray.length);
		this.xmlArray = tempXmlArray;
		this.objectArray = tempObjectArray;
	}

	/**
	 * 将一个xml转换为object
	 * @param xml
	 * @return object
	 */
	public abstract <T> T convertXml2Object(String xml);
	
	/**
	 * 将一个object转换为xml
	 * @param object
	 * @return xml
	 */
	public abstract <T> String convertObject2Xml(T object);
}
