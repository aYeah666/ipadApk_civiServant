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
/*要素信息*/

public class DBHelper1 extends SQLiteOpenHelper {

    /**
     * 数据库名称
     **/
    private static final String DB_NAME = "factor_info.db";
    /**
     * 数据库版本号
     **/
    private static final int DB_VERSION = 1;
    //数据表
    public static final String DBTABLE = "factorInfo";

    //自动增长ID 用于修改，删除操作 添加时自动增长
    // public static final String ID = "_id";

    //当前二维码编号  int
    public static final String Now_qc_num = "now_qc_num";
    //要素数量 int
    public static final String Factor_num = "factor_num";
    //初始化二维码数量 int
    public static final String Initialize_qc_num = "initialize_qc_num";
    //要素最低分
    public static final String Factor_min_score = "factor_min_score";
    //	要素最高分
    public static final String Factor_max_score = "factor_max_score";
    //	面试编号
    public static final String Config_id = "config_id";
    //	二维码类型   固定：ibaoming_interview_conf
    public static final String Qc_name = "qc_name";
    //	要素名
    public static final String Factor_name = "factor_name";
    //	要素名
    public static final String Show_value = "show_value";

    /*   NAME_TABLE_CREATE---SOL*/
    private static final String SQL = "create table " + DBTABLE + " (" +
            //     ID + " integer primary key autoincrement ,"
            Factor_min_score + " text,"
            + Factor_max_score + " text,"
            + Config_id + " text,"
            + Qc_name + " text,"
            + Factor_name + " text,"
            + Now_qc_num + " integer unique,"//唯一字段
            + Initialize_qc_num + " integer,"
            + Show_value + " integer,"//这是自己定义的
            + Factor_num + " integer)";


    public DBHelper1(Context context) {
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
     * @return i
     */
    public static boolean deleteDatabase_fac(Context context) {
        return context.deleteDatabase(DB_NAME);
    }

    /*复制数据库到手机目录下 卸载后不会消失*/
    public static void copyToSdCard(Context context) {
        //默认数据库路径  /data/data/包名/databases/数据库名称
        File dbFile = new File(Environment.getDataDirectory().getAbsolutePath() + "/data/" + context.getPackageName() + "/databases/" + DB_NAME);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            //文件复制到sd卡中
            fis = new FileInputStream(dbFile);
            fos = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/copy_factor.db");
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
