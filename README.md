
---

# Health Portal System

The Health Portal System is a multi-agent system designed to facilitate interactions between patients and medical specialists. The system allows users to register, schedule appointments with specialists, and manage their health-related activities.
This repository contains the codebase for a Multi-Agent System designed for Healthcare Appointment Scheduling, developed as part of the Agent-based Software Engineering (SENG696) course by Group 5 - Rahul Jha and Narges Babadi.


## Table of Contents
- [Introduction](#health-portal-system)
- [Features](#features)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)

## Features

- **User Registration:** Patients can register in the system with their personal information.
- **Appointment Scheduling:** Patients can schedule appointments with medical specialists based on their availability.
- **Specialist Services:** Medical specialists can list their services and availability.
- **Payment Integration:** The system supports payment for medical services.
- **Multi-Agent System:** Utilizes the JADE (Java Agent DEvelopment Framework) for multi-agent communication.

## Architecture

The system is built on a multi-agent architecture using JADE. The primary agents include:

## Primary Agents

### 1. Portal Agent

- **Description:** Handles patient requests for appointments.
- **Functionality:**
  - Manages user registration and login.
  - Displays specialist categories and lists.
  - Requests availability from the Appointment Agent.
  - Collects user preferences and interacts with the Payment Agent.
  - Generates a booking memo for users.

### 2. Access Agent

- **Description:** Manages user registration and login processes.
- **Functionality:**
  - Receives registration requests from the Portal Agent.
  - Validates user login information against the Patient Database.
  - Saves new user information in the Patient Database.

### 3. Specialist Agent

- **Description:** Provides information about available specialists.
- **Functionality:**
  - Supplies a list of specialist categories.
  - Based on user selection, provides a list of available specialists.
  - Retrieves availability information for the selected specialist.

### 4. Payment Agent

- **Description:** Facilitates patient payments.
- **Functionality:**
  - Receives payment information from the Portal Agent.
  - Validates payment information.
  - Returns transaction details and status to the Portal Agent.

### 5. Appointment Agent

- **Description:** Manages appointment scheduling.
- **Functionality:**
  - Writes appointment information to the Appointment Database.
  - Updates payment status after successful payment.

## Getting Started

To run the Health Portal System locally, follow these steps:

1. Clone the repository:

    ```bash
    git clone https://github.com/rkj1998/SENG_696.git
    ```

2. Open the project in your preferred IDE.

3. Build and run the project.

## Usage

1. Start the Health Portal System.

2. Register as a patient or specialist.

3. Log in to your account.

4. Schedule appointments, view specialists, and manage your health-related activities.

## Contributors

- Rahul Jha
- Narges Babadi

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

