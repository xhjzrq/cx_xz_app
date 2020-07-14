package com.fenglian.tools.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @version 	1.0
 * @author	������
 * ˵��������������������ҳ���ύ�����ַ�ת�����⡣
 * ���ܣ�����ҳ���ύ����request�ַ����ͣ���getUserEncoding(������ָ�����ɶ���ɶ�ȡ�����ļ���
 */
public class CharacterEncodingFilter implements Filter
{
	
    private String encoding = null;   //���뷽ʽ
    protected FilterConfig filterConfig = null;

	/**
	* @see javax.servlet.Filter#void ()
	*/
	public void destroy()
	{

	}

	/**
	* @see javax.servlet.Filter#void (javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	* @����������������ϳ�¼������servlet2.3�����ķ�������request��response�ַ�
	*/
	public void doFilter(
		ServletRequest request,
		ServletResponse response,
		FilterChain chain)
		throws ServletException, IOException
	{

		
		encoding = getUserEncoding(); //��ǰ�û��ı��뷽ʽ�����û����õ�һ��ֵ

        if (encoding == null)
        {
            encoding = request.getCharacterEncoding(); // ��������صı��뷽ʽ
        }

        if (encoding == null)
        {
            encoding = filterConfig.getInitParameter("encoding"); //Ӧ�ó���ı��뷽ʽ
        }

        if (encoding != null)
        {
            request.setCharacterEncoding(encoding); //����servlet 2.3�¼ӵķ�����ר��������request���뷽ʽ
            response.setContentType("text/html;charset=" + encoding); //����response���뷽ʽ
        }

		chain.doFilter(request, response);

	}

	/**
	* Method init.
	* @param config
	* @throws javax.servlet.ServletException
	*/
	public void init(FilterConfig config) throws ServletException
	{
		this.filterConfig = config;
		
	}
	
	
    /**
     * Get encoding from current user's configuration.
     */

    private String getUserEncoding() {
    
        return "GBK";	//��ʱ�־��GB2312
//        return "x-EUC-CN";        
        // �ɴ�session�ж�ȡ��������ļ���
    }


}
