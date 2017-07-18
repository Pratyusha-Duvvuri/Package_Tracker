package com.codepath.packagetwitter;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by pratyusha98 on 7/3/17.
 */


@ParseClassName("Message")
public class Message extends ParseObject {
    public static final String USER_ID_KEY = "userId";
    public static final String BODY_KEY = "body";
    public static final String USER_NAME_KEY = "userName";
    public static final String PICTURE_KEY = "picture";
    public static final String TRANSACTION_ID_KEY = "transactionid";

    public String getUserId() {
        return getString(USER_ID_KEY);
    }

    public String getBody() {
        return getString(BODY_KEY);
    }
    public String getUserName() {
        return getString(USER_NAME_KEY);
    }
    public ParseFile getPicture() {
        return getParseFile(PICTURE_KEY);
    }

    public String getTransactionId() {return getString(TRANSACTION_ID_KEY);}


    public void setUserId(String userId) {
        put(USER_ID_KEY, userId);
    }

    public void setBody(String body) {
        put(BODY_KEY, body);
    }

    public void setUserName(String userName) {
        put(USER_NAME_KEY, userName);
    }
    public void setPicture(ParseFile file) {
        put(PICTURE_KEY, file);
    }
    public void setTransactionId(String transactionId) {
        put(TRANSACTION_ID_KEY, transactionId);
    }


}