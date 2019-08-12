/**
 * @create 2019-08-12 16:22
 * @desc log message
 **/
package com.magictown.freeworld.peterpan.kongfu.consumerk.message;

import java.io.Serializable;
import java.util.Date;

public class LogMessage implements Serializable{


    private static final long serialVersionUID = 6801496394771709488L;

    private Long id;
    private Long userId;
    private String msg;
    private String logLevel;
    private String serviceType;
    private Date createTime;

    public LogMessage() {
        super();
    }

    public LogMessage(Long id, Long userId, String msg, String logLevel, String serviceType, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.msg = msg;
        this.logLevel = logLevel;
        this.serviceType = serviceType;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "LogMessage{" +
                "id=" + id +
                ", userId=" + userId +
                ", msg='" + msg + '\'' +
                ", logLevel='" + logLevel + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

