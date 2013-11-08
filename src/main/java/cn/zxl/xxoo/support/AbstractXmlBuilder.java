package cn.zxl.xxoo.support;

import cn.zxl.xxoo.processor.XmlBulider;
/**
 * xml构建器抽象类，实现了一个xml构建器的基本特性，定义了构建器的模板步骤。
 * @author 左潇龙
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbstractXmlBuilder implements XmlBulider{
	
	/**
	 * 默认头部
	 */
	public static final String DEFAULT_XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	
	/**
	 * 前置标签前缀
	 */
	public static final String TAG_HEAD_PREFIX = "<";
	
	/**
	 * 后置标签前缀
	 */
	public static final String TAG_END_PREFIX = "</";
	
	/**
	 * 标签后缀
	 */
	public static final String TAG_SUFFIX = ">";
	
	/**
	 * 换行符
	 */
	public static final String LINE = "\r\n";
	
	/**
	 * 制表符
	 */
	public static final String TAB = "\t";
	
	/**
	 * 默认无格式
	 */
	public static final Format DEFAULT_FORMAT = Format.NO_SPACE;
	
	/**
	 * xml头部
	 */
    protected String header = DEFAULT_XML_HEADER;
	
    /**
	 * xml格式，默认无格式
	 */
    protected Format format = DEFAULT_FORMAT;
	
	/**
	 * xml缓冲
	 */
    protected final StringBuffer xmlStringBuffer = new StringBuffer();
	
	public AbstractXmlBuilder() {
		super();
	}

	public AbstractXmlBuilder(Format format) {
		super();
		this.format = format;
	}

	public AbstractXmlBuilder(String header) {
		super();
		this.header = header;
	}

	public AbstractXmlBuilder(String header, Format format) {
		super();
		this.header = header;
		this.format = format;
	}
	
	public String buildXml(Object object) {
		prepareBuild();
		appendHeader(xmlStringBuffer,object);
		appendBody(xmlStringBuffer,object);
		finishBuild();
		return xmlStringBuffer.toString();
	}
	
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	/**
	 * 准备构建
	 */
	public abstract void prepareBuild();
	
	/**
	 * 构建头部
	 * @param stringBuffer
	 * @param object
	 */
	public abstract void appendHeader(StringBuffer stringBuffer,Object object);
	
	/**
	 * 构建主体
	 * @param stringBuffer
	 * @param object
	 */
	public abstract void appendBody(StringBuffer stringBuffer,Object object);
	
	/**
	 * 完成构建
	 */
	public abstract void finishBuild();

}
