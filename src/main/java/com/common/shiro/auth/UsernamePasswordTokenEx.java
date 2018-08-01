package com.common.shiro.auth;

import org.apache.shiro.authc.UsernamePasswordToken;


public class UsernamePasswordTokenEx extends UsernamePasswordToken 
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 808928259279421525L;

    public UsernamePasswordTokenEx(String username, String password) {
        super(username, password, false, null);
    }
    public UsernamePasswordTokenEx(String username, String password, String sbbh, String nsrsbh) {
    	 super(username, password, false, null);
    }
    
}

