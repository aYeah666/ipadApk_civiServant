package com.jiufang.interviewsystem.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiufang.interviewsystem.R;

/**
 * 修改评分  只剩“关闭窗口”按钮
 */
public class UpdateScoreDialog extends Dialog {

    public View view;
    private Context c;
    TextView finishName, finishXuhao, finishScore;
    Button btnResultClose;
    ImageView mBarcodeImageView;

    /**
     * 构造确认信息弹窗
     *
     * @param context 上下文
     */
    public UpdateScoreDialog(Context context, int theme) {
        super(context, theme);
        view = View.inflate(context, R.layout.dialog_update_result, null);
        this.setContentView(view);
        this.c = context;
        mBarcodeImageView = (ImageView) findViewById(R.id.barcode_image);
        finishName = (TextView) findViewById(R.id.tv_dia_finish_name);
        finishXuhao = (TextView) findViewById(R.id.tv_dia_finish_xuhao);
        finishScore = (TextView) findViewById(R.id.tv_dia_finish_score);
        btnResultClose = (Button) findViewById(R.id.btn_dia_close);


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

    public ImageView getmBarcodeImageView() {
        return mBarcodeImageView;
    }

    public void setmBarcodeImageView(ImageView mBarcodeImageView) {
        this.mBarcodeImageView = mBarcodeImageView;
    }

    public TextView getFinishName() {
        return finishName;
    }

    public void setFinishName(TextView finishName) {
        this.finishName = finishName;
    }

    public TextView getFinishXuhao() {
        return finishXuhao;
    }

    public void setFinishXuhao(TextView finishXuhao) {
        this.finishXuhao = finishXuhao;
    }

    public TextView getFinishScore() {
        return finishScore;
    }

    public void setFinishScore(TextView finishScore) {
        this.finishScore = finishScore;
    }

    public Button getBtnResultClose() {
        return btnResultClose;
    }

    public void setBtnResultClose(Button btnResultClose) {
        this.btnResultClose = btnResultClose;
    }
}
