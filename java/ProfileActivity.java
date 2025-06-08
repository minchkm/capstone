package com.project.gudasi; // ← 앱 패키지에 맞게 수정

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // 현재 액티비티 레이아웃

        // 하단 메뉴 버튼들 참조
        ImageView btnHome = findViewById(R.id.btnHome);
        ImageView btnRanking = findViewById(R.id.btnRanking);  // 수정: 래퍼 LinearLayout에도 ID 추가
        ImageView btnAppUsage = findViewById(R.id.btnAppUsage);
        ImageView btnMyPage = findViewById(R.id.btnMyPage);
        LinearLayout logoutLayout = findViewById(R.id.logoutLayout);

        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다이얼로그에 사용할 뷰 inflate
                LayoutInflater inflater = LayoutInflater.from(ProfileActivity.this);
                View dialogView = inflater.inflate(R.layout.dialog_logout, null);

                // AlertDialog 생성
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this, R.style.CustomDialog); // 스타일 적용 가능
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                // 버튼 핸들링
                Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
                Button btnLogout = dialogView.findViewById(R.id.btn_logout);

                btnCancel.setOnClickListener(view -> dialog.dismiss());

                btnLogout.setOnClickListener(view -> {
                    // SharedPreferences 초기화
                    SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                    preferences.edit().clear().apply();

                    // 로그인 화면으로 이동
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                });

                dialog.show();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // 현재 액티비티 종료 (선택)
            }
        });

        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, RankingActivity.class);
                startActivity(intent);
                finish(); // 현재 액티비티 종료 (선택)
            }
        });

        btnAppUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UsageStatsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
