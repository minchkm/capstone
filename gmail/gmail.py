from google_auth_oauthlib.flow import InstalledAppFlow
from googleapiclient.discovery import build
from email import message_from_bytes
import base64, re, firebase_admin
from firebase_admin import credentials, firestore

# 1. Gmail 인증 (처음 실행하면 브라우저 열림)
def get_gmail_service():
    SCOPES = ['https://www.googleapis.com/auth/gmail.readonly']
    flow = InstalledAppFlow.from_client_secrets_file('credentials.json', SCOPES)
    creds = flow.run_local_server(port=0)
    return build('gmail', 'v1', credentials=creds)

# 2. Firebase 연결
def connect_firestore():
    cred = credentials.Certificate('firebase-adminsdk.json')
    firebase_admin.initialize_app(cred)
    return firestore.client()

# 3. 넷플릭스 결제 메일 가져오기
def get_netflix_emails(service):
    query = 'from:(billing@mailer.netflix.com) subject:(결제)'
    result = service.users().messages().list(userId='me', q=query).execute()
    return result.get('messages', [])

# 4. 메일 하나 파싱하기
def parse_netflix_message(service, msg_id):
    msg = service.users().messages().get(userId='me', id=msg_id, format='raw').execute()
    raw = base64.urlsafe_b64decode(msg['raw'].encode('ASCII'))
    email_msg = message_from_bytes(raw)
    body = email_msg.get_payload(decode=True).decode(errors='ignore')

    # 정규표현식으로 정보 추출
    service_name = "Netflix"
    date_match = re.search(r'결제일: ?([0-9년월일\s\-\.]+)', body)
    amount_match = re.search(r'금액: ?([0-9,]+)원', body)

    return {
        "service": service_name,
        "date": date_match.group(1) if date_match else "알 수 없음",
        "amount": amount_match.group(1) if amount_match else "0"
    }

# 5. Firestore에 저장
def save_to_firestore(db, user_email, parsed_data):
    doc_ref = db.collection('subscriptions').document()
    doc_ref.set({
        "user_id": user_email,
        "service": parsed_data["service"],
        "date": parsed_data["date"],
        "amount": parsed_data["amount"]
    })
    print(f"저장 완료: {parsed_data}")

# 전체 실행
if __name__ == "__main__":
    gmail = get_gmail_service()
    db = connect_firestore()
    user_email = "user@example.com"  # 예시 이메일

    messages = get_netflix_emails(gmail)
    if not messages:
        print("넷플릭스 메일이 없어요.")
    else:
        for msg in messages[:5]:  # 최근 5개만 처리
            parsed = parse_netflix_message(gmail, msg['id'])
            save_to_firestore(db, user_email, parsed)
