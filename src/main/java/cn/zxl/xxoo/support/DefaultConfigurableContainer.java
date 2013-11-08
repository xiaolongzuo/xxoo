package cn.zxl.xxoo.support;



/**
 * 默认的可配置容器实现，采用默认的xml解析器和xml构建器
 * @author 左潇龙
 * @version 1.0.0
 * @since 1.0.0
 */
public class DefaultConfigurableContainer extends AbstractConfigurableContainer{
	
	{
		xmlBulider = new DefaultConfigurableXmlBuilder();
		xmlParser = new DomConfigurableXmlParser(clazz);
	}
	
	public <T> DefaultConfigurableContainer(Class<T> clazz) {
		super(clazz);
	}
	
	public <T> DefaultConfigurableContainer(int length,Class<T> clazz) {
		super(length, clazz);
	}

	public <T> DefaultConfigurableContainer(String xml,Class<T> clazz) {
		super(xml, clazz);
	}

	public <T> DefaultConfigurableContainer(T object,Class<T> clazz) {
		super(object, clazz);
	}

	public <T> DefaultConfigurableContainer(String[] xmlArray,Class<T> clazz) {
		super(xmlArray, clazz);
	}

	public <T> DefaultConfigurableContainer(T[] objectArray,Class<T> clazz) {
		super(objectArray, clazz);
	}

	public <T> T convertXml2Object(String xml) {
		return xmlParser.parseXml(xml);
	}

	public <T> String convertObject2Xml(T object) {
		return xmlBulider.buildXml(object);
	}
	

}
