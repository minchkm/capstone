package com.project.gudasi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gudasi.db";
    private static final int DATABASE_VERSION = 2;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1. user 테이블 (기존)
        String CREATE_USER_TABLE = "CREATE TABLE user (" +
                "name TEXT NOT NULL, " +
                "email TEXT NOT NULL" +
                ")";
        db.execSQL(CREATE_USER_TABLE);

// 2. subscription 테이블
        String CREATE_SUBSCRIPTION_TABLE = "CREATE TABLE subscription (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "icon TEXT, " +
                "app_name TEXT NOT NULL, " +
                "price INTEGER NOT NULL, " +
                "renewal_date TEXT NOT NULL" +
                ")";
        db.execSQL(CREATE_SUBSCRIPTION_TABLE);

// 3. ott_rank 테이블
        String CREATE_OTT_RANK_TABLE = "CREATE TABLE ott_rank (" +
                "rank INTEGER PRIMARY KEY, " +
                "app_name TEXT NOT NULL, " +
                "user_count INTEGER NOT NULL" +
                ")";
        db.execSQL(CREATE_OTT_RANK_TABLE);

// 4. streaming_rank 테이블
        String CREATE_STREAMING_RANK_TABLE = "CREATE TABLE streaming_rank (" +
                "rank INTEGER PRIMARY KEY, " +
                "app_name TEXT NOT NULL, " +
                "user_count INTEGER NOT NULL" +
                ")";
        db.execSQL(CREATE_STREAMING_RANK_TABLE);

// 5. cloud_rank 테이블
        String CREATE_CLOUD_RANK_TABLE = "CREATE TABLE cloud_rank (" +
                "rank INTEGER PRIMARY KEY, " +
                "app_name TEXT NOT NULL, " +
                "user_count INTEGER NOT NULL" +
                ")";
        db.execSQL(CREATE_CLOUD_RANK_TABLE);

        db.execSQL("INSERT INTO subscription (icon, app_name, price, renewal_date) VALUES " +
                "('icon_netflix', 'Netflix', 15000, '2025-06-30')," +
                "('icon_youtube', 'YouTube Premium', 10900, '2025-06-15')");
        db.execSQL("INSERT INTO ott_rank (rank, app_name, user_count) VALUES " +
                "(1, 'Netflix', 2000000)," +
                "(2, 'Disney+', 1500000)," +
                "(3, 'TVING', 1000000)");
        db.execSQL("INSERT INTO streaming_rank (rank, app_name, user_count) VALUES " +
                "(1, 'Spotify', 1800000)," +
                "(2, 'Melon', 1200000)," +
                "(3, 'FLO', 900000)");
        db.execSQL("INSERT INTO cloud_rank (rank, app_name, user_count) VALUES " +
                "(1, 'Google Drive', 3000000)," +
                "(2, 'OneDrive', 2000000)," +
                "(3, 'Dropbox', 1500000)");




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 기존 테이블 삭제 후 재생성
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS subscription");
        db.execSQL("DROP TABLE IF EXISTS ott_rank");
        db.execSQL("DROP TABLE IF EXISTS streaming_rank");
        db.execSQL("DROP TABLE IF EXISTS cloud_rank");
        onCreate(db);
    }

    public void insertUser(String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        // 이미 저장된 사용자 삭제 (간단한 처리)
        db.execSQL("DELETE FROM user");

        db.execSQL("INSERT INTO user (name, email) VALUES (?, ?)",
                new Object[]{name, email});

        db.close();
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name, email FROM user", null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String email = cursor.getString(1);

            User user = new User(name, email);
            userList.add(user);
        }

        cursor.close();
        db.close();
        return userList;
    }

    public List<Subscription> getAllSubscriptions() {
        List<Subscription> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, icon, app_name, price, renewal_date FROM subscription", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String icon = cursor.getString(1);
            String appName = cursor.getString(2);
            int price = cursor.getInt(3);
            String renewalDate = cursor.getString(4);

            list.add(new Subscription(id, icon, appName, price, renewalDate));
        }

        cursor.close();
        db.close();
        return list;
    }

}
