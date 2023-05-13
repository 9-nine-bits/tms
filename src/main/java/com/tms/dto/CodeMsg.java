package com.tms.dto;

import lombok.Data;

@Data
public class CodeMsg {
    private int code;
    private String msg;
    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... args) {//带自定义格式化参数的错误信息
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code,message);
    }

    /**
     * 通用异常
     */
    public static CodeMsg SUCCESS = new CodeMsg(0, "SUCESS");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常:%s");
    /**
     * 登录模块5002XX
     */
    public static final CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "密码不能为空");
    public static final CodeMsg MOBILE_EMPTY = new CodeMsg(500211, "手机号不能为空");
    public static final CodeMsg MOBILE_ERROR = new CodeMsg(500211, "手机号格式错误");
    public static final CodeMsg USER_NOT_EXITS = new CodeMsg(500211, "用户不存在");

    public static final CodeMsg PASSWORD_ERROR = new CodeMsg(500211, "密码错误");

    public static final CodeMsg SELETED_ERROR = new CodeMsg(500212, "该选题已经有小组选了");
    public static final CodeMsg NUMBER_ERROR = new CodeMsg(500213, "当前选题人数大于最大数量");
    public static final CodeMsg TOPIC_NOT_EXITS = new CodeMsg(500214, "选题不存在");

    public static final CodeMsg REPEAT_INSERT = new CodeMsg(500215, "不可重复插入");
    public static final CodeMsg REPEAT_TEAMINSERT = new CodeMsg(500215, "已经在小组里面了");
    public static final CodeMsg NOT_JOIN_GROUP = new CodeMsg(500216, "未加入小组");
    public static final CodeMsg STATUS_NOT_IN = new CodeMsg(500217, "不在时间阶段里面");
    public static final CodeMsg UESR_NOT_IN = new CodeMsg(500218, "不在小组里面");
}