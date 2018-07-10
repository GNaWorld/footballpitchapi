package com.common.controller;

import com.common.ret.ReqResult;
import com.jfinal.core.Controller;

public class BaseController extends Controller
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
}
