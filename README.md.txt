# Back-End-Project

## Description

**Back-End-Project** is a demonstration portfolio project developed for learning Java and Spring Boot technologies. It implements four key features focused on task management, habit tracking, life analysis, and journaling. Additionally, the project integrates AI to generate inspiring quotes.

## Main Features

### 1. ToDo List

A simple task management system offering the following functionalities:

- Add a task with a title, description, and priority level:

- Easy

- Medium

- Hard

- Goal

- Mark a task as completed.

- Delete a task.

### 2. Habits Tracker

A system for tracking habits:

- Record a habit for the week (from Monday to Sunday).

- Mark the days a habit was followed.

- Automatically update the list after completing the week.

- Track the number of weeks the habit was followed.

### 3. Diary

An electronic journaling system:

- Add entries with a title and the current mood:

- Bad

- Normal

- Great

- Productive

- Save all diary entries for future analysis.

### 4. "Wheel of Life"

A module for analyzing eight areas of life:

- Life areas:

- Health & Sport

- Career

- Finances

- Personal Growth

- Friends & Environment

- Relationships

- Spirituality & Creativity

- Life Brightness

- The user rates each area on a scale of 1 to 10.

- Analyze areas that need development.

- Add thoughts and plans for improvement directly into the ToDo List.

- Repeat the analysis after two months to monitor progress.

### 5. Inspirational Quote Generator

- Inspirational quotes are displayed at the top of the application, generated using AI and the Groq API key.

- New quotes are generated every time the page is refreshed.

## Technologies

The following technologies are used in the development:

- **Java** — the primary programming language.

- **Spring Boot** — backend development.

- **Microsoft Azure (MySQL)** — hosting and supporting the database.

- **JavaScript, CSS, HTML** — frontend development and design.

## Installation and Launch

> **Note**: These instructions can be updated later. If you recall the exact setup process, feel free to share it. Below are the general steps.

1. Ensure the following dependencies are installed:

- Java JDK (version 11 or higher)

- Maven

- Git

2. Clone the repository:

```bash

git clone https://github.com/YouMei2/Back-End-Project.git

```

3. Configure the MySQL database in Microsoft Azure:

- Create a database and add the relevant configurations (username, password) in the project's `application.properties` file.

4. Build the project using Maven:

```bash

mvn clean install

```

5. Run the project:

```bash

mvn spring-boot:run

```

6. Open the application in a browser:

```text

http://localhost:8080

```

## Project Structure

```

Back-End-Project/
├── BackEnd/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   │       ├── application.properties # Database configurations
│   │   │     
│   │   └── test/
│   │       └── java/
│   ├── pom.xml # Maven dependencies
│   └── README.md # Project documentation
└── FrontEnd/
    ├── index.html
    ├── style.css
    ├── script.js
    ├── *.html
    ├── *.css
    ├── *.js

```

## Contacts

If you have any questions or suggestions, feel free to contact me through my GitHub profile.

---

If you'd like to update this further (e.g., with API endpoint descriptions or additional setup details), let me know!