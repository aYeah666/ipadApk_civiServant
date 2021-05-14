package com.jiufang.interviewsystem.base;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jiufang.interviewsystem.R;
import com.jiufang.interviewsystem.bean.UpdateStuBean;
import com.jiufang.interviewsystem.utils.AdderView;
import com.jiufang.interviewsystem.utils.MessageEvent2;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by aixy on 2019/11/7.
 * Desc:评分
 */

public class UpdateScoresListAdapter extends BaseAdapter {

    private Activity activity;
    private List<UpdateStuBean> datas;

    public UpdateScoresListAdapter(Activity activity, List<UpdateStuBean> datas) {
        this.activity = activity;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    int yaosuScore;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        final UpdateStuBean bean = datas.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.item_update, null);
            holder.bianhao = (TextView) convertView
                    .findViewById(R.id.item_tv_biaohao);
            holder.factorname = (TextView) convertView
                    .findViewById(R.id.item_tv_factorname);
            holder.seekBar = (SeekBar) convertView
                    .findViewById(R.id.item_seekbar);
            holder.adderView = (AdderView) convertView
                    .findViewById(R.id.item_adderview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }


        final int maxScore = Integer.valueOf(bean.getFactor_max_score());
        final int minScore = Integer.valueOf(bean.getFactor_min_score());

        if (bean.getNow_qc_num() == 10) {
            holder.bianhao.setText("09");
        } else {
            holder.bianhao.setText("0" + (bean.getNow_qc_num() - 1));
        }
        holder.factorname.setText("“" + datas.get(position).getFactor_name() + "”最高分" + datas.get(position).getFactor_max_score());
        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /* 拖动条进度改变的时候调用*/
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int currentProgress = seekBar.getProgress();
                setValue(position, currentProgress);
                int min = minScore;
                if (currentProgress < min) {
                    seekBar.setProgress(min);
                    yaosuScore = min;
                } else {
                    seekBar.setProgress(currentProgress);
                    yaosuScore = currentProgress;
                }
                holder.adderView.setCurrentValue(yaosuScore);
                EventBus.getDefault().post(updateScore());
            }
        });
        yaosuScore = datas.get(position).getFinishScore();
        holder.seekBar.setMax(maxScore);
        holder.seekBar.setProgress(yaosuScore);//
        EventBus.getDefault().post(updateScore());
//          /*-----------------------------------*/

        holder.adderView.setOnValueChangeListene(new AdderView.OnValueChangeListener() {
            @Override
            public void onValueChange(int changevalue, int position) {

                //          setValue(position, changevalue);
                holder.seekBar.setProgress(changevalue);//修改seekbar的进度
                yaosuScore = changevalue;
                bean.setFinishScore(yaosuScore);
                EventBus.getDefault().post(updateScore());
            }
        });
        holder.adderView.setMinValue(minScore);
        holder.adderView.setMaxValue(maxScore);
        holder.adderView.setPosition(position);
        holder.adderView.setCurrentValue((yaosuScore));
        return convertView;
    }

    private void setValue(int position, int value) {
        datas.get(position).setFinishScore(value);
    }


    public MessageEvent2 updateScore() {
        int score = 0;
        String scoreStr = "";//成绩明细
        for (int i = 0; i < datas.size(); i++) {
            score += datas.get(i).getFinishScore();
            scoreStr += datas.get(i).getFactor_name() + ":" + datas.get(i).getFinishScore() + ";";

        }
        //      Log.e("总分明细scoreinfo--",scoreStr.substring(0, scoreStr.length() - 1));
        MessageEvent2 e = new MessageEvent2();
        e.setTotal(score);
        e.setScoreinfo(scoreStr.substring(0, scoreStr.length() - 1));
        e.setDatas(datas);

        return e;

    }

    class ViewHolder {
        private TextView bianhao, factorname;
        private SeekBar seekBar;
        private AdderView adderView;
    }
}

