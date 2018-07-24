package com.controller.api;

import java.util.UnknownFormatFlagsException;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;

import com.common.controller.BaseController;
import com.jfinal.ext.route.ControllerBind;

@ControllerBind(controllerKey = "/auth", viewPath = "/auth")
public class AuthController extends BaseController
{
	//http://localhost:8080/auth/doLogin?login_name=gna&login_pwd=111
	public void doLogin() {
//		renderNull();
		String loginName = getPara("login_name");
		String loginPwd = getPara("login_pwd");
		
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
