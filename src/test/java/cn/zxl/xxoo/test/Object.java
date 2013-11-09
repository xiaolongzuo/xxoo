package cn.zxl.xxoo.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.zxl.xxoo.annotation.ADate;

/**
 * 用于测试的对象
 * @author 左潇龙
 * @version 1.0.0
 * @since 1.0.0
 */
public class Object implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String stringParam = "stringValue";
	
	@ADate(format = "yyyy-MM-dd hh")//日期注解，设置日期格式
	private Date dateParam = new Date();
	
	private Object testParam;
	
	private List<Object> testListParam;

	public String getStringParam() {
		return stringParam;
	}

	public void setStringParam(String stringParam) {
		this.stringParam = stringParam;
	}

	public Date getDateParam() {
		return dateParam;
	}

	public void setDateParam(Date dateParam) {
		this.dateParam = dateParam;
	}

	public Object getTestParam() {
		return testParam;
	}

	public void setTestParam(Object testParam) {
		this.testParam = testParam;
	}

	public List<Object> getTestListParam() {
		return testListParam;
	}

	public void setTestListParam(List<Object> testListParam) {
		this.testListParam = testListParam;
	}
	
	/**
	 * 提供一个创建测试对象的方法
	 */
	public static Object createObject(){
		Object t = new Object();
		Object t1 = new Object();
		Object t2 = new Object();
		Object t3 = new Object();
		Object t4 = new Object();
		Object t5 = new Object();
		Object t6 = new Object();
		Object t7 = new Object();
		Object t8 = new Object();
		Object t9 = new Object();
		Object t10 = new Object();
		List<Object> testList2 = new ArrayList<Object>();
		testList2.add(t1);
		testList2.add(t2);
		List<Object> testList = new ArrayList<Object>();
		t10.testListParam = testList2;
		testList.add(t10);
		testList.add(t9);
		testList.add(t8);
		testList.add(t7);
		testList.add(t6);
		t5.testListParam = testList;
		t5.testParam = t4;
		t3.testParam = t5;
		t.testParam = t3;
		return t;
	}
	
	public String toString(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("stringParam:" + (stringParam == null? "" : stringParam) + "\r\n")
		.append("dateParam:" + (dateParam == null ? "" : dateParam) + "\r\n")
		.append("testParam:" + (testParam == null ? "" : testParam) + "\r\n");
		if (testListParam != null) {
			stringBuffer.append("testListParam:\r\n");
			for (Object temp : testListParam) {
				stringBuffer.append(temp + "\r\n");
			}
		}
		return stringBuffer.toString();
	}
}
