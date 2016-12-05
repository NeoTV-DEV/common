package cn.dianjingquan.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tommy on 2016-10-31.
 * utils
 * cn.dianjingquan.utils.
 */
public class Helper {
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    public static boolean ValidationColByJson(String[] list, JsonNode json) {
        if (json == null) return true;
        for (String arg : list) {
            if (json.get(arg) == null || Arrays.binarySearch(nullValue, json.get(arg).asText()) > 0) {
                return true;
            }
        }

        return false;
    }

    public static boolean ValidationColByQuery(String[] list, String queryString) {
        for (String each : list) {
            if (queryString == null || Arrays.binarySearch(nullValue, queryString) > 0) {
                return true;
            }
        }
        return false;
    }

    public static String GetMD5(String origin) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString
                    .getBytes()));
        } catch (Exception ex) {
        }
        return resultString;
    }

    public static JsonNode Str2JO(String res) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(res);
    }

    public static String GeneratePIN() {
        return String.valueOf((int) (Math.random() * 9000) + 1000);
    }

    public static Timestamp ConvertTimestamp(long sec) {
        long unixSeconds = sec / 1000;
        Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8")); // give a timezone reference for formating (see comment at the bottom
        String dt = sdf.format(date);
        return Timestamp.valueOf(dt);
    }

    public static Timestamp ConvertTimestampByVs(long sec) {
        return ConvertTimestamp(sec * 1000);
    }

    public static String GetTimeInterval(Timestamp s1, Timestamp s2) {
        String result = "";
        long t = s1.getTime() - s2.getTime();
        int hours = (int) (t / 3600000);
        int minutes = (int) ((t / 1000 - hours * 3600) / 60);
        int second = (int) (t / 1000 - hours * 3600 - minutes * 60);
        int day = hours / 24;
        if (day > 0) {
            result = day + "天前";
        } else if (hours > 0) {
            result = hours + "小时前";
        } else if (minutes > 0) {
            result = minutes + "分钟前";
        } else if (second > 0) {
            result = second + "秒前";
        } else {
            result = "刚刚";
        }
        return result;
    }

    public static boolean CompareMinTimeInterval(Date orgin, Date target) {
        long interval = target.getTime() - orgin.getTime();
        return interval < REQ_MIN_INTERVAL_TIME;
    }

    public static boolean CompareDay(Date orgin, Date target) {
        long interval = target.getTime() - orgin.getTime();
        return (int) (interval / (24 * 60 * 60 * 1000)) < OneDay;
    }

    public static String vagueMobile(String mobile) {
        if (mobile == null || !CheckInputHelper.isCheckMobile(mobile)) return mobile;
        String s1 = mobile.substring(0, 3);
        String s2 = mobile.substring(7, 11);
        String result = s1 + "****" + s2;
        return result;
    }

    public static boolean isValidName(String text) {
        Matcher matcher = patternFileName.matcher(text);
        return matcher.matches();
    }

    public static String generateKey(long uid) {
        String key = UUID.randomUUID() + ":" + String.valueOf(uid) + ":" + String.valueOf(System.currentTimeMillis());
        String result = Base64.getEncoder().encodeToString(key.getBytes());
        return result;
    }

    public static boolean isScoreScope(int score) {
        return score < 99 || score >= 0;
    }

    public static String filterPerCent(String source) { return source.replaceAll("%", "%25"); }

    public static boolean isPowerOfTwo(int number) {
        int square = 1;
        while (number >= square) {
            if (number == square) {
                return true;
            }
            square = square * 2;
        }
        return false;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
    private static final long REQ_MIN_INTERVAL_TIME = 1000 * 60; // 1 min
    private static final int OneDay = 1;
    private static final String[] nullValue = {"null", "undefined", ""};
    private static final Pattern patternFileName = Pattern.compile(
            "# Match a valid Windows filename (unspecified file system).          \n" +
                    "^                                # Anchor to start of string.        \n" +
                    "(?!                              # Assert filename is not: CON, PRN, \n" +
                    "  (?:                            # AUX, NUL, COM1, COM2, COM3, COM4, \n" +
                    "    CON|PRN|AUX|NUL|             # COM5, COM6, COM7, COM8, COM9,     \n" +
                    "    COM[1-9]|LPT[1-9]            # LPT1, LPT2, LPT3, LPT4, LPT5,     \n" +
                    "  )                              # LPT6, LPT7, LPT8, and LPT9...     \n" +
                    "  (?:\\.[^.]*)?                  # followed by optional extension    \n" +
                    "  $                              # and end of string                 \n" +
                    ")                                # End negative lookahead assertion. \n" +
                    "[^<>:\"/\\\\|?*\\x00-\\x1F]*     # Zero or more valid filename chars.\n" +
                    "[^<>:\"/\\\\|?*\\x00-\\x1F\\ .]  # Last char is not a space or dot.  \n" +
                    "$                                # Anchor to end of string.            ",
            Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.COMMENTS);
}

