package com.common.ret;

import java.io.Serializable;

public class ReqResult implements Serializable
{
	private static final long serialVersionUID = -2974719921585501934L;

    public static ReqResult POST_PARAM_ERROR = new ReqResult(400, "����Ĳ���", null);

    public static ReqResult EXCEPTION_ERROR = new ReqResult(500, "ϵͳ�쳣", null);

    public static ReqResult COMMON_SUCCESS = new ReqResult(200, "�ɹ�", null);

    public static ReqResult COMMON_ERROR = new ReqResult(400, "ʧ��", null);


    public static ReqResult   AUTH_FAILED = new ReqResult(401, "�Ự��֤ʧ��", null);
    
    private String message;

    private Integer code;

    private Object data;
    
    private ReqResult(Integer incode, String msg, Object data) {
        // this.invalidsession = false;
        this.code = incode;
        this.message = msg;
        this.data = data;
    }
    
    public ReqResult setData(Object data) {
        return new ReqResult(this.code, this.message, data);
    }
    
    public String setMsg(String msg) {
        return new ReqResult(this.code, msg, data).toString();
    }
}
