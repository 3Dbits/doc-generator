# Kotlin Spring Boot CV Generator

This Kotlin Spring Boot application is designed to generate CVs or resumes using JasperReports. The app provides a REST API to create CV documents in multiple formats, with a focus on extending features such as internationalization and version tracking.

## Features

- Generate CVs in customizable formats using JasperReports.
- REST API endpoint for generating CVs.
- Dockerized for easy deployment.

## Example API Endpoint

You can see an example of the CV generation API at:

[cv.matijacvetan.com/api/v1/resume-documents/en](http://cv.matijacvetan.com/api/v1/resume-documents/en)

This endpoint currently generates CVs in English. Support for other languages will be implemented as part of internationalization.

---

## Getting Started

### Docker Deployment

1. Build the Docker image:

   ```bash
   docker build -t kotlin-springboot-cv-generator .
   ```

2. Run the Docker container:

   ```bash
   docker run -p 8080:8080 kotlin-springboot-cv-generator
   ```

### Configuration

- **Templates**: Update Jasper template files in `src/main/resources/templates`.

---

## API Documentation

### Endpoint

- **GET** `/api/v1/resume-documents/{language}`

#### Parameters

- `language`: The language code for the CV. Only `en` is currently supported.

#### Response

- **200 OK**: Returns the generated CV file in the desired format (e.g., PDF).
- **400 Bad Request**: If the request parameters are invalid.
- **500 Internal Server Error**: If there is an issue with CV generation.

---

## Roadmap

- [ ] Implement full internationalization (i18n) support.
- [ ] Enhance template customization options.
- [ ] Improve error handling and validation.
- [ ] Add more tests to cover edge cases.

---

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bugfix.
3. Commit your changes with descriptive messages.
4. Push to your fork and create a pull request.

---


Feel free to reach out for questions or suggestions!

