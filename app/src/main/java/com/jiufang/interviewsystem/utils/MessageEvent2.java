package com.jiufang.interviewsystem.utils;

import com.jiufang.interviewsystem.bean.FactorBean;
import com.jiufang.interviewsystem.bean.UpdateStuBean;

import java.util.List;

public class MessageEvent2 {
    public int total;//总分数
    public String  scoreinfo;//成绩明细
    public    List<UpdateStuBean> datas;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getScoreinfo() {
        return scoreinfo;
    }

    public void setScoreinfo(String scoreinfo) {
        this.scoreinfo = scoreinfo;
    }

    public List<UpdateStuBean> getDatas() {
        return datas;
    }

    public void setDatas(List<UpdateStuBean> datas) {
        this.datas = datas;
    }
}