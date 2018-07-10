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
		String loginName = getPara("login_name");
		String loginPwd = getPara("login_pwd");
		
		String msg = "";
		try {
			renderData("success");
			return;
		} catch (UnknownFormatFlagsException uae) {
			msg = "�û���������";
		} catch (IncorrectCredentialsException ice) {
			msg = "�û����������벻��ȷ";
		} catch (LockedAccountException lae) {
			msg = "�ʺ��Ѿ�������";
		} catch (AccountException e) {
			// TODO: handle exception
			msg = e.getMessage();
		}
		renderError(msg);
	}
	public void test() {
		renderText("AuthController�ӿ�");
	}
}
