package com.project.gudasi; // ← 앱 패키지에 맞게 수정

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // 현재 액티비티 레이아웃

        // 하단 메뉴 버튼들 참조
        ImageView btnHome = findViewById(R.id.btnHome);
        ImageView btnRanking = findViewById(R.id.btnRanking);  // 수정: 래퍼 LinearLayout에도 ID 추가
        ImageView btnAppUsage = findViewById(R.id.btnAppUsage);
        ImageView btnMyPage = findViewById(R.id.btnMyPage);

        DBHelper dbHelper = new DBHelper(this);
        List<Subscription> subscriptions = dbHelper.getAllSubscriptions();
        RecyclerView recyclerView = findViewById(R.id.subscriptionRecyclerView);
        SubscriptionAdapter adapter = new SubscriptionAdapter(this, subscriptions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user", null);

        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("name");

            if (nameIndex != -1) {
                String name = cursor.getString(nameIndex); // DB에서 직접 가져오기

                TextView userName = findViewById(R.id.userName);
                userName.setText(name);
            } else {
                Log.e("DB_ERROR", "컬럼 인덱스를 찾을 수 없습니다.");
            }
        } else {
            Toast.makeText(this, "사용자 정보가 없습니다.", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) cursor.close();
        db.close();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RankingActivity.class);
                startActivity(intent);
            }
        });

        btnAppUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UsageStatsActivity.class);
                startActivity(intent);
            }
        });

        btnMyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
