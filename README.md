# Solar Watch

## About the Project

[![Solar Watch Screen Shot][screenshot]](https://example.com)

The "Solar Watch" project is a full-stack web application developed as part of a Codecool assignment. It enables users to retrieve sunrise and sunset times for selected cities on specific dates. The backend is built with Java and Spring Boot, while the frontend utilizes JavaScript, all containerized using Docker for streamlined deployment.


### Core Features

* **Sunrise and Sunset Times:** Users can input a city and date to retrieve the corresponding sunrise and sunset times.
* **User Authentication:** The application includes user registration and login functionalities to personalize the experience.
* **Admin Controls:** Administrators have additional capabilities, such as managing city data (Backend only).
* **Dockerization:** The application is fully containerized using Docker.
* **Backend Tests:** Unit tests cover the business logic of the Backend side of the application and there are integration tests ensuring system reliability.


### Built with

* [![Node.js][Node-ico]][Node-url]
* [![React][React-ico]][React-url]
* [![Tailwind css][Tailwind-ico]][Tailwind-url]
* [![DaisyUI][DaisyUI-ico]][DaisyUI-url]
* [![Maven][Maven-ico]][Maven-url]
* [![Java Spring][JavaSpring-ico]][JavaSpring-url]
* [![JUnit][JUnit-ico]][JUnit-url]
* [![PostgreSQL][PostgreSQL-ico]][PostgreSQL-url]


## Getting Started

#### About Geocoding API key

To use this application you will need an API key from the Openweathermar.org website. For the details see for [Openweather Geocoding API page][Openweathermap-API]. This key has to be set up in different ways depensing on the way you want to run the application (see the next sections).

#### Download app

You can download the application here: [Solar Watch GitHub page](https://github.com/mecsbalint/solar-watch). Click on the Code button and choose the Download ZIP option. After downloading unzip it.
Or alternatively clone the repository: ```git clone https://github.com/mecsbalint/solar-watch.git```

### With Docker
#### Prerequisites

* **Docker Desktop:** You can download from here: [Docker Desktop][Docker-Desktop]. You have to sign up to use the Docker Desktop app.

#### Set Up and Run
1. **Install and Run Docker Desktop:** Download and run the installation file and follow the steps. After installation run the Docker Desktop application.
2. **Set up .env file**
    1. Rename the `.env.example` file to `.env` in the app's root folder
    2. Add the personal Geocoding API key to the `GEOCODING_API_KEY` variable as value. For example: `GEOCODING_API_KEY=your_api_key_here`
    3. Optionally you can change the JWT secret key, the JWT expiration time, the database' username or password or the port number (for more details see [.env.example](https://github.com/mecsbalint/solar-watch/blob/main/.env.example))
3. **Start the Application:** Run the `docker-start.bat` batch file from the application's root folder.
4. **Access the Application:** Open a web browser and go to `http://localhost:` + `PORT_NUMBER` to access the frontend (by default it's `http://localhost:5173`). The port number can be changed in the `.env.example` file.
5. **Stop the application:** Run the `docker-stop.bat` batch file from the application's root folder.

### Manual Setup (Without Docker)
#### Prerequisites

* **Java Development Kit** 21 or above [Download](https://www.oracle.com/java/technologies/downloads/)
* **Apache Maven** [Download](https://maven.apache.org/download.cgi)
* **Node.js** [Download](https://nodejs.org/en/download)
* **PostgreSQL** [Download](https://www.postgresql.org/download/)

#### Installation

1. **Set up PostgreSQL Database**
    1. Create a database dedicated to this application ([step-by-step guide](https://www.postgresql.org/docs/current/tutorial-createdb.html))
    2. Replace the `spring.datasource.url`, `spring.datasource.username` and `spring.datasource.password` variables' value with your database' properties in the `\solarwatch_backend\src\main\resources\application.properties` file.
2. **Set up, Build and Run Spring Boot Backend**
    1. Replace the `mecsbalint.app.geocodingApiKey` variable's value with your personal Geocoding API key and set up the `mecsbalint.app.jwtExpirationMs` and `mecsbalint.app.jwtSecret` variables in the `\solarwatch_backend\src\main\resources\application.properties` file.
    2. Open a terminal and navigate to the `\solarwatch_backend` folder
    3. Run the `./mvnw clean package` command (build the backend application with Maven).
    4. Run the `./mvnw spring-boot:run` command (run the backend application).
3. **Install dependencies and Run the React Frontend**
    1. Open a terminal and navigate to the `\solarwatch_frontend` folder
    2. Run the `npm install` command (install dependencies)
    3. Run the `npm run dev` command (run the frontend application)
4. **Access the Application**
    1. Click on the `Local` link in the terminal where the frontend is running.


## Contact

mecsbalint@gmail.com - https://github.com/mecsbalint


<!-- Links -->
[Openweathermap-API]: https://openweathermap.org/api/geocoding-api

[Docker-Desktop]: https://www.docker.com/products/docker-desktop/

[screenshot]: readme_resources/screenshot_01.png

[Node-ico]: https://img.shields.io/badge/Node.js-35495E?style=for-the-badge&logo=node.js
[Node-url]: https://nodejs.org/

[React-ico]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react
[React-url]: https://reactjs.org/

[Tailwind-ico]: https://img.shields.io/badge/Tailwind-35495E?style=for-the-badge&logo=tailwindcss
[Tailwind-url]: https://tailwindcss.com/

[DaisyUI-ico]: https://img.shields.io/badge/DaisyUI-DD0031?style=for-the-badge&logo=daisyui
[DaisyUI-url]: https://daisyui.com/

[Maven-ico]: https://img.shields.io/badge/Maven-0769AD?style=for-the-badge&logo=apachemaven
[Maven-url]: https://maven.apache.org/

[JavaSpring-ico]: https://img.shields.io/badge/Spring-FF2D20?style=for-the-badge&logo=spring
[JavaSpring-url]: https://spring.io/

[JUnit-ico]: https://img.shields.io/badge/JUnit-563D7C?style=for-the-badge&logo=junit5
[JUnit-url]: https://junit.org/junit5/

[PostgreSQL-ico]: https://img.shields.io/badge/PostgreSQL-4A4A55?style=for-the-badge&logo=postgresql
[PostgreSQL-url]: https://www.postgresql.org/
