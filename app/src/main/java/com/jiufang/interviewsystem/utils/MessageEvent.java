package com.jiufang.interviewsystem.utils;

import com.jiufang.interviewsystem.bean.FactorBean;

import java.util.List;

public class MessageEvent {
    public int total;//总分数
    public String scoreinfo;//成绩明细
    public List<FactorBean> datas;

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

    public List<FactorBean> getDatas() {
        return datas;
    }

    public void setDatas(List<FactorBean> datas) {
        this.datas = datas;
    }
}