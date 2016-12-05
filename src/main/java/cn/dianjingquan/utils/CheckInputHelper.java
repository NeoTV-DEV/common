package cn.dianjingquan.utils;

import java.util.regex.Pattern;

/**
 * Created by tommy on 2016-10-31.
 * utils
 * cn.dianjingquan.utils.
 */
public class CheckInputHelper {
    public final static boolean isCheckMobile(String mobile) {
        return Pattern.matches("^((13[0-9])|(15[^4,\\D])|(199)|(18[0-9])|(17[0-1,6-8]))\\d{8}$", mobile);
    }

    public final static boolean isTestAccount(String mobile) {
        return "199".equals(mobile.substring(0, 3));
    }

    public final static boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim()))
            return s.matches("^[0-9]*$");
        else
            return false;
    }

    public final static boolean isCheckPwd(String pwd) {
        return Pattern.matches("^[A-Za-z0-9]{6,12}$", pwd);
    }

    public final static boolean isCheckNickName(String nickName) {
        return nickName.length() >= 1 && nickName.length() <= 30;
    }

    public final static boolean isCheckFullName(String fullName) {
        return fullName.length() >= 1 && fullName.length() <= 30;
    }

    public final static boolean isCheckIdName(String idName) {
        return idName.length() >= 1 && idName.length() <= 30;
    }

    public final static boolean isSpecialStrByShort(String str) {
        return str.length() >= 0 && str.length() <= 30;
    }

    public final static boolean isSpecialStrByLong(String str) {
        return str.length() > 0 && str.length() <= 1200;
    }
}
