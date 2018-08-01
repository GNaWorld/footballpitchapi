package com.gn.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.common.date.DateUtils;
import com.common.ret.ReqResult;
import com.gn.kit.IAWCodecKit;
import com.gn.kit.UrlListKit;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
//import com.model.RequestRecords;

/**
 * 全局请求的拦截器
 * 
 * @author IAskWind
 *
 */
public class GlobalRequestInterceptor implements Interceptor {

	private String key = "gna"+DateUtils.getnowday();
	@Override
	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		HttpServletRequest request = inv.getController().getRequest();
		HttpSession session = inv.getController().getSession();
		String sessionsValue = "";

		Enumeration<String> sessions = session.getAttributeNames();
		while (sessions.hasMoreElements()) {
			String name = (String) sessions.nextElement();
			String value = session.getAttribute(name).toString();
			sessionsValue += name + ":" + value + ",";
		}
		String execptionStr = "";
		String actionKey = inv.getActionKey();
		String controllerKey = inv.getControllerKey();
		String decParas = "";
		Map<String, String[]> paraMap = request.getParameterMap();
		String ip = getIpAddress(request);
		Enumeration<String> headers = request.getHeaderNames();
		String headersValue = "";
		while (headers.hasMoreElements()) {
			String name = (String) headers.nextElement();
			String value = request.getHeader(name);
			headersValue += name + ":" + value + ",";
		}
		try {
			if (UrlListKit.encryptDealList.contains(actionKey)&&request.getHeader("version")!=null) {// 加密处理
				if (!paraMap.isEmpty()) {
					try {
						System.out.println("获得的加密参数：" + paraMap.toString());
						String aesData = paraMap.get("aesData")[0];
						decParas = IAWCodecKit.aesDecrypt(aesData,key);
						System.out.println("获得的解密参数：" + decParas);
						inv.getController().setAttrs(getUrlParams(decParas));
					} catch (Exception e) {
						// 解密失败 跳登录
//						setCodeExpires(session, inv);
						inv.getController().getResponse().setHeader("code", key);
						inv.getController().renderJson(ReqResult.AUTH_FAILED);
						return;
					}
				}
				inv.invoke();
			}else {
				inv.getController().getResponse().setHeader("code", key);
				inv.invoke();
			}
			
			
//			SbUser sbUser = (SbUser) SecurityUtils.getSubject().getPrincipal();
//			if (sbUser != null) {//用户登录了
//				Object code = session.getAttribute("code");
//				Object expires = session.getAttribute("expires");
//				if (UrlListKit.encryptDealList.contains(actionKey)) {// 加密处理
//					if (code != null && expires != null) {
//						long nowTime = System.currentTimeMillis();
//						if (nowTime > Long.parseLong(expires.toString())) {
//							// 返回401
//							setCodeExpires(session, inv);
//							inv.getController().renderJson(ReqResult.AUTH_FAILED);
//						} else {
//							// TODO //用code 解密数据
//							if (!paraMap.isEmpty()) {
//								try {
//									System.out.println("获得的加密参数：" + paraMap.toString());
//									String aesData = paraMap.get("aesData")[0];
//									decParas = IAWCodecKit.aesDecrypt(aesData, code.toString());
//									System.out.println("获得的解密参数：" + decParas);
//									inv.getController().setAttrs(getUrlParams(decParas));
//								} catch (Exception e) {
//									// 解密失败 跳登录
//									setCodeExpires(session, inv);
//									inv.getController().renderJson(ReqResult.AUTH_FAILED);
//									return;
//								}
//							}
//							inv.invoke();
//						}
//					} else {
//						setCodeExpires(session, inv);
//						inv.getController().renderJson(ReqResult.AUTH_FAILED);
//					}
//				} else {// 不加密处理
//					if (code == null) {
//						setCodeExpires(session, inv);
//					}
//					inv.invoke();
//				}
//			} else {//用户没登录
//				if (UrlListKit.encryptDealList.contains(actionKey)) {// 加密处理
//					// TODO //用code 解密数据
//					if (!paraMap.isEmpty()) {
//						try {
//							System.out.println("获得的加密参数：" + paraMap.toString());
//							String aesData = paraMap.get("aesData")[0];
//							decParas = IAWCodecKit.aesDecrypt(aesData, key);
//							System.out.println("获得的解密参数：" + decParas);
//							inv.getController().setAttrs(getUrlParams(decParas));
//						} catch (Exception e) {
//							// 解密失败 跳登录
//							inv.getController().getResponse().setHeader("code", key);
//							inv.getController().renderJson(ReqResult.AUTH_FAILED);
//							return;
//						}
//					}
//					inv.invoke();
//					setCodeExpires(session, inv);
//				}else {// 不加密处理
//					setCodeExpires(session, inv);
//					inv.invoke();
//				}
//			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(); // 正式去掉
			execptionStr = "拦截器捕获异常:" + e.getMessage();
		}
		if ((UrlListKit.dealList.contains(actionKey) || UrlListKit.dealList.contains(controllerKey))
				&& !UrlListKit.noDealList.contains(actionKey)) {
//			RequestRecords requestRecords = new RequestRecords();
//			requestRecords.setActionKey(actionKey);
//			requestRecords.setParaMap(decParas == "" ? paraMap.toString() : decParas);
//			requestRecords.setIp(ip);
//			requestRecords.setHeadersValue(headersValue);
//			requestRecords.setSessionsValue(sessionsValue);
//			requestRecords.setExecptionStr(execptionStr);
//			requestRecords.setRequTime(new Date());
//			requestRecords.save();
		}

	}

	public static Map<String, Object> getUrlParams(String param) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		if (StringUtils.isBlank(param)) {
			return map;
		}
		String[] params = param.split("&");
		for (int i = 0; i < params.length; i++) {
			String[] p = params[i].split("=");
			if (p.length == 2) {
				map.put(p[0], p[1]);
			}
		}
		return map;
	}

	private void setCodeExpires(HttpSession session, Invocation inv) {
		String udid = UUID.randomUUID().toString();
		session.setAttribute("code", udid);
		session.setAttribute("expires", System.currentTimeMillis() + 60 * 1000 * 6 * 60);
		inv.getController().getResponse().setHeader("code", udid);
	}

	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
