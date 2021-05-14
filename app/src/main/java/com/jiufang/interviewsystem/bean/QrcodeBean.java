package com.jiufang.interviewsystem.bean;

import java.io.Serializable;

/**
 * Created by aixy on 2019/11/12.
 * Desc:
 */

public class QrcodeBean implements Serializable {

    int type;//状态  0-待扫描  1-已扫描
    int po;
    int ImageId;
    String text;

    public int getPo() {
        return po;
    }

    public void setPo(int po) {
        this.po = po;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
