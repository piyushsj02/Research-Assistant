# Research Assistant

A Spring Boot application that sends text to the Google Gemini API and returns AI-generated research output. The project is built to handle lightweight content analysis tasks such as summarization and topic suggestions.

## Overview

This repository contains a Java-based REST API that accepts a user-provided text block and an operation type, then uses the Gemini API to generate a result. It is a simple service-oriented project designed for research assistance workflows.

The application currently supports:

- `summarize`: returns a concise summary of the input text
- `suggest`: returns related topics and reading ideas based on the provided content

## Project Purpose

The app is intended for quick AI-assisted content exploration. It can be used as a backend for:

- summarizing documents or notes
- generating topic ideas from a paragraph
- embedding simple AI processing into another application or frontend

## Tech Stack

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring WebFlux
- Maven
- Google Gemini API

## Project Structure

```text
research-assistant/
├── src/
│   ├── main/
│   │   ├── java/com/research/assistant/
│   │   │   ├── controller/
│   │   │   │   └── ResearchController.java
│   │   │   ├── dto/
│   │   │   │   ├── GeminiResponse.java
│   │   │   │   └── ResearchRequest.java
│   │   │   ├── service/
│   │   │   │   └── ResearchService.java
│   │   │   ├── ResearchAssistantApplication.java
│   │   │   └── WebClientConfig.java
│   │   └── resources/
│   │       └── application.yaml
│   └── test/
│       └── java/com/research/assistant/
│           └── ResearchAssistantApplicationTests.java
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
├── HELP.md
├── build.out
└── README.md
```

## Application Flow

1. A client sends a POST request to `/api/research`.
2. The controller receives the `ResearchRequest` payload.
3. The service builds a prompt depending on the requested operation.
4. The request is sent to the Gemini API through `WebClient`.
5. The API response is parsed and returned as a plain string response.

## Prerequisites

Before running the project, ensure you have:

- Java 21 or later
- Maven or the included Maven wrapper (`mvnw` / `mvnw.cmd`)
- A valid Google Gemini API key

## Configuration

The API key is loaded from an environment variable named `GEMINI_KEY`.

### macOS/Linux

```bash
export GEMINI_KEY=your_api_key_here
```

### Windows PowerShell

```powershell
$env:GEMINI_KEY = "your_api_key_here"
```

The project configuration is in `src/main/resources/application.yaml`:

```yaml
spring:
  application:
    name: research-assistant

gemini:
  api:
    url: https://generativelanguage.googleapis.com/v1beta/interactions?key=
    key: ${GEMINI_KEY}
```

## Running the Application

### Using the Maven wrapper

```bash
./mvnw spring-boot:run
```

### On Windows

```powershell
mvnw.cmd spring-boot:run
```

### Using Maven directly

```bash
mvn spring-boot:run
```

The application runs on the default Spring Boot port:

```text
http://localhost:8080
```

## API Endpoint

### POST `/api/research`

This endpoint accepts a JSON request body with:

- `content`: the text to analyze
- `operation`: either `summarize` or `suggest`

### Example request

```bash
curl -X POST http://localhost:8080/api/research \
  -H "Content-Type: application/json" \
  -d '{
    "content": ""Avul Pakir Jainulabdeen Abdul Kalam (/ˈʌbdʊl kəˈlɑːm/ ⓘ UB-duul kə-LAHM; 15 October 1931 – 27 July 2015) was an Indian aerospace engineer and science administrator who served as president of India from 2002 to 2007. The Government of India honoured him with the Padma Bhushan in 1981 and the Padma Vibhushan in 1990.[2] In 1997, he was awarded India's highest civilian honour, the Bharat Ratna, for his contribution to the scientific research and modernisation of defence technology in India.[3] Born and raised in a Muslim family in Rameswaram, Tamil Nadu, Kalam studied physics and aerospace engineering. He spent the next four decades as a scientist and science administrator, mainly at the Defence Research and Development Organisation (DRDO) and Indian Space Research Organisation (ISRO) and was intimately involved in India's civilian space programme and military missile development efforts. He is popularly known as the Missile Man of India for his work on the development of ballistic missile and launch vehicle technology. He also played a pivotal organisational, technical, and political role in Pokhran-II nuclear tests in 1998, India's second such test after the first test in 1974.Kalam was elected as the president of India in 2002 with the support of both the ruling Bharatiya Janata Party and the then-opposition Indian National Congress. He was widely referred to as the People's President. He engaged in teaching, writing and public service after his presidency. Kalam is known as Missile Man of India. While delivering a lecture at IIM Shillong, Kalam collapsed and died from an apparent cardiac arrest on 27 July 2015, aged 83. Thousands attended the funeral ceremony held in his hometown of Rameswaram, where he was buried with full state honours. A memorial was inaugurated near his home town in 2017.",
    "operation": "summarize"
  }'
```

### Example response

```json
"A.P.J. Abdul Kalam was an Indian aerospace engineer and science administrator who served as the president of India from 2002 to 2007. Popularly known as the "Missile Man of India," he spent four decades at DRDO and ISRO playing a vital role in India's space program, missile development, and the 1998 Pokhran-II nuclear tests, which earned him the Bharat Ratna. Referred to as the "People's President," he dedicated his post-presidency to teaching, writing, and public service until his death in 2015."
```

## Supported Operations

### `summarize`

Used to generate a brief summary of the provided text.

### `suggest`

Used to generate topic suggestions and related ideas based on the input content.

## Data Models

### `ResearchRequest`

```java
public class ResearchRequest {
    private String content;
    private String operation;
}
```

### `GeminiResponse`

The project parses the Gemini API response using nested DTOs to extract the model output text from the generated response structure.

## Notes

- The project uses Spring WebClient for outbound HTTP calls to the Gemini service.
- The service currently returns the generated text as a plain string.
- Error handling is minimal and may require adjustment if the Gemini API response format changes.
- This project is a backend service and does not include a frontend UI.

## License

No explicit license has been added to this project yet. If needed, a license file should be added before publication or distribution.

## Contributing

Contributions are welcome. If you plan to make significant changes, please open an issue or discuss the intended update before submitting a pull request.
