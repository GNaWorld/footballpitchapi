package com.common.controller;

import org.apache.shiro.SecurityUtils;

import com.common.ret.ReqResult;
import com.gn.kit.UrlListKit;
import com.jfinal.core.Controller;
import com.model.User;

public abstract class BaseController extends Controller
{
	public void renderData(Object data) {
		renderJson(ReqResult.COMMON_SUCCESS.setData(data));
	}
	
	public void renderSuccess() {
		renderJson(ReqResult.COMMON_SUCCESS);
	}

	public void renderError(String errormsg) {
		renderJson(ReqResult.COMMON_ERROR.setMsg(errormsg));
	}
	
	public User getCurrentUser() {
		return (User) SecurityUtils.getSubject().getPrincipal();
	}
	
	/**
	 * 重写父类方法，加个加密判断 加密用getattr获取参数
	 */
	public String getPara(String name) {
		String curUrl = getRequest().getRequestURI().replace(getRequest().getContextPath(), "");
		if (UrlListKit.encryptDealList.contains(curUrl) && getRequest().getHeader("version") != null) {
			return super.getAttr(name);
		} else {
			return super.getPara(name);
		}
	}

	/**
	 * 重写父类方法，加个加密判断 加密用getattr获取参数
	 */
	public String getPara(String name, String defaultVal) {
		String curUrl = getRequest().getRequestURI().replace(getRequest().getContextPath(), "");
		if (UrlListKit.encryptDealList.contains(curUrl) && getRequest().getHeader("version") != null) {
			String param = super.getAttr(name);
			if (param == null || param.equals("")) {
				param = defaultVal;
			}
			return param;
		} else {
			return super.getPara(name, defaultVal);
		}
	}
}
