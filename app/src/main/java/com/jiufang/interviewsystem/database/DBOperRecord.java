package com.jiufang.interviewsystem.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.jiufang.interviewsystem.base.MyApplication;
import com.jiufang.interviewsystem.bean.RecordInfoBean;
import java.util.ArrayList;
import java.util.List;

/*留存-操作记录*/
public class DBOperRecord {

    private DBHelperRecord helper;
    private SQLiteDatabase db;
    private static DBOperRecord dbOper;

    private DBOperRecord() {
        helper = new DBHelperRecord(MyApplication.applicationContext);
    }

    public static DBOperRecord getInstance() {
        if (dbOper == null) {
            dbOper = new DBOperRecord();
        }
        return dbOper;
    }

    /*查询所有数据*/
    public List<RecordInfoBean> queryDatas() {
        List<RecordInfoBean> datas = new ArrayList<RecordInfoBean>();
        // 获取数据库的读权限
        db = helper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(DBHelperRecord.DBTABLE, null, null, null, null, null,
                    null);
            while (cursor.moveToNext()) {// 循环查询数据
                String config_id = cursor.getString(cursor
                        .getColumnIndex(DBHelperRecord.Config_id));
                String student_seq = cursor.getString(cursor
                        .getColumnIndex(DBHelperRecord.Student_seq));
                String examiner_name = cursor.getString(cursor
                        .getColumnIndex(DBHelperRecord.Examiner_name));
                String device_time = cursor.getString(cursor
                        .getColumnIndex(DBHelperRecord.Device_time));
                Float total_score = cursor.getFloat(cursor
                        .getColumnIndex(DBHelperRecord.total_score));
                String scores = cursor.getString(cursor
                        .getColumnIndex(DBHelperRecord.scores));
                String type = cursor.getString(cursor
                        .getColumnIndex(DBHelperRecord.type));

                RecordInfoBean bean = new RecordInfoBean();
                bean.setConfig_id(config_id);
                bean.setStudent_seq(student_seq);
                bean.setExaminer_name(examiner_name);
                bean.setDevice_time(device_time);
                bean.setTotal_score(total_score);
                bean.setScores(scores);
                bean.setType(type);
                datas.add(bean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return datas;
    }

    /**
     * 插入数据
     *
     * @param bean
     * @return 返回 -1 表示添加失败，返回1表示添加成功
     */
    public long insertData(RecordInfoBean bean) {
        try {
            // 获取数据库写权限
            db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelperRecord.Config_id, bean.getConfig_id());
            values.put(DBHelperRecord.Student_seq, bean.getStudent_seq());
            values.put(DBHelperRecord.Examiner_name, bean.getExaminer_name());
            values.put(DBHelperRecord.Device_time, bean.getDevice_time());
            values.put(DBHelperRecord.scores, bean.getScores());
            values.put(DBHelperRecord.total_score, bean.getTotal_score());
            values.put(DBHelperRecord.type, bean.getType());

            return db.insert(DBHelperRecord.DBTABLE, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }

        }
        return -1;
    }

    /**
     * 更新数据
     *
     * @param bean
     * @return 返回0 表示修改失败 返回1表示修改成功
     */
    public int updateData(RecordInfoBean bean) {
        try {
            db = helper.getWritableDatabase();
            // 要修改的东西
            ContentValues values = new ContentValues();
            values.put(DBHelperRecord.Config_id, bean.getConfig_id());
            values.put(DBHelperRecord.Student_seq, bean.getStudent_seq());
            values.put(DBHelperRecord.Examiner_name, bean.getExaminer_name());
            values.put(DBHelperRecord.Device_time, bean.getDevice_time());
            values.put(DBHelperRecord.scores, bean.getScores());
            values.put(DBHelperRecord.total_score, bean.getTotal_score());
            values.put(DBHelperRecord.type, bean.getType());
            // 修改的依据
            String whereClause = DBHelperStu.Student_seq + "=?";
            String[] whereArgs = new String[]{String.valueOf(bean.getStudent_seq())};
            return db.update(DBHelperStu.DBTABLE, values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return 0;
    }

}
