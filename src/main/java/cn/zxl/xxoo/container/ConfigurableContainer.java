package cn.zxl.xxoo.container;

import cn.zxl.xxoo.processor.XmlBulider;
import cn.zxl.xxoo.processor.XmlParser;
/**
 * 可配置容器父接口，定义了可配置容器的标准，实现该接口的容器可配置xml解析器、xml构建器以及容器基类型。如果要想使用可配置的容器，需要放弃父接口container使用该接口，或直接由父接口向下强转，但容器实现类必须实现了可配置容器接口。
 * @author 左潇龙
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ConfigurableContainer extends Container{
    
	/**
	 * 获取xml解析器
	 * @return xml解析器
	 */
	XmlParser getXmlParser();

	/**
	 * 设置xml解析器
	 * @param xmlParser xml解析器
	 */
	void setXmlParser(XmlParser xmlParser);

	/**
	 * 获取xml构建器
	 * @return xml构建器
	 */
	XmlBulider getXmlBulider();

	/**
	 * 设置xml构建器
	 * @param xmlBulider xml构建器
	 */
	void setXmlBulider(XmlBulider xmlBulider);
	
	/**
	 * 设置容器基类型
	 * @param clazz 基类型
	 */
	<T> void setClass(Class<T> clazz);
	
}
