package com.jiufang.interviewsystem.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/*信息*/

public class DBHelperStu extends SQLiteOpenHelper {

    /**
     * 数据库名称
     **/
    private static final String DB_NAME = "student_info.db";
    /**
     * 数据库版本号
     **/
    private static final int DB_VERSION = 1;
    //数据表
    public static final String DBTABLE = "studentInfo";

    //自动增长ID 用于修改，删除操作 添加时自动增长
  //  public static final String ID = "id";


    //	面试编号
    public static final String Config_id = "config_id";
    //考生序号
    public static final String Student_seq = "student_seq";
    //	考官姓名
    public static final String Examiner_name = "examiner_name";
    //	二维码类型   固定：ibaoming_interview_score
    public static final String Qc_name = "qc_name";
    //	设备二维码生成时间
    public static final String Device_time = "device_time";
    //总分
    public static final String total_score = "total_score";
    //成绩明细
    public static final String scores = "scores";
    /*-----------------------------*/

    /*   NAME_TABLE_CREATE---SOL*/
    private static final String SQL = "create table " + DBTABLE + " (" +
       //    ID + " integer primary key autoincrement ,"
            Student_seq + " text unique,"//考生序号设置唯一字段
            + Examiner_name + " text,"
            + Config_id + " text,"
            + Qc_name + " text,"
            + Device_time + " text,"
            /**/
            + total_score + " real," //float类型 其他都是string
            + scores + " text)";


    public DBHelperStu(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**向数据中添加表**/
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /**可以拿到当前数据库的版本信息 与之前数据库的版本信息   用来更新数据库**/
        try {
            db.execSQL("drop table if exists " + DBTABLE);
            onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除数据库
     *
     * @param context
     * @return
     */
    public  static boolean deleteDatabase_stu(Context context) {
        return context.deleteDatabase(DB_NAME);
    }
    /*复制数据库到手机目录下*/
    public static void copyToSdCard(Context context) {
        //找到文件的路径  /data/data/包名/databases/数据库名称
        File dbFile = new File(Environment.getDataDirectory().getAbsolutePath() + "/data/" + context.getPackageName() + "/databases/" + DB_NAME);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            //文件复制到sd卡中
            fis = new FileInputStream(dbFile);
            fos = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/copy_student.db");
            int len = 0;
            byte[] buffer = new byte[2048];
            while (-1 != (len = fis.read(buffer))) {
                fos.write(buffer, 0, len);
            }
            fos.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭数据流
            try {
                if (fos != null) fos.close();
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
