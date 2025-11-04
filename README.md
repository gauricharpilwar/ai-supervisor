# 🧠 AI Supervisor – Call Handling & Knowledge Management System

A Spring Boot application that simulates a customer care AI assistant.  
If the AI knows an answer, it responds automatically — otherwise, it logs a "help request" for a supervisor to resolve. The solution grows its knowledge base over time via supervised learning.

---

## ✨ Features

- 📞 Simulate customer calls via REST API
- 🤖 Auto-answer known queries using a knowledge base
- ❓ Log unknown questions as pending help requests
- 🧑‍💼 Supervisor UI to resolve or mark unanswered questions
- 📚 Moves resolved answers to a knowledge base for future use
- 🔄 Real-time UI updates (fetch + REST)
- 🎨 Fully responsive and modern UI with HTML, CSS, and JavaScript

---

## 🛠 Tech Stack

| Component   | Technology          |
|-------------|---------------------|
| Backend     | Java 21, Spring Boot|
| Database    | MySQL (via JPA/Hibernate) |
| Frontend    | HTML5, CSS3, Vanilla JavaScript |
| Build Tool  | Maven               |

---

## 📂 Project Structure

src/
└── main/
├── java/com/frontdesk/ai_supervisor/
│ ├── controller/ # REST & UI controllers
│ ├── model/ # JPA Entity models
│ ├── repository/ # JPA Repositories
│ └── service/ # Business logic (AI handler)
└── resources/
├── static/ # Frontend (UI) files
│ ├── index.html
│ ├── style.css
│ └── script.js
└── application.properties

---

## Configure Database

- Create a MySQL database named: ai_supervisor_db
- Update your DB credentials inside src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/ai_supervisor_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

---

## Run the App

mvn spring-boot:run

App starts locally at: http://localhost:8080

---

## 🖥️ Supervisor UI

🔗 Open:
http://localhost:8080/index.html

Panel Sections:

Pending → View unresolved customer requests

Resolved → See completed/resolved queries

Learned → Full knowledge base

Activity → Recent logs

---

## 🧪 API Usage & Testing

1. Trigger AI Call (simulate incoming customer call)
   GET /calls?callerName=John&question=Do%20you%20offer%20haircut%20services?

Response (if unknown):
AI: Let me check with my supervisor and get back to you.

2. Supervisor Resolves a Request

- Go to UI → Pending
- Add answer in the input box
- Click "Resolve"

✅ Answer is:

- Saved to DB
- Added to the knowledge base
- Marked as RESOLVED in request list

3. Ask the same question again

GET /calls?callerName=Maria&question=Do%20you%20offer%20haircut%20services?

Response (auto-answered from KB):
AI: Yes, we offer haircut services starting ₹300.

---

## 🎥 Demo Recording

You can create a demo video including:

- App startup
- Simulating a call (API/send button)
- Resolving via UI
- Showing learned result

---

## My demo link is here:


---

## 📄 License

This project is intended for educational/demo purposes.


---

Let me know if you’d like help:
- Uploading this to GitHub
- Including screenshots or video links in the README
- Adding badges or footer sections!

