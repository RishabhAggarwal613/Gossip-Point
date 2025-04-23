# Gossip Point

Gossip Point is a real-time chat application designed to facilitate seamless communication between users. It supports features like user authentication, profile management, group chats, and more.

## Features

- **User Authentication**: Secure signup and login functionality with JWT-based authentication.
- **Real-Time Messaging**: Send and receive messages instantly.
- **Profile Management**: Update profile details and upload profile pictures.
- **Group Chats**: Create and manage group conversations.
- **Search Functionality**: Search for users to start a conversation.
- **Responsive Design**: Optimized for both desktop and mobile devices.

## Technologies Used

### Backend

- **Spring Boot**: For building the RESTful API.
- **MySQL**: Database for storing user and chat data.
- **JWT**: For secure authentication.
- **WebSocket**: For real-time communication.

### Frontend

- **React**: For building the user interface.
- **Redux**: For state management.
- **Tailwind CSS**: For styling.
- **Material-UI**: For pre-styled components.
- **Vite**: For fast development and build tooling.

## Prerequisites

- **Node.js**: Version 16 or higher.
- **Java**: Version 17 or higher.
- **MySQL**: Installed and running.
- **Maven**: For building the backend.

## Setup Instructions

### Backend

1. Navigate to the `server` directory:

   ```bash
   cd server
   ```

2. Configure the database connection in `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/gossip_point
   spring.datasource.username=YOUR_DB_USERNAME
   spring.datasource.password=YOUR_DB_PASSWORD
   ```

3. Build and run the backend:
   ```bash
   ./mvnw spring-boot:run
   ```

### Frontend

1. Navigate to the `client` directory:

   ```bash
   cd client
   ```

2. Install dependencies:

   ```bash
   npm install
   ```

3. Start the development server:

   ```bash
   npm run dev
   ```

4. Open the application in your browser at [http://localhost:5173](http://localhost:5173).

## Project Structure

```
Gossip-point/
├── server/                # Backend code
│   ├── src/               # Source code
│   ├── pom.xml            # Maven configuration
│   └── application.properties # Backend configuration
├── client/                # Frontend code
│   ├── src/               # Source code
│   ├── vite.config.js     # Vite configuration
│   └── tailwind.config.js # Tailwind CSS configuration
└── README.md              # Project documentation
```

## License

This project is licensed under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

## Contact

For any inquiries or support, please contact the project maintainer.
