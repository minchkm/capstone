package com.project.gudasi; // ← 앱 패키지에 맞게 수정

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user", null);

        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("name");
            int emailIndex = cursor.getColumnIndex("email");

            if (nameIndex != -1) {
                String name = cursor.getString(nameIndex); // DB에서 직접 가져오기
                String email = cursor.getString(emailIndex);

                TextView profileName = findViewById(R.id.profileName);
                profileName.setText(name);

                TextView profileEmail = findViewById(R.id.profileEmail);
                profileEmail.setText(email);
            } else {
                Log.e("DB_ERROR", "컬럼 인덱스를 찾을 수 없습니다.");
            }
        } else {
            Toast.makeText(this, "사용자 정보가 없습니다.", Toast.LENGTH_SHORT).show();
        }

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

            LinearLayout showInfoLayout = findViewById(R.id.showInfoLayout);
            showInfoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            LinearLayout logoutLayout = findViewById(R.id.logoutLayout);
            logoutLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showLogoutDialog();
                }
            });
        }



    private void showLogoutDialog() {
        // dialog_logout.xml을 inflate
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_logout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog); // 스타일 적용 가능
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

        dialog.getWindow().setLayout(
                (int)(getResources().getDisplayMetrics().widthPixels * 0.9),  // 가로 화면의 90% 사용
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        // 버튼 동작 설정
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        Button btnLogout = dialogView.findViewById(R.id.btn_logout);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss(); // 다이얼로그 닫기
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 실제 로그아웃 처리 예시
                Toast.makeText(ProfileActivity.this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                // 예: 구글 로그아웃 처리 후 로그인 화면으로 이동
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

}

