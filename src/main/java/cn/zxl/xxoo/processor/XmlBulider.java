package cn.zxl.xxoo.processor;

/**
 * xml构建器接口，定义了xml构建器的标准。
 * @author 左潇龙
 * @version 1.0.0
 * @since 1.0.0
 */
public interface XmlBulider extends XmlProcessor{

	/**
	 * 创新一个xml
	 * @param object object
	 * @return xml
	 */
	String buildXml(Object object);
	
	/**
	 * 获取xml头部
	 * @return header
	 */
	String getHeader();

	/**
	 * 设置xml头部
	 * @param header
	 */
	void setHeader(String header);

	/**
	 * 获取xml格式
	 * @return format
	 */
	Format getFormat();

	/**
	 * 设置xml格式
	 * @param format
	 */
	void setFormat(Format format);
	
	/**
	 * xml格式枚举类，包含三种格式，分别为无格式、只换行、以及标准格式。
	 * @author 左潇龙
	 * @version 1.0.0
	 */
    public static enum Format{
    
    	NO_SPACE,ONLY_LINE,TAB_AND_LINE
    	
    }
	
}
