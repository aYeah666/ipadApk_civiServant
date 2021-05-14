package com.jiufang.interviewsystem.bean;

import java.io.Serializable;

/**
 * Created by aixy on 2019/10/31.
 * Desc:初始化信息
 */

public class BasicinfoBean implements Serializable {
    /**
     * area : 哈尔滨考区
     * factor_num : 5
     * config_id : hrbms20190001
     * year : 2019
     * qc_name : ibaoming_interview_conf_init
     * now_qc_num : 1
     * print_title : 2019年度全省公务员招考
     * initialize_qc_num : 4
     */
    private String config_id;//面试编号
    private String qc_name;//  二维码类型固定：ibaoming_interview_conf_init
    private String area;//考区
    private String year;//年度
    private String print_title;//  打印标题
    private int factor_num;// 要素数量
    private int now_qc_num;// 当前二维码编号
    private int initialize_qc_num;// 初始化二维码数量

    public void setArea(String area) {
        this.area = area;
    }

    public void setFactor_num(int factor_num) {
        this.factor_num = factor_num;
    }

    public void setConfig_id(String config_id) {
        this.config_id = config_id;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setQc_name(String qc_name) {
        this.qc_name = qc_name;
    }

    public void setNow_qc_num(int now_qc_num) {
        this.now_qc_num = now_qc_num;
    }

    public void setPrint_title(String print_title) {
        this.print_title = print_title;
    }

    public void setInitialize_qc_num(int initialize_qc_num) {
        this.initialize_qc_num = initialize_qc_num;
    }

    public String getArea() {
        return area;
    }

    public int getFactor_num() {
        return factor_num;
    }

    public String getConfig_id() {
        return config_id;
    }

    public String getYear() {
        return year;
    }

    public String getQc_name() {
        return qc_name;
    }

    public int getNow_qc_num() {
        return now_qc_num;
    }

    public String getPrint_title() {
        return print_title;
    }

    public int getInitialize_qc_num() {
        return initialize_qc_num;
    }
}
