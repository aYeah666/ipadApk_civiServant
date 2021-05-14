package com.jiufang.interviewsystem.base;

import android.app.Activity;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aixy on 2019/11/7.
 * Desc:
 */

import com.jiufang.interviewsystem.R;
import com.jiufang.interviewsystem.bean.StudentBean;


//角色库
public class HistoryListAdapter extends BaseAdapter {

    private Activity activity;
    private List<StudentBean> datas;
    private Handler handler;

    public HistoryListAdapter(Activity activity, List<StudentBean> datas, Handler handler) {
        this.activity = activity;
        this.datas = datas;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.item_history, null);
            holder.name = (TextView) convertView
                    .findViewById(R.id.item_tv_name);
            holder.xuhao = (TextView) convertView
                    .findViewById(R.id.item_tv_xuhao);
            holder.time = (TextView) convertView
                    .findViewById(R.id.item_tv_time);
            holder.total_score = (TextView) convertView
                    .findViewById(R.id.item_tv_totalscore);
            holder.configid = (TextView) convertView
                    .findViewById(R.id.item_tv_configid);
            holder.lookinfo = (TextView) convertView
                    .findViewById(R.id.item_tv_lookinfo);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        final StudentBean bean = datas.get(position);

        holder.name.setText(bean.getExaminer_name());
        holder.xuhao.setText(bean.getStudent_seq());
        holder.time.setText(bean.getDevice_time());
        holder.total_score.setText(bean.getTotal_score() + "");
        holder.configid.setText(bean.getConfig_id());
        Log.e("kao",bean.getScores());
        holder.lookinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Message msg = new Message();
                msg.what = 1;
                msg.obj = bean;
                handler.sendMessage(msg);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView name, xuhao, time, total_score, configid, lookinfo;

    }
}

