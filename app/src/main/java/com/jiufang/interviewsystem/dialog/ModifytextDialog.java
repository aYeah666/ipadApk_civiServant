package com.jiufang.interviewsystem.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiufang.interviewsystem.R;

import butterknife.BindView;

/**
 * 修改评委姓名 （点击外面可以消失）
 */
public class ModifytextDialog extends Dialog {

    public View view;
    private Context c;

    EditText etDialogModifyText;
    TextView tvDialogModOk, tvDialogModCancel;

    /**
     * 构造确认信息弹窗
     *
     * @param context 上下文
     */
    public ModifytextDialog(Context context, int theme) {
        super(context, theme);
        view = View.inflate(context, R.layout.dialog_modify_name, null);
        this.setContentView(view);
        this.c = context;
        etDialogModifyText = (EditText) findViewById(R.id.et_dialog_modify_text);
        tvDialogModOk = (TextView) findViewById(R.id.tv_dialog_mod_ok);
        tvDialogModCancel = (TextView) findViewById(R.id.tv_dialog_mod_cancel);
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


    public EditText getEtDialogModifyText() {
        return etDialogModifyText;
    }

    public void setEtDialogModifyText(EditText etDialogModifyText) {
        this.etDialogModifyText = etDialogModifyText;
    }

    public TextView getTvDialogModOk() {
        return tvDialogModOk;
    }

    public void setTvDialogModOk(TextView tvDialogModOk) {
        this.tvDialogModOk = tvDialogModOk;
    }

    public TextView getTvDialogModCancel() {
        return tvDialogModCancel;
    }

    public void setTvDialogModCancel(TextView tvDialogModCancel) {
        this.tvDialogModCancel = tvDialogModCancel;
    }
}
