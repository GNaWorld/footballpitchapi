package com.gn.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
//import com.model.SysLog;

public class LogKitInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		Object[] args = inv.getArgs();
		String level = args[0] + "";
		String classStr = args[1] + "";
		String methodStr = args[2] + "";
		String lineNumber = args[3] + "";
		Object obj = args[4];
		boolean saveDb = (boolean) args[5];
		String location = classStr + "." + methodStr + "(" + lineNumber + "): ";
		String msg = "";
		if (obj != null) {
			if (obj instanceof Exception) {
				Exception e = (Exception) obj;
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw, true));
				String str = sw.toString();
				msg = str;
			} else {
				msg = obj + "";
			}
			inv.setArg(4, location + msg);
			if (saveDb) {
//				SysLog sysLog = new SysLog();
//				sysLog.setClassStr(classStr);
//				sysLog.setMethodStr(methodStr);
//				sysLog.setLineNumber(lineNumber);
//				sysLog.setCreteTime(new Date());
//				sysLog.setLogLevel(level);
//				sysLog.setMsg(msg + "");
//				sysLog.save();
			}
			inv.invoke();
		}

	}

}
