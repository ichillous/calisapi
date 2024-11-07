# Calis100 - 100 Day Calisthenics Challenge 💪

![Calis100 Logo](/src/main/resources/static/images/logo.png)

## Overview

Calis100 is a web application designed to help users complete a 100-day calisthenics challenge. Track your progress, set goals, and join a community of fitness enthusiasts on their journey to better health through bodyweight exercises.

## Features 🌟

- **100-Day Challenge Tracking**: Monitor your progress through a structured 100-day fitness program
- **Exercise Logging**: Record daily performance for:
    - Push-ups
    - Pull-ups
    - Sit-ups
    - Squats
    - Plank holds
    - Running distance
- **User Dashboard**: Visualize your progress and achievements
- **Community Support**: Connect with other participants for motivation and tips
- **Secure Authentication**: Safe and secure user account management

## Technology Stack 🛠

- **Backend**:
    - Java Spring Boot
    - Spring Security
    - JPA/Hibernate
    - PostgreSQL
- **Frontend**:
    - Thymeleaf
    - Bootstrap 5
    - Font Awesome
- **Build Tool**:
    - Maven

## Getting Started 🚀

### Prerequisites

- Java JDK 17 or higher
- Maven
- PostgreSQL

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/calis100.git
```

2. Navigate to the project directory:
```bash
cd calis100
```

3. Configure the database connection in `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/calis100
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. Build the project:
```bash
mvn clean install
```

5. Run the application:
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`

## Database Schema 📊

### User
- Stores user account information
- Manages authentication and profile data
- Links to user challenges

### Challenge
- Tracks individual 100-day challenges
- Maintains challenge status and progress
- Connected to daily exercise logs

### Log
- Records daily exercise achievements
- Stores exercise counts and metrics
- Links to specific challenges

## API Endpoints 🔌

### Authentication
- `GET /login` - Login page
- `GET /register` - Registration page
- `POST /register` - Create new account

### Dashboard
- `GET /dashboard` - User dashboard
- `GET /dashboard/log` - View exercise logs
- `POST /dashboard/log/save` - Save new exercise log
- `GET /dashboard/log/{id}` - View specific log
- `POST /dashboard/log/delete/{id}` - Delete log entry

### Profile
- `GET /profile` - View user profile
- `POST /edit` - Update profile information
- `POST /users/update-username` - Update username

## Contributing 🤝

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


## Contact 📧

Project Link: [https://github.com/ichillous/calisapi](https://github.com/ichillous/calisapi)

Website: [calis100.com](https://www.calis100.com)

---

Built with ❤️ for the calisthenics community