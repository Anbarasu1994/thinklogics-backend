# THINKLOGICS

## Description

This project is built using Spring Boot and connects to a MongoDB database. It provides APIs for various functionalities related to attendees, visitors, enquiries, job applications, and job descriptions.

## Requirements

- Java Development Kit (JDK) 8 or later
- Apache Maven
- MongoDB Server

## Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/project-name.git
   cd project-name

2. **Database Configuration:**

Ensure MongoDB is installed and running.
Configure MongoDB connection details in `application.properties` :

`spring.data.mongodb.host=localhost`
`spring.data.mongodb.port=27017`
`spring.data.mongodb.database=your_database_name`

Build and Run:

Build the project using Maven:
   `mvn clean install`



API Endpoints:

Public Endpoints (No Authentication Required):

`/user/login` - POST: User login

`/user/register` - POST: Register new user

`/user/verify-otp` - POST: Verify user OTP

`/attendees/exists/{phoneNumber}` - GET: Check if attendee exists by phone number

`/attendees/create` - POST: Create new attendee

`/visitors/create` - POST: Create new visitor

`/visitors/checkEmail` - POST: Check visitor email availability

`/enquiries/create` - POST: Create new enquiry

`/jobApplications/create` - POST: Create new job application

`/jobDescriptions/getAll` - GET: Get all job descriptions

`/jobDescriptions/getById/{id}` - GET: Get job description by ID

`/Admin/api/sessions/list` -GET: Get all sessions in list


Admin Endpoints (Requires Admin Authorization):

`/user/all` - GET: Get all users

`/Admin/api/sessions/create` - POST: Create new session (admin only)

`/Admin/api/sessions/{id}` - GET: Get session by ID (admin only)

`/Admin/api/sessions/delete/{id}` - DELETE: Delete session by ID (admin only)

`/Admin/api/sessions/update/{id}` - PUT: Update session by ID (admin only)

`/attendees/**` - Attendees endpoints (admin only)

`/attendees/list` - GET:
`/attendees/delete/{id}` - DELETE :
`/attendees/update/{id}` - PUT:

`/visitors/**` - Visitors endpoints (admin only)

`/enquiries/**` - Enquiries endpoints (admin only)
`/enquiries/list` - GET: List of enquiries
`/jobApplications/**` - Job applications endpoints (admin only)

`/jobDescriptions/**` - Job descriptions endpoints (admin only)
`/jobDescriptions/create`-  POST:



**ADMIN ENDPOINTS :**






 **Testing the APIs:**

Use tools like Postman to test the exposed APIs. Ensure proper authentication and authorization headers for authorized endpoints.
