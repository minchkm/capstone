package com.project.gudasi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


public class MainActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user", null);

        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("name");
            int emailIndex = cursor.getColumnIndex("email");

            if (nameIndex != -1 && emailIndex != -1) {
                String name = getIntent().getStringExtra("name");
                String email = getIntent().getStringExtra("email");

                TextView userName = findViewById(R.id.userName);
                userName.setText(name);

                TextView userEmail = findViewById(R.id.userEmail);
                userEmail.setText(email);

                // 예: photo URL을 Glide로 불러오고 싶다면 아래 코드 추가 가능
                // ImageView userPhoto = findViewById(R.id.userPhoto);
                // Glide.with(this).load(photo).into(userPhoto);
            } else {
                Log.e("DB_ERROR", "컬럼 인덱스를 찾을 수 없습니다.");
            }
        } else {
            Toast.makeText(this, "사용자 정보가 없습니다.", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) cursor.close();
        db.close();

        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                // 날짜 선택 시 처리할 코드
                Toast.makeText(MainActivity.this,
                        "선택한 날짜: " + date.getDate().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}