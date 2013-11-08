package cn.zxl.xxoo.support;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cn.zxl.xxoo.annotation.DateAnnotationHandler;
import cn.zxl.xxoo.exception.XmlParseException;
import cn.zxl.xxoo.utils.Commons;
import cn.zxl.xxoo.utils.Reflects;


/**
 * 基于DOM的xml解析器实现，也是默认容器的默认xml解析器。
 * @author 左潇龙
 * @version 1.0.0
 * @since 1.0.0
 */
public class DomConfigurableXmlParser extends AbstractXmlParser {

	public <T> DomConfigurableXmlParser(Class<T> clazz) {
		super(clazz);
	}

	public <T> T doParseXml(String xml) {
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dombuilder = domfac.newDocumentBuilder();
			ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes());
			Document doc = dombuilder.parse(inputStream);
			Element root = doc.getDocumentElement();
			return (T) parseRecursion(clazz, root);
		} catch (ParserConfigurationException e) {
			throw new XmlParseException("文档构建器创建失败：未找到达到配置要求的文档构建器",e);
		} catch (FileNotFoundException e) {
			throw new XmlParseException("未找到xml流",e);
		} catch (SAXException e) {
			throw new XmlParseException("DOM解析过程发生错误",e);
		} catch (IOException e) {
			throw new XmlParseException("IO过程发生错误",e);
		} catch (IllegalArgumentException e) {
			throw new XmlParseException("传入的xml可能为空",e);
		} catch (SecurityException e) {
			throw new XmlParseException("基本类型的带String参数的构造方法被拒绝访问",e);
		} catch (IllegalAccessException e) {
			throw new XmlParseException("设置属性值时被拒绝访问",e);
		} catch (InstantiationException e) {
			throw new XmlParseException("尝试采用默认无参构造方法初始化实体时失败",e);
		} catch (ParseException e) {
			throw new XmlParseException("解析日期错误",e);
		} catch (NoSuchMethodException e) {
			throw new XmlParseException("基本类型的带String参数的构造方法未找到",e);
		} catch (InvocationTargetException e) {
			throw new XmlParseException("隐藏的构造方法错误",e);
		}
	}

	<T> T parseRecursion(Class<T> clazz,Node root) throws IllegalArgumentException, IllegalAccessException, InstantiationException, ParseException, SecurityException, NoSuchMethodException, InvocationTargetException{
		if (Commons.isNull(clazz) || !Reflects.isComplexType(clazz)) {
			return null;
		}
		T object = (T) Reflects.getInstance(clazz);
		if (!Reflects.hasField(clazz)) {
			return object;
		}
		NodeList nodeList = root.getChildNodes();
        for (int i = 0 ; i < nodeList.getLength() ; i++) {
        	Node node = nodeList.item(i);
        	if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
        	String nodeName = node.getNodeName();
        	String nodeValue = node.getTextContent();
        	if (nodeName == null || nodeValue == null) {
				continue;
			}
			Field field = null;
			try {
				field = clazz.getDeclaredField(nodeName);
			}catch (NoSuchFieldException e) {
				continue;
			}
			field.setAccessible(true);
			if (!Reflects.isComplexType(field.getType()) && nodeValue.trim().length() > 0 ) {
				Constructor constructor = field.getType().getConstructor(String.class);
				if (Date.class.isAssignableFrom(field.getType())) {
					field.set(object, DateAnnotationHandler.handle(field, nodeValue));
				}else if(constructor != null){
					constructor.setAccessible(true);
					field.set(object, constructor.newInstance(nodeValue));
				}else if(field.getType() == char.class || field.getType() == Character.class){
					field.set(object, nodeValue.charAt(0));
				}
			}else if (!Reflects.isComplexType(field.getType()) && nodeValue.trim().length() == 0 ) {
				field.set(object, field.getType().newInstance());
			}else if (Reflects.isComplexType(field.getType()) && Iterable.class.isAssignableFrom(field.getType())) {
				Class<?> iterableGenericType = Reflects.getArrayGenericType(field);
				Iterable iterable = null;
				if (List.class.isAssignableFrom(field.getType())) {
					iterable = new ArrayList();
				}
				if (Set.class.isAssignableFrom(field.getType())) {
					iterable = new HashSet();
				}
				NodeList iterableNodeList = node.getChildNodes();
				for (int j = 0 ; j < iterableNodeList.getLength() ; j++) {
					Node iterableNode = iterableNodeList.item(j);
					if (iterableNode.getNodeType() != Node.ELEMENT_NODE) {
						continue;
					}
					if (List.class.isAssignableFrom(field.getType())) {
						((List)iterable).add(parseRecursion(iterableGenericType, iterableNode));
					}
					if (Set.class.isAssignableFrom(field.getType())) {
						((Set)iterable).add(parseRecursion(iterableGenericType, iterableNode));
					}
				}
				field.set(object, iterable);
			}else {
				field.set(object, parseRecursion(field.getType(),node));
			}
		}
		return object;
	}
	
}
