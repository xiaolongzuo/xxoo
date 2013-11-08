package cn.zxl.xxoo.support;

import cn.zxl.xxoo.processor.XmlParser;
/**
 * xml解析器抽象类，将执行解析操作推迟到子类。
 * @author 左潇龙
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbstractXmlParser implements XmlParser{
	
	/**
	 * 基类型
	 */
	protected Class<?> clazz;
	
	public <T> AbstractXmlParser(Class<T> clazz){
		this.clazz = clazz;
	}

    public <T> T parseXml(String xml) {
		return doParseXml(xml);
	}
    
    /**
     * 执行解析操作
     * @param xml
     * @return object
     */
    public abstract <T> T doParseXml(String xml);

}
