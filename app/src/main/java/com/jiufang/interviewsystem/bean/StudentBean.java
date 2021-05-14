package com.jiufang.interviewsystem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aixy on 2019/11/7.
 * Desc:评委评分生成的二维码
 */

public class StudentBean implements Serializable{

    /**
     * student_seq : 1
     * config_id : hrbms20190001
     * scores : 综合分析:33; 计划组织:20;应急应变:24;言语表达:13;举止仪表:4
     * qc_name : ibaoming_interview_conf
     * total_score : 94
     * examiner_name : 张立明
     * device_time : 2019-10-18 12:49:50
     */
    private String student_seq;//考生序号
    private float total_score;//总分
    private String config_id;//面试编号
    private String examiner_name;//考官姓名
    private String device_time;//设备二维码生成时间
    private String qc_name;//二维码类型  固定：ibaoming_interview_score
    private String scores;//
    //成绩明细，每个要素成绩用“;”间隔，每个成绩明细中要素名和成绩用“:”间隔，最后一个要素结尾不加“;”

    public String getStudent_seq() {
        return student_seq;
    }

    public void setStudent_seq(String student_seq) {
        this.student_seq = student_seq;
    }

    public float getTotal_score() {
        return total_score;
    }

    public void setTotal_score(float total_score) {
        this.total_score = total_score;
    }

    public String getConfig_id() {
        return config_id;
    }

    public void setConfig_id(String config_id) {
        this.config_id = config_id;
    }

    public String getExaminer_name() {
        return examiner_name;
    }

    public void setExaminer_name(String examiner_name) {
        this.examiner_name = examiner_name;
    }

    public String getDevice_time() {
        return device_time;
    }

    public void setDevice_time(String device_time) {
        this.device_time = device_time;
    }

    public String getQc_name() {
        return qc_name;
    }

    public void setQc_name(String qc_name) {
        this.qc_name = qc_name;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "StudentBean{" +
                "student_seq='" + student_seq + '\'' +
                ", total_score=" + total_score +
                ", config_id='" + config_id + '\'' +
                ", examiner_name='" + examiner_name + '\'' +
                ", device_time='" + device_time + '\'' +
                ", qc_name='" + qc_name + '\'' +
                ", scores='" + scores + '\'' +
                '}';
    }
}
