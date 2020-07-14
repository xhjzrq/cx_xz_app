/**
 * 文件创建日期: 2004-7-8
 * 版权所有宁翔电子公司
 */
package com.fenglian.tools.util;

/**
 * @author 张阳斌
 * 
 * 类描述:完成翻页数据库的记录Bean 应用环节: 翻页查询方法，翻页标签 创建日期:2004-7-8
 */
public class PageInfo implements Cloneable {
	/**
	 * 用户只需要设置： pageNo 当前页号 rowCount 总记录数量 pageSize 需要改变时设置，默认是20
	 */

	private int pageNo = 1; // 页号

	private int pageSize = 13; // 页面大小

	private int rowCount = 0; // 中记录数

	private int pageCount = 1; // 总页数

	private int currentResultCount = 0; // 当前结果集数据大小，即本页数据量。

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {

		if (pageNo <= 0)
			pageNo = 1;
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize <= 0)
			pageSize = 20;
		this.pageSize = pageSize;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPageCount() {
		pageCount = (rowCount + pageSize - 1) / pageSize;

		return pageCount;
	}

	/**
	 * @return 返回第一页地址
	 */
	public int getFirst() {
		return 1;
	}

	/**
	 * @return 返回最后一页地址
	 */
	public int getLast() {
		return getPageCount();
	}

	/**
	 * @return 返回上一页地址
	 */
	public int getForward() {

		if (pageNo == 1)
			return 1;
		else
			return (pageNo - 1);

	}

	/**
	 * @return 返回下一页地址
	 */
	public int getNext() {
		if (pageNo >= getPageCount())
			return getPageCount();
		else
			return (pageNo + 1);

	}

	public int getCurrentResultCount() {
		return currentResultCount;
	}

	public void setCurrentResultCount(int currentResultCount) {
		this.currentResultCount = currentResultCount;
	}

	/**
	 * 获取当前页面的第一条位置。
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (pageNo * pageSize) - pageSize;

	}
	
	public boolean isFirstNo()
	{
		if(pageNo==1)
			return true;
		else
			return false;
	}
	
	public boolean isLastNo()
	{
		if(pageNo==getPageCount())
			return true;
		else
			return false;
	}
	
	
	public String getPageCode()
	{
		StringBuffer str=new StringBuffer();
		str.append("<table width='90%' border='0' align='left' cellpadding='0' cellspacing='0'>");
		str.append("<INPUT type='hidden' name='pageNo' id='pageNo' value='1'/>");
		str.append("<tr>");
		str.append("<td align='center'>");
		if(getFirst()==pageNo){
			str.append("<font  class='toplink6'>首页</font>&nbsp;&nbsp;");
		}else{
		str.append("<a href='#' onclick='return page("+getFirst()+")' class='toplink6'>首页</a>&nbsp;&nbsp;");
		}
		if(!isFirstNo()){
			str.append("<a href='#' onclick='return page("+getForward()+")' class='toplink6'>上一页</a> ");
		}
		if(!isLastNo()){
			str.append("<a href='#' onclick='return page("+getNext()+")' class='toplink6'>下一页</a> ");	
		}
		if(getLast()==pageNo)
		{
			str.append("<font  class='toplink6'>尾页</font>&nbsp;&nbsp;");
		}else{
		str.append("<a href='#' onclick='return page("+getLast()+")' class='toplink6'>尾页</a>&nbsp;&nbsp;");
		}
		str.append("<span class='text1'>共</span>");
		str.append("<span class='text1'><font color='#669933'>"+rowCount+"</font>");
		str.append("条信息 当前是 <font color='#669933'>"+pageNo+"</font> 页</span>");
		str.append("</td>");
		str.append("</tr>");
		str.append("</table>");
		str.append("<script language='JavaScript'>");
		str.append("function page(no)");
		str.append("{");
		str.append("document.getElementById('pageNo').value = no;");
		str.append("document.forms[0].submit();");
		str.append("return false;");
		str.append("};");
		str.append("</script>");
		if(rowCount<=pageSize)
		{
			str=new StringBuffer("");
		}
		return str.toString();
	}
	
}