package com.common.ret;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class ReqResult
{

    public static ReqResult POST_PARAM_ERROR = new ReqResult(400, "错误的参数", null);

    public static ReqResult EXCEPTION_ERROR = new ReqResult(500, "系统异常", null);

    public static ReqResult COMMON_SUCCESS = new ReqResult(200, "成功", null);

    public static ReqResult COMMON_ERROR = new ReqResult(400, "失败", null);


    public static ReqResult   AUTH_FAILED = new ReqResult(401, "会话验证失败", null);
    
    private String message;

    private Integer code;

    private Object data;
    
    private ReqResult(Integer incode, String msg, Object data) {
        // this.invalidsession = false;
        this.code = incode;
        this.message = msg;
        this.data = data;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public ReqResult setCode(Integer code) {
        this.code = code;
        return this;
    }
    
    public Object getData() {
        return data;
    }
    
    public ReqResult setData(Object data) {
        return new ReqResult(this.code, this.message, data);
    }
    
    public String getMessage() {
        return message;
    }
    
    public String setMsg(String msg) {
        return new ReqResult(this.code, msg, data).toString();
    }
    
    @Override
    public String toString() {
        String ret = JSONObject.toJSONString(this,
                SerializerFeature.WriteNullStringAsEmpty);
        System.out.println(ret);
        return ret;
    }
}
