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
	 * 注册
	 */
	public void doRegister() 
	{
		String loginName = getPara("login_name");
		String loginPwd = getPara("login_pwd");
		
		//判断用户名是否存在
		//是-
		//否-
		List<User> user = User.dao.findEx("getUserByLoginName");
		if (user == null) {
			
		} else {
			
		}
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
		//api自动获取
//		String loginName = getAttrForStr("login_name");
		//重写了getPara
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
		renderText("AuthController接口测试");
	}
}
