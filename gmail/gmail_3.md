🧩 Step 4. Android Studio에서 Firestore 읽기
✅ 4-1. Gradle 설정 (build.gradle)

dependencies {
    implementation 'com.google.firebase:firebase-firestore:24.7.0'
    implementation 'com.google.firebase:firebase-auth:22.1.0' // 필요시
}


✅ 4-2. Firestore 데이터 읽기 (Java 예시)
FirebaseFirestore db = FirebaseFirestore.getInstance();
String userEmail = "user@example.com"; // 동일해야 함

db.collection("subscriptions")
  .whereEqualTo("user_id", userEmail)
  .get()
  .addOnSuccessListener(queryDocumentSnapshots -> {
      for (DocumentSnapshot doc : queryDocumentSnapshots) {
          String service = doc.getString("service");
          String date = doc.getString("date");
          String amount = doc.getString("amount");

          // 화면에 출력하기 (예: TextView로 보여주기)
          Log.d("Firestore", service + " | " + date + " | " + amount + "원");
      }
  });
