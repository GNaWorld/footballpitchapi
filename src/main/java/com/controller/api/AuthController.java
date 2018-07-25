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
	 * ע��
	 */
	public void doRegister() 
	{
		String loginName = getPara("login_name");
		String loginPwd = getPara("login_pwd");
		
//		User user = new User();
//		user.setLoginName(loginName);
//		user.setLoginPwd(loginPwd);
//		user.save();
		
		//���ͳɹ���ǰ����axios��������
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
