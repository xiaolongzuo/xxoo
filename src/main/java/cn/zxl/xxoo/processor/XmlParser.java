package cn.zxl.xxoo.processor;

/**
 * xml解析器接口，定义了xml解析器的标准。
 * @author 左潇龙
 * @version 1.0.0
 * @since 1.0.0
 */
public interface XmlParser extends XmlProcessor{

	/**
	 * 解析一个xml
	 * @param xml
	 * @return object
	 */
	<T> T parseXml(String xml);
}
