package com.controller.api;

import java.util.List;
import java.util.UnknownFormatFlagsException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.subject.Subject;

import com.common.controller.BaseController;
import com.common.date.DateUtils;
import com.common.plugins.xmlsql.sqlparse.SqlParam;
import com.common.shiro.auth.UsernamePasswordTokenEx;
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
		
		//�ж��û����Ƿ����
		//��-
		//��-
		List<User> user = User.dao.findEx("getUserByLoginName");
		if (user == null) {
			
		} else {
			
		}
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
		//api�Զ���ȡ
//		String loginName = getAttrForStr("login_name");
		//��д��getPara
		String loginName = getPara("login_name");
		String loginPwd = getPara("login_pwd");
		
//		Subject currentUser = SecurityUtils.getSubject();
//		UsernamePasswordTokenEx token = new UsernamePasswordTokenEx(loginName, loginPwd);
		
		String msg = "-1";
		try {
//			currentUser.login(token);
//			User user = getCurrentUser();
//			user.setLastLoginTime(DateUtils.now());
//			user.update();
//			select * from sb_user where sbbh = '$sbbh$' and nsrsbh = '$nsrsbh$'
			List<User> user = User.dao.findEx("getUserByLoginName");
			for (User item : user) {
				System.out.println("1111111 ----- " + item.getLoginName());
				if (item.getLoginName().equals(loginName) && item.getLoginPwd().equals(loginPwd)) {
					msg = "1";
					break;
				} else if (item.getLoginName().equals(loginName) && !item.getLoginPwd().equals(loginPwd)) {
					msg = "2";
					break;
				}
			}
			renderData(msg);
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
		renderText("AuthController�ӿڲ���");
	}
}
