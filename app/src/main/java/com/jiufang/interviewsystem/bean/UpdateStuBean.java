package com.jiufang.interviewsystem.bean;

import java.io.Serializable;

/**
 * Created by aixy on 2019/11/7.
 * Desc:需要修改的student信息
 */

public class UpdateStuBean implements Serializable{

    private String config_id;//面试编号
    private String qc_name;//二维码类型  固定：score
    private String device_time;//设备二维码生成时间
    private String student_seq;//考生序号
    private String examiner_name;//考官姓名
    private float total_score;//总分

    private String factor_name;//要素名
    private String factor_min_score;//	要素最低分
    private String factor_max_score;//要素最高分
    private int current_score;//要素当前得分
    private int now_qc_num;//当前二维码编号
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

    public String getDevice_time() {
        return device_time;
    }

    public void setDevice_time(String device_time) {
        this.device_time = device_time;
    }

    public String getStudent_seq() {
        return student_seq;
    }

    public void setStudent_seq(String student_seq) {
        this.student_seq = student_seq;
    }

    public String getExaminer_name() {
        return examiner_name;
    }

    public void setExaminer_name(String examiner_name) {
        this.examiner_name = examiner_name;
    }

    public float getTotal_score() {
        return total_score;
    }

    public void setTotal_score(float total_score) {
        this.total_score = total_score;
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
    /*自定义的打分之后的要素分*/
    public int finishScore;

    public int getFinishScore() {
        return finishScore;
    }

    public void setFinishScore(int finishScore) {
        this.finishScore = finishScore;
    }

}
