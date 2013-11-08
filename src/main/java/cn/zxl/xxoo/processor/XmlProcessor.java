package cn.zxl.xxoo.processor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * xml处理器接口，为了统一xml构建器和xml解析器的标准
 * @author 左潇龙
 * @version 1.0.0
 * @since 1.0.0
 */
public interface XmlProcessor {
	
	/**
	 * 所有处理器统一的默认日期格式
	 */
	DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.ENGLISH);
	
}
