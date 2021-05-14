package com.jiufang.interviewsystem.utils;

import android.widget.Toast;
import com.jiufang.interviewsystem.base.MyApplication;

public class ToastUtil {
    /*Toast.LENGTH_LONG（3.5秒）和Toast.LENGTH_SHORT（2秒）*/
    public static Toast toast;

    /**
     * 强大的可以连续弹的吐司
     *
     * @param text
     */
    public static void showToast(String text) {
        if (toast == null) {
            //创建吐司对象
            toast = Toast.makeText(MyApplication.instance, text, Toast.LENGTH_LONG);

            // toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            //说明吐司已经存在了，那么则只需要更改当前吐司的文字内容
            toast.setText(text);
        }
        //最后你再show
        toast.show();
    }
}