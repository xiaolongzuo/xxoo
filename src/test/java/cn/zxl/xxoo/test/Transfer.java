package cn.zxl.xxoo.test;

import java.io.IOException;

import cn.zxl.xxoo.container.ConfigurableContainer;
import cn.zxl.xxoo.container.Container;
import cn.zxl.xxoo.processor.XmlBulider;
import cn.zxl.xxoo.processor.XmlBulider.Format;
import cn.zxl.xxoo.support.DefaultConfigurableContainer;

public class Transfer {

	public static void main(String[] args) throws IOException {
		//测试不可配置的容器
		useUnconfigurableContainer("E:/test1.xml");
		//测试可配置的容器
		useConfigurableContainer("E:/test2.xml");
	}
	
	public static void useUnconfigurableContainer(String path) throws IOException{
		//不可配置的容器
		Container container = new DefaultConfigurableContainer(Object.class);
		//向容器中添加一个复杂的Test对象
		container.add(Object.createObject());
		//获取容器自动解析的xml内容
		String xml = container.getXml();
		//将xml内容存放在一个文件中
		FileUtils.write(path, xml);
	}
	
	public static void useConfigurableContainer(String path) throws IOException{
		//可配置的容器，使用可配置的容器接口，推荐此种方式，比较灵活
		ConfigurableContainer configurableContainer = new DefaultConfigurableContainer(Object.class);
		XmlBulider xmlBulider = configurableContainer.getXmlBulider();
		//设置构建器的xml格式
		xmlBulider.setFormat(Format.TAB_AND_LINE);
		//改变容器中的构建器
		configurableContainer.setXmlBulider(xmlBulider);
		//向可配置容器添加复杂对象
		configurableContainer.add(Object.createObject());
		//获取容器自动解析的xml内容，比较下不能配置的容器构建的xml格式和日期格式
		String configXml = configurableContainer.getXml(0);//等同于getXml()
		//将xml内容存放在一个文件中
		FileUtils.write(path, configXml);
		
		/*             ---------------------------------               */
		
		//再将xml从test2.xml中读取出来
		String readableConfigXml = FileUtils.read(path);
		//向容器中再加入一个xml
		configurableContainer.add(readableConfigXml);
		//获取容器自动解析的对象
		//因为之前已经加入了一个对象，所以在加入xml之前，容器中已包含一对xml和object，此时索引为1
		//容器维护了两个保持一致的数组，分别存放xml和object，索引规则与数组一致，从0开始
		Object test = configurableContainer.getObject(1);
		//打印容器大小
		System.out.println("size:" + configurableContainer.size());
		//打印解析的对象，打印的可能不太清楚
		//要想打印格式清晰，与我当初构建xml时相似，过程比较复杂，就不写那么详细了，各位可以自己加断点看对象内容
		System.out.println(test);
	}
	
}
