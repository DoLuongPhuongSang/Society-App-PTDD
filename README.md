# 🌐 Society App - PTDD

> Đồ án môn học Phát triển Ứng dụng Di động (PTDD).  
> Ứng dụng mạng xã hội đa chức năng xây dựng theo mô hình full-stack: backend REST API & WebSocket kết hợp cùng ứng dụng di động Android.

---

## 📌 1. Giới thiệu

**Society App PTDD** tập trung vào việc xây dựng một mạng xã hội hiện đại với đầy đủ các tính năng tương tác giữa người dùng:

- **Xác thực & Người dùng:** Đăng nhập, đăng ký, xác thực OTP qua Email, OAuth (Google / Facebook login), quản lý profile cá nhân.
- **Bài viết & Tương tác:** Đăng bài viết, bình luận, thả cảm xúc (reaction), lưu bài viết.
- **Story ngắn:** Đăng story dạng ảnh/video ngắn hiển thị theo feed.
- **Kết bạn & Nhóm:** Gửi lời mời kết bạn, tạo nhóm cộng đồng, tham gia nhóm, đăng bài trong nhóm.
- **Trò chuyện trực tuyến (Chat Realtime):** Chat 1-1, hiển thị trạng thái online/typing, nickname.
- **Media & Documents:** Upload hình ảnh, video và tài liệu trực tiếp lên Cloudinary.
- **Tính năng mở rộng:** Notification, Quiz, Live Stream, Library nội bộ.

---

## 🛠 2. Công nghệ sử dụng

### 2.1 Backend
- Node.js
- Express.js
- TypeScript
- MongoDB + Mongoose
- Socket.IO / WebSocket
- JWT & Refresh Token
- Cloudinary API
- Swagger UI
- Nodemailer, Express Validator, Dotenv

### 2.2 Frontend (Android)
- Android Studio
- Java
- Gradle
- Retrofit
- OkHttp
- SharedPreferences
- RecyclerView
- ViewModel / Android UI flow
- Socket client cho realtime chat

### 2.3 Dịch vụ bên ngoài
- MongoDB Atlas
- Cloudinary
- Google OAuth / Facebook OAuth
- Email SMTP
---

## 🏗 3. Kiến trúc hệ thống

Dự án được xây dựng theo kiến trúc Client-Server rõ ràng:

```text
Android / Web App
   │
   ├── REST API (HTTP Request)
   │     ├── Auth & User
   │     ├── Post & Feed
   │     ├── Story
   │     ├── Friend & Group
   │     ├── Chat
   │     ├── Media
   │     └── Notification
   │
   ├── Socket.IO / WebSocket
   │     └── Realtime Chat & Notification
   │
   └── Cloud Database & Storage
         ├── MongoDB / Database (Dữ liệu người dùng, bài viết, tin nhắn)
         └── Cloudinary (Hình ảnh, Video, File media)


---

## 📂 4. Cấu trúc thư mục

```text
Society-App-PTDD/
├── backend/
│   ├── src/
│   │   ├── app.ts
│   │   ├── server.ts
│   │   ├── config/
│   │   ├── controllers/
│   │   ├── middlewares/
│   │   ├── models/
│   │   ├── routes/
│   │   ├── socket/
│   │   ├── swagger/
│   │   └── utils/
│   ├── .env.example
│   ├── .gitignore
│   ├── package.json
│   └── tsconfig.json
│
├── frontend/
│   ├── app/
│   ├── gradlew
│   ├── build.gradle.kts
│   └── settings.gradle.kts
│
├── .gitignore
├── README.md
└── ...
```

---

## 🚀 5. Hướng dẫn khởi chạy dự án

### 5.1 Backend

```bash
cd backend
npm install
cp .env.example .env
npm run dev
```

Mặc định server chạy tại:
- REST API: http://localhost:3000
- WebSocket: ws://localhost:3000

### 5.2 Frontend Android

```bash
cd frontend
# mở project trong Android Studio
# đồng bộ Gradle
# chạy trên emulator hoặc thiết bị thật
```

> Nếu chạy trên môi trường khác nhau, cần chỉnh `BASE_URL` và `SOCKET_URL` trong project Android cho phù hợp với địa chỉ máy chủ local hoặc server thực tế.

---

## 🔑 6. Biến môi trường (.env)

Mẫu cấu hình tham khảo tại:
- `backend/.env.example`

Các biến quan trọng bao gồm:

- `MONGO_URI_ATLAS`
- `JWT_SECRET`
- `JWT_REFRESH_SECRET`
- `CLOUDINARY_NAME`
- `CLOUDINARY_API_KEY`
- `CLOUDINARY_API_SECRET`
- `GOOGLE_CLIENT_ID`
- `GOOGLE_CLIENT_SECRET`
- `FACEBOOK_APP_ID`
- `FACEBOOK_APP_SECRET`
- `EMAIL_USER`
- `EMAIL_PASS`
- `OPENAI_API_KEY` (nếu có module AI/Quiz tích hợp)

---

## ✅ 7. Tính năng chính hiện có trong repo

- Đăng ký / đăng nhập / logout
- Quản lý thông tin người dùng & profile
- Feed bài viết, tương tác reaction, comment
- Story ảnh/video ngắn
- Kết bạn, lời mời, profile người dùng liên quan
- Nhóm cộng đồng / group management
- Chat realtime 1-1 qua Socket.IO
- Upload media lên Cloudinary
- Quiz, Notification, Live
- Library / tài liệu nội bộ

---

## 📘 8. Kết luận

Society App PTDD là một dự án full-stack có phạm vi khá rộng, từ xác thực người dùng, bài viết, story, kết bạn, group, media, realtime chat cho đến quiz và live. Đây là project phù hợp để học tập, phát triển kỹ năng backend/frontend tích hợp, và mô phỏng quy trình xây dựng một ứng dụng mạng xã hội thực tế.

---
