🧩 Step 1. Gmail API 준비 (Python)
✅ 1-1. Google Cloud Console 설정
Google Cloud Console 접속

새 프로젝트 생성

왼쪽 메뉴 → API 및 서비스 > 라이브러리 → "Gmail API" 검색 후 활성화

왼쪽 메뉴 → 사용자 인증 정보 > 사용자 인증 정보 만들기 > OAuth 클라이언트 ID

애플리케이션 유형: 데스크톱 앱

이름: 아무거나 (예: "Gmail Parser")

만들어진 후 나오는 credentials.json 파일을 저장해 둠

✅ 이 파일이 있어야 Python에서 Gmail 접근 가능함

🧩 Step 2. Firebase Firestore 설정
Firebase 콘솔 접속

새 프로젝트 생성

왼쪽 메뉴에서 Cloud Firestore > 시작하기

테스트 모드로 시작 (보안은 나중에 설정)

Firebase 설정 → 서비스 계정 탭 →
새 비공개 키 생성 → firebase-adminsdk.json 다운로드

✅ 이 파일이 있어야 Python에서 Firebase DB에 연결 가능함


🧩 Step 3. Python으로 메일 가져오고 Firestore에 저장하기
✅ 3-1. 필요한 라이브러리 설치
터미널에서 아래 명령어 실행


pip install --upgrade google-api-python-client google-auth-httplib2 google-auth-oauthlib firebase-admin
