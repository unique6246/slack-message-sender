# Slack Message Sender

This Spring Boot application sends messages to a Slack channel using Slack's Incoming Webhooks.


## Docker Containerization

### Pull Docker Image (Optional)
If you want to use a pre-built Docker image, you can pull it from Docker Hub:
```bash
docker pull pintu6246/slack-message-sender
docker run -d -p {PORT}:8080 --name slack-message-container pintu6246/slack-message-sender
```
change the {PORT} with your convenient port

    - Access the application at `http://localhost:{PORT}` through your browser.

## Setup
1. **Clone the Repository:**
    ```bash
    git clone https://github.com/unique6246/slack-message-sender.git
    cd slack-message-sender
    ```

2. **Update Configuration:**
    - Update the `src/main/resources/application.properties` file with your Slack Webhook URL:
      ```properties
      slack.webhook.url=YOUR_WEBHOOK_URL
      ```

3. **Build the Project:**
    ```bash
    mvn clean install
    ```

4. **Run the Application:**
    ```bash
    mvn spring-boot:run
    ```

5. **Access the UI:**
    - Open your browser and go to `http://localhost:8080` to access the UI.

## API Endpoint
- **POST /sendSlackMessage**
    - Request Body: `{ "text": "Your message here" }`
    - Response: `200 OK` with message "Message sent to Slack"




## Tests
- Unit tests and integration tests are included. To run the tests:
  ```bash
  mvn test