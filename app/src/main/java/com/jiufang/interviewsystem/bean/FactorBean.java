package com.jiufang.interviewsystem.bean;

import java.io.Serializable;

/**
 * Created by aixy on 2019/11/5.
 * Desc:要素信息
 */

public class FactorBean implements Serializable {


    /**
     * factor_max_score : 30
     * factor_num : 5
     * config_id : hrbms20190001
     * factor_min_score : 0
     * qc_name : ibaoming_interview_conf
     * factor_name : 综合分析
     * now_qc_num : 2
     * initialize_qc_num : 4
     */

    private String config_id;//面试编号
    private String qc_name;//二维码类型
    private String factor_name;//要素名
    private String factor_min_score;//	要素最低分
    private String factor_max_score;//要素最高分
    private int now_qc_num;//当前二维码编号
    private int factor_num;//要素数量
    private int initialize_qc_num;//初始化二维码数量

    public String getConfig_id() {
        return config_id;
    }

    public void setConfig_id(String config_id) {
        this.config_id = config_id;
    }

    public String getQc_name() {
        return qc_name;
    }

    public void setQc_name(String qc_name) {
        this.qc_name = qc_name;
    }

    public String getFactor_name() {
        return factor_name;
    }

    public void setFactor_name(String factor_name) {
        this.factor_name = factor_name;
    }

    public String getFactor_min_score() {
        return factor_min_score;
    }

    public void setFactor_min_score(String factor_min_score) {
        this.factor_min_score = factor_min_score;
    }

    public String getFactor_max_score() {
        return factor_max_score;
    }

    public void setFactor_max_score(String factor_max_score) {
        this.factor_max_score = factor_max_score;
    }

    public int getNow_qc_num() {
        return now_qc_num;
    }

    public void setNow_qc_num(int now_qc_num) {
        this.now_qc_num = now_qc_num;
    }

    public int getFactor_num() {
        return factor_num;
    }

    public void setFactor_num(int factor_num) {
        this.factor_num = factor_num;
    }

    public int getInitialize_qc_num() {
        return initialize_qc_num;
    }

    public void setInitialize_qc_num(int initialize_qc_num) {
        this.initialize_qc_num = initialize_qc_num;
    }
    /*自定义的打分之后的要素分*/
    public int finishScore;

    public int getFinishScore() {
        return finishScore;
    }

    public void setFinishScore(int finishScore) {
        this.finishScore = finishScore;
    }

    private int  showValue;      // 物品当前初始显示值

    public int getShowValue() {
        return showValue;
    }

    public void setShowValue(int showValue) {
        this.showValue = showValue;
    }
}
