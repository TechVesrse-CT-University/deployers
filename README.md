# 📚 EduVoice: Multilingual Video Learning Platform

EduVoice is an intelligent web platform designed to bridge language barriers in education. It allows teachers to upload educational videos and automatically **translates**, **transcribes**, and **dubs** them into multiple regional Indian languages. Students can then access these translated videos in their chosen language with synced audio and transcripts.

---

## 💡 Key Features

### 👨‍🏫 Teachers can:
- Upload lecture videos  
- Select a target language for translation and dubbing

### 👩‍🎓 Students can:
- View and stream educational videos  
- Choose a language and view the translated transcript  
- Listen to dubbed audio and download translated videos  

---

## 🌐 Supported Languages
- Bengali (`bn`)  
- Malayalam (`ml`)  
- Kannada (`kn`)  
- Tamil (`ta`)  
- Telugu (`te`)  
- Gujarati (`gu`)  

---

## 🧩 Tech Stack

| Frontend                        | Backend               | Machine Learning                                 | Other Tools                            |
|--------------------------------|------------------------|--------------------------------------------------|-----------------------------------------|
| React (TypeScript, Tailwind)   | Spring Boot (Java)     | OpenAI Whisper (Transcription), gTTS (Text-to-Speech) | FFmpeg (Video/Audio merging), Postman (API testing) |

---

## 🖼 Live Preview
_Add screenshots or a demo video of the Teacher and Student dashboards here._

---

## 📁 Folder Structure

```
EduVoice/
├── backend/                  # Spring Boot backend
│   ├── controller/           # API endpoints
│   ├── service/              # Logic for transcription, translation, merging
│   └── model/                # Data models
├── frontend/                 # React frontend
│   ├── components/           # Reusable UI components
│   └── pages/                # TeacherDashboard.tsx & StudentDashboard.tsx
├── python/                   # Python scripts for Whisper and gTTS
└── README.md
```

---

## 📜 Backend API Overview

| Endpoint                              | Method | Description                                      |
|---------------------------------------|--------|--------------------------------------------------|
| `/api/video/upload`                   | POST   | Uploads a video and triggers transcription & translation |
| `/api/video/videos`                   | GET    | Retrieves list of uploaded videos               |
| `/api/video/translate`               | POST   | Translates transcript into selected language     |
| `/api/video/stream/{filename}`       | GET    | Streams the original video                      |
| `/api/video/stream/audio/{audioFile}`| GET    | Streams the translated audio                    |
| `/api/video/download/{mergedFileName}`| GET    | Downloads the translated merged video           |

---

## ⚙ How to Run

### 1. 🖥 Backend (Spring Boot)

```bash
cd backend
./mvnw spring-boot:run
```

### 2. 🌐 Frontend (React)

> 💡 _For Windows users:_

```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

Then:

```bash
cd frontend
npm install
npm run dev
```

### 3. 🧠 Python Services

Make sure your Python environment is set up:

```bash
cd python
python generate-audio.py
```

---

## 🧠 Future Improvements

- Add login and role-based dashboards (teacher vs student)  
- Add subtitle support with translated text  
- Store user language preferences  
- Show upload and translation progress indicators  

---

## 📄 License

This project is for educational and demonstration purposes only.
