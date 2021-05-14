package com.jiufang.interviewsystem.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.jiufang.interviewsystem.R;

/**
 * 评分页，结束评分确认弹窗
 */
public class PingfenConfirmDialog extends Dialog {

    public View view;
    private Context c;
    private TextView tv_ok,tv_cancel;

    /**
     * 构造确认信息弹窗
     *
     * @param context 上下文
     */
    public PingfenConfirmDialog(Context context, int theme) {
        super(context, theme);
        view = View.inflate(context, R.layout.dialog_pingfen_confirm, null);
        this.setContentView(view);
        this.c = context;

        tv_ok = (TextView) findViewById(R.id.tv_dialog_ok);
        tv_cancel = (TextView) findViewById(R.id.tv_dialog_cancel);
    }


    public void cancel() {
        if (!isShowing()) {
            return;
        }
        super.cancel();
    }

    public void show() {
        if (isShowing()) {
            return;
        }
        super.show();
    }

    public void setView(View view) {
        this.view = view;
        this.setContentView(view);
    }

    public TextView getTv_ok() {
        return tv_ok;
    }

    public void setTv_ok(TextView tv_ok) {
        this.tv_ok = tv_ok;
    }

    public TextView getTv_cancel() {
        return tv_cancel;
    }

    public void setTv_cancel(TextView tv_cancel) {
        this.tv_cancel = tv_cancel;
    }
}
