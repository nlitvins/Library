# 📚 Library

**Library** is a library management system. It allows users to browse books and
librarians to manage them (add, reserve edit, delete). The project includes authentication and role-based access
control.

---

## 🚀 Features

- User authentication and registration
- Roles: **Administrator**, **Librarian** and **Reader**
- Book management (CRUD operations)
- Book list with search and filtering
- REST API for frontend-backend communication

---

## 🛠️ Tech Stack

**Backend**:

- Java 17, Spring Boot
- Spring Security
- JPA / Hibernate
- PostgreSQL

**Frontend**:

- Angular
- TypeScript

**DevOps**:

- Docker & docker-compose
- Swagger / OpenAPI (API documentation)

---

## ⚙️ How to Run

### 1. Clone the repository

```bash
git clone https://github.com/nlitvins/Library.git
cd Library
```

### 2. Run with Docker

```bash
docker-compose up --build
```

After startup:

- Backend: `http://localhost:8080`
- Frontend: `http://localhost:3000`

---

## 👤 Demo Users

- **Administrator**  
  Login: `admin`  
  Password: `admin123`


- **Librarian**

  Login:     `librarian`  
  Password: `librarian123`

- **Reader**  
  Login: `user`  
  Password: `user123`

---

## 📖 API Documentation

Swagger UI is available after startup:

```
http://localhost:8080/swagger-ui.html
```

---

## 🔮 Future

- [ ] Improve frontend design
- [ ] Add pagination and book search
- [ ] Write unit tests for the backend
- [ ] Automate deployment to a server

---

## 📜 License

This project is licensed under the [MIT License](LICENSE).  
