package com.shinra.base.common;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author yekai
 * @Date 2018/9/30 14:23
 */
@Data
@ToString
public class User {
    private String userName;
    private String passWord;
    private String key;
    private String jobName;
    private String jobGroup;
    private String method;

    public User(String userName, String passWord,String key,String jobName,String group) {
        this.userName=userName;
        this.passWord=passWord;
        this.key=key;
        this.jobName=jobName;
        this.jobGroup=group;
    }

}
