/*
 * �������� 2005-7-22
 * ���ߣ������
 */
package com.fenglian.tools.data;

import java.util.Hashtable;
import java.util.List;


/**
 * @author ����� �������ڣ� 2005-7-22
 * 
 * ���������ֵ���ݵ�xml��ʽ,�����浽�����У�����ѯ�ֵ��ڻ����С�
 * 
 * Ӧ�ó��ϣ��ֵ���ݵĻ�ȡ����
 * 
 * �汾��V0.1
 * 2006-02-21 :������޸�����ֵ��ѯ�����ж�
 */
public class DicBuffer
{
	private static Hashtable m_dicBuffer = new Hashtable();
	private static Hashtable  dic_Hashtable = new Hashtable();//��Ż�ȡ�ֵ�������л���
	
	public static boolean dicContainsKey(String dicId)
	{
		return m_dicBuffer.containsKey(dicId);
	}
	
	
	public static List getDicBuffer(String dicId)
	{
		return (List)m_dicBuffer.get(dicId);
	}
	
	public static void putDicBuffer(String dicId,List date)
	{
		m_dicBuffer.put(dicId,date);
	}
	
	
	public static Hashtable getNamesBuffer(String dicId)
	{
		return (Hashtable)dic_Hashtable.get(dicId);
	}
	
	public static void putNamesBuffer(String dicId,Hashtable date)
	{
		dic_Hashtable.put(dicId,date);
	}
	
	/**
	 * ���ܣ�ˢ��ȫ���ֵ����
	 */
	public static void refurbish()
	{
		m_dicBuffer.clear();
		dic_Hashtable.clear();
	}
	
	/**
	 * ���ܣ�ˢ��ָ���ֵ仺�����
	 * @param dicId ָ��ָ�����
	 */
	public static void refurbish(String dicId)
	{
		m_dicBuffer.remove(dicId+"list");
		m_dicBuffer.remove(dicId+"show");
		m_dicBuffer.remove(dicId);
		dic_Hashtable.remove(dicId);
		
	}
	
}