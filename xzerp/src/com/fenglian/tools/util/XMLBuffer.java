/*
 * 创建日期 2005-7-12
 * 作者：张阳斌
 */
package com.fenglian.tools.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.fenglian.tools.logger.FileLogger;

/**
 * @author 张阳斌 创建日期： 2005-7-12
 * 
 * 类描述：完成配置文件读取的缓冲，将配置文件文档类保存到缓冲中，当发现有更新则重新读取。
 * 
 * 应用场合：获取配置文件类
 * 
 * 版本：V0.1 
 */
public class XMLBuffer {

	//保存xml文档静态变量
	private static Hashtable xmlDocument = new Hashtable();

	//保存文件最后修改时间静态变量

	public static String ContextPath = ""; //应用跟路径，必须要初始化

	public static final String DICTIONARE_FILE = "Dictionary_Config.xml";



	/**
	 * 
	 * 功能：
	 * @param xmlFileName  配置文件中配置健名称，
	 * 		DATAWINDOW-QUERY  查询描述文件
	 * 		DATAWINDOW-DATA   数据及页面显示描述文件
	 * 
	 * @return
	 */
	public Document getDocument(String urlpath) {

	

		if (!XMLBuffer.xmlDocument.containsKey(urlpath)) {
			try {
				
				FileLogger.debug("读取配置文件路径：" + urlpath);
				
				SAXReader saxReader = new SAXReader();
				
				Document read_doc = saxReader.read(UrlLoader.loadResource(urlpath),
						"utf-8");
				XMLBuffer.xmlDocument.put(urlpath, read_doc);

				// System.out.println(read_doc.asXML());

			} catch (Exception e) {
				e.printStackTrace();
				//throw new DataWindowException(e.toString());
				return null;
			}
		}
		Document read_doc1 = (Document) XMLBuffer.xmlDocument.get(urlpath);
		return (Document) read_doc1.clone();
	}

	
	
	/**
	 * 功能：获取任意文档的xpath查询结果
	 * @param xmlFileName  xml文档路径及名称，或数据维护窗口固定描述文件。 
	 * @param xpath 查询元素语句，使用标志的xpath语句，本方法查询多元素结果，不支持查询属性结果。
	 * @return 返回序列元素类型是：Element, Attribute, Text, CDATA, Comment, ProcessingInstruction, Boolean, Double, or String
	 * @throws JDOMException
	 */
	public static List xpathSelectNodes(String xmlFileName, String xpath) {
		XMLBuffer xml = new XMLBuffer();
		Document doc = xml.getDocument(xmlFileName);
		return doc.selectNodes(xpath);
	}

	/**
	 * 功能：查询单一节点元素
	 * @param xmlFileName
	 * @param xpath
	 * @return 根据查询不同可能方法的类型是: Element, Attribute, Text, CDATA, Comment, ProcessingInstruction, Boolean, Double, String, or null if no item was selected.
	 * @throws JDOMException
	 */
	public static Object xpathSelectSingleNodes(String xmlFileName, String xpath) {
		XMLBuffer xml = new XMLBuffer();
		Document doc = xml.getDocument(xmlFileName);
		return doc.selectSingleNode(xpath);
	}

	/**
	 * 
	 * 功能：完成xml文档对象加载样式操作。将xml与xslt结合
	 * 
	 * @param stylesheet
	 *            样式模板：xslt样式表文件名称。
	 * @param in
	 *            ：输入的文档
	 * @return 返回新的文档内容。
	 * @throws JDOMException
	 */
	public static Document transform(String stylesheet, Document in)

	{

		/* xml（头）格式页面 */
		//			page_doc = ActionDataWindow.transform(xslt,page_doc);
		//			XMLOutputter xmlout = new
		// XMLOutputter(Format.getPrettyFormat().setEncoding("gb2312"));
		//			xmlout.output(page_doc,out);
		try {			
			
			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer(new StreamSource(UrlLoader.loadResource(stylesheet)));

			DocumentResult out = new DocumentResult();

			transformer.transform(new DocumentSource(in), out);
			return out.getDocument();
		} catch (Exception e) {
			throw new RuntimeException("XSLT Trandformation failed", e);
		}
	}

	/**
	 * 
	 * 功能：完成xml文档对象加载样式操作。将xml与xslt结合
	 * 
	 * @param xsltFile
	 *            样式模板：xslt样式表文件名称。
	 * @param in
	 *            ：输入的文档
	 * @return 返回新的文档内容。
	 * @throws JDOMException
	 */
	public static void transform(Document in, OutputStream out, String xsltFile) {
		try {
			
			Source xmlSource = new DocumentSource(in);
			Source xslSource = new StreamSource(UrlLoader.loadResource(xsltFile));
			Result result = new StreamResult(out);
			TransformerFactory factory = TransformerFactory.newInstance();

			Transformer transformer = factory.newTransformer(xslSource);

			transformer.transform(xmlSource, result);

		} catch (Exception e) {
			throw new RuntimeException("XSLT Trandformation failed", e);
		}
	}

	/**
	 * 功能：xml文档或元素输出到控制台
	 * @param d  输出xml文档元素
	 */
	public static void print(Object d) {
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("gb2312");

			XMLWriter xmlout = new XMLWriter(System.out, format);
			if (FileLogger.isDebugEnabled()) {
				xmlout.write(d);
				xmlout.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}