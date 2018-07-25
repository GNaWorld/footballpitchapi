package com.controller.api;

import java.util.UnknownFormatFlagsException;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;

import com.alibaba.fastjson.JSONObject;
import com.common.controller.BaseController;
import com.common.plugins.xmlsql.sqlparse.SqlParam;
import com.common.ret.ReqResult;
import com.jfinal.ext.route.ControllerBind;
import com.model.User;

@ControllerBind(controllerKey = "/auth", viewPath = "/auth")
public class AuthController extends BaseController
{
	/**
	 * 注册
	 */
	public void doRegister() 
	{
		String loginName = getPara("login_name");
		String loginPwd = getPara("login_pwd");
		
//		User user = new User();
//		user.setLoginName(loginName);
//		user.setLoginPwd(loginPwd);
//		user.save();
		
		//发送成功，前端用axios接受数据
//		JSONObject json = new JSONObject();
//		json.put("gna", "success");
//		renderJson(json);
		
//		renderData("ssssss");
//		renderJson("141");
//		JSONObject json = new JSONObject();
//		json.put("gna", "success");
////		renderJson(json);
//		renderData(json);
//		renderData(new TestReq(1, "55", null));
		renderSuccess();
//		renderJson(ReqResult.COMMON_SUCCESS.setData("141"));
		
//		renderText("5555");
	}
	//http://localhost:8080/auth/doLogin?login_name=gna&login_pwd=111
	public void doLogin() {
//		renderNull();
		
		String loginName = getPara("login_name");
		String loginPwd = getPara("login_pwd");
		
		User sbuser = User.dao.findFirstEx("getUserByLoginName", SqlParam.Init("login_name", loginName));
		
//		Blog blog = new Blog();
//		blog.set("name", loginName);
//		blog.set("desc", loginPwd);
//		blog.save();
		
		String msg = "";
		try {
//			renderData("success");
			renderText("5555");
			return;
		} catch (UnknownFormatFlagsException uae) {
			msg = "用户名不存在";
		} catch (IncorrectCredentialsException ice) {
			msg = "用户名或者密码不正确";
		} catch (LockedAccountException lae) {
			msg = "帐号已经被锁定";
		} catch (AccountException e) {
			// TODO: handle exception
			msg = e.getMessage();
		}
		renderError(msg);
	}
	public void test() {
		renderText("AuthController接口");
	}
}
