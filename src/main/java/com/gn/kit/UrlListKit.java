package com.gn.kit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * 需要url集合
 * @author IAskWind
 *
 */
public class UrlListKit {
	/**
	 * 拦截存数据库用
	 */
	public static List<String> dealList = new ArrayList<>(Arrays.asList("/auth/doLogin", "/fprz", "/fpcy", "/dzfp",
			"/fpcb", "/fpcx", "/fpplkj", "/fptb", "/fptj", "/hlSkfwqApi", "/hlSkpApi", "/hlDpptApi"));

	public static List<String> noDealList = new ArrayList<>(Arrays.asList("/fprz/clienthello"));

	/**
	 * 加密使用
	 */
//	public static List<String> encryptDealList = new ArrayList<>(Arrays.asList(""));
	
	public static List<String> encryptDealList = new ArrayList<>(Arrays.asList("/auth/doLogin"));
}
