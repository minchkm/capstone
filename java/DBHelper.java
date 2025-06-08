package com.project.gudasi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gudasi.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // user 테이블 생성
        String CREATE_USER_TABLE = "CREATE TABLE user (" +
                "uid TEXT PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "email TEXT NOT NULL " +
                ")";
        db.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 기존 테이블 삭제 후 재생성
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    public void insertUser(String uid, String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        // 이미 저장된 사용자 삭제 (간단한 처리)
        db.execSQL("DELETE FROM user");

        db.execSQL("INSERT INTO user (uid, name, email) VALUES (?, ?, ?)",
                new Object[]{uid, name, email});

        db.close();
    }
}
