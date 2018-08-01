package com.controller.api;

import com.common.controller.BaseController;
import com.jfinal.ext.route.ControllerBind;

@ControllerBind(controllerKey = "/interface", viewPath = "/interface")
public class InterfaceController extends BaseController
{
	public void test() {
		renderText("InterfaceController½Ó¿Ú²âÊÔ");
	}
}
