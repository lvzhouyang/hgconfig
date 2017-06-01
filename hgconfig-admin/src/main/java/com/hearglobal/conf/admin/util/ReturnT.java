package com.hearglobal.conf.admin.util;

/**
 * common return
 *
 * @param <T> the type parameter
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.01
 */
public class ReturnT<T> {
    /**
     * The constant SUCCESS.
     */
    public static final ReturnT<String> SUCCESS = new ReturnT<String>(null);
    /**
     * The constant FAIL.
     */
    public static final ReturnT<String> FAIL = new ReturnT<String>(500, null);

    /**
     * The Code.
     */
    private int code;
    /**
     * The Msg.
     */
    private String msg;
    /**
     * The Content.
     */
    private T content;

    /**
     * Instantiates a new Return t.
     *
     * @param code the code
     * @param msg  the msg
     */
    public ReturnT(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Instantiates a new Return t.
     *
     * @param content the content
     */
    public ReturnT(T content) {
        this.code = 200;
        this.content = content;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Gets msg.
     *
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets msg.
     *
     * @param msg the msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    public T getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content the content
     */
    public void setContent(T content) {
        this.content = content;
    }

    /**
     * To string string.
     *
     * @return the string
     * @since 2017.04.01
     */
    @Override
    public String toString() {
        return "ReturnT [code=" + code + ", msg=" + msg + ", content=" + content + "]";
    }

}
