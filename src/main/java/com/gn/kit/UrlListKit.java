package com.gn.kit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * ��Ҫurl����
 * @author IAskWind
 *
 */
public class UrlListKit {
	/**
	 * ���ش����ݿ���
	 */
	public static List<String> dealList = new ArrayList<>(Arrays.asList("/auth/doLogin", "/fprz", "/fpcy", "/dzfp",
			"/fpcb", "/fpcx", "/fpplkj", "/fptb", "/fptj", "/hlSkfwqApi", "/hlSkpApi", "/hlDpptApi"));

	public static List<String> noDealList = new ArrayList<>(Arrays.asList("/fprz/clienthello"));

	/**
	 * ����ʹ��
	 */
//	public static List<String> encryptDealList = new ArrayList<>(Arrays.asList(""));
	
	public static List<String> encryptDealList = new ArrayList<>(Arrays.asList("/auth/doLogin"));
}
