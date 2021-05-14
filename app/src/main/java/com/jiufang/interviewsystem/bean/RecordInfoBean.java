package com.jiufang.interviewsystem.bean;

import java.io.Serializable;

/**
 * Created by aixy on 2020/8/20.
 * Desc:需要留存的操作记录 信息
 */

public class RecordInfoBean implements Serializable {

    public String Config_id;  //	面试编号
    public String Student_seq;    //考生序号
    public String Examiner_name;    //	考官姓名
    public String Device_time;    //	二维码生成时间
    private float total_score;//总分
    public String scores;    //成绩明细
    public String type;    //"评分"还是“修改”

    public String getConfig_id() {
        return Config_id;
    }

    public void setConfig_id(String config_id) {
        Config_id = config_id;
    }

    public String getStudent_seq() {
        return Student_seq;
    }

    public void setStudent_seq(String student_seq) {
        Student_seq = student_seq;
    }

    public String getExaminer_name() {
        return Examiner_name;
    }

    public void setExaminer_name(String examiner_name) {
        Examiner_name = examiner_name;
    }

    public String getDevice_time() {
        return Device_time;
    }

    public void setDevice_time(String device_time) {
        Device_time = device_time;
    }

    public float getTotal_score() {
        return total_score;
    }

    public void setTotal_score(float total_score) {
        this.total_score = total_score;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
