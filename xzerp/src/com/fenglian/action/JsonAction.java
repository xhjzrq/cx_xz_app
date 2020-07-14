/**
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 * @author kevin.du  2010-11-8
 */
package com.fenglian.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.fenglian.command.JsonCommandFactory;
import com.fenglian.tools.logger.FileLogger;

/**
 * 执行请求命令，接受PAD请求，并执行CMD参数为命令的指令
 * @author zhang.yb
 */
public class JsonAction extends DispatchAction {

	/**
	 * 执行命令请求，指令内部会处理返回和异常�?�异常以request写入字符串方式返回�??
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exe(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FileLogger.debug("指令处理JSON");
		JsonCommandFactory cf = new JsonCommandFactory();
		cf.doCommand(response, request);
		return null;
	}
}