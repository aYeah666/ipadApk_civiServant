package com.jiufang.interviewsystem.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：判断考官姓名规则
 *
 * @author ye.tian
 * @date 2020-09-23 07:45
 */
public class CheckExaminerName {

    public static void main(String[] args){
        String testStr = "不知道a";
        String testStr1 = "不知道A";
        String testStr2 = "不知道1";
        String testStr3 = "不知道0";
        String testStr4 = "不知道";
        String testStr5 = "1不知道";
        String testStr6 = "不知3道";
        System.out.println(checkRule(testStr));
        System.out.println(checkRule(testStr1));
        System.out.println(checkRule(testStr2));
        System.out.println(checkRule(testStr3));
        System.out.println(checkRule(testStr4));
        System.out.println(checkRule(testStr5));
        System.out.println(checkRule(testStr6));

    }

    /**
     * 判断字符串是否符合规则
     * PS：编码规则：最后1位可以是汉字，大写字母，数字，其他的都是汉字
     *
     * @param c 需要判断的字符串
     * @return 是符合(true), 不符合(false)
     */
    public static boolean checkRule(String s){
        boolean flag = false;
        if (isChineseChar(s.substring(0,s.length()-1)) &&
                ( isChineseChar(s.substring(s.length()-1 , s.length())) ||
                        isNumberChar(s.substring(s.length()-1 , s.length())) ||
                        isCapitalLetterChar(s.substring(s.length()-1 , s.length())) ) ){
            flag = true ;
        }
        return flag;
    }

    /**
     * 判断字符串是否是汉字
     * PS：中文汉字的编码范围：[\u4e00-\u9fa5]
     *
     * @param c 需要判断的字符
     * @return 是汉字(true), 不是汉字(false)
     */
    public static boolean isChineseChar(String s) {
        return s.matches("[\\u4e00-\\u9fa5]+");
    }

    /**
     * 判断字符串是否是数字
     * PS：数字的编码范围：[1-9]
     *
     * @param c 需要判断的字符
     * @return 是数字(true), 不是数字(false)
     */
    public static boolean isNumberChar(String s) {
        return s.matches("[1-9]");
    }

    /**
     * 判断字符串是否是大写字母
     * PS：大写字母的编码范围：[1-9]
     *
     * @param c 需要判断的字符
     * @return 是大写字母(true), 不是大写字母(false)
     */
    public static boolean isCapitalLetterChar(String s) {
        return s.matches("[A-Z]");
    }


    /**
     * 判断考生序号是数字0--999
     */
    public static boolean isStuXuhaoNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

}
