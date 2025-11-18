# 🚀 Spring Boot WebSocket Chatroom with Redis Pub/Sub

A real-time chat application built with **Spring Boot**, **WebSockets (STOMP)**, and **Redis Pub/Sub** to enable **stateless**, **horizontally scalable**, multi-instance real-time communication.

This project demonstrates:

* Live chat using WebSockets
* Redis-based message broadcasting
* Running multiple Spring Boot instances
* Stateless architecture suitable for clustering

---

## 📌 Features

### 🔹 Real-Time WebSocket Messaging

Uses **Spring WebSocket + STOMP** to communicate instantly between clients and server.

### 🔹 Redis Pub/Sub for Horizontal Scaling

Redis distributes messages between multiple application instances:

* Instance A receives a WebSocket message
* Publishes to Redis
* Redis broadcasts to **all** instances
* Every instance pushes message to its connected WebSocket clients

### 🔹 Simple Chat UI

The frontend includes:

* Username prompt
* Scrollable chat area
* Live updates
* Automatic message rendering

### 🔹 Multi-Instance Support

Run 2+ application instances on different ports (8080, 8081, …) and they all stay in sync.

---

# 🛠️ Tech Stack

| Layer                 | Technology                 |
| --------------------- | -------------------------- |
| Backend               | Spring Boot                |
| Realtime Messaging    | WebSocket + STOMP          |
| Distributed Messaging | Redis Pub/Sub              |
| Serialization         | Jackson JSON               |
| Frontend              | HTML, JS, SockJS, STOMP.js |
| Build Tool            | Maven / Gradle             |

---

# 📁 Project Structure

```
src/main/java/com/shumisoft/chatroom/
│
├── config/
│   ├── WebSocketConfig.java
│   └── RedisConfig.java
│
├── controller/
│   └── ChatController.java
│
├── model/ or dto/
│   └── ChatMessageDTO.java
│
├── redis/
│   ├── RedisMessagePublisher.java
│   └── RedisMessageSubscriber.java
│
└── ChatApplication.java
```

Frontend:

```
src/main/resources/static/
└── index.html
```

---

# ⚙️ How It Works (Architecture)

### 1️⃣ Client connects via WebSocket

The browser initializes a STOMP connection:

```
/ws-chat
```

### 2️⃣ Messages from client → server

STOMP sends messages to:

```
/app/sendMessage
```

### 3️⃣ Controller publishes to Redis

```java
redisTemplate.convertAndSend("chatroom", message);
```

### 4️⃣ Redis broadcasts to all instances

### 5️⃣ Each instance forwards message to WebSocket clients

Using:

```
/topic/public
```

Result:
**All connected clients see the same messages instantly**, no matter which backend instance they connect to.

---

# 🚀 Running the App

## 1. Start Redis

### Option A: Docker

```bash
docker run -p 6379:6379 redis
```

### Option B: Local installation

Install Redis for your OS.

---

## 2. Run one instance

```bash
mvn spring-boot:run
```

(or Gradle)

---

## 3. Run a second instance on another port

### Maven:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

### Gradle:

```bash
./gradlew bootRun --args='--server.port=8081'
```

### JAR:

```
java -jar target/chatroom.jar --server.port=8081
```

---

# 🖥️ Test the Chat

1. Open:

   * [http://localhost:8080](http://localhost:8080)
   * [http://localhost:8081](http://localhost:8081)
2. Enter different usernames
3. Send messages

You will see:

* When you send a message from port 8080,
  it appears **instantly** on the page running on port 8081
* And vice-versa

This confirms Redis Pub/Sub is working.

---

# 🔧 Key Configurations

### WebSocketConfig

Enables STOMP and WebSocket endpoints.

### RedisConfig

Configures:

* JSON serialization
* Subscriber
* RedisMessageListenerContainer

### RedisMessagePublisher

Publishes chat messages to Redis.

### RedisMessageSubscriber

Listens for Redis events and forwards to WebSocket clients.

---

# 🎯 Why This Architecture Is Powerful

* **Stateless** → no user sessions stored in memory
* **Scalable** → run unlimited app instances
* **Resilient** → if one instance goes down, chat stays alive
* **Standards-based** → no vendor lock-in

This is the same pattern behind apps like Slack, Discord, and large-scale notification systems.

---

# 📌 Future Improvements

* Redis Streams for message history
* Presence/online-user tracking
* Authentication + JWT WebSocket handshakes
* Docker Compose for full local cluster
* Kubernetes deployment
* Load balancing (NGINX / Spring Cloud Gateway)

---

# 🤝 Contributing

Feel free to submit issues or PRs!
This project is great for anyone learning distributed real-time systems.

---

# ⭐ If this project helped you

Give the repo a **star** and feel free to use it in interviews or portfolios!
