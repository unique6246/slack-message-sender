// Add event listener to the Slack message form submission
document.getElementById('slackForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent default form submission behavior
    const message = document.getElementById('messageInput').value; // Get message input value

    // Send POST request to sendSlackMessage endpoint
    fetch('/sendSlackMessage', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ text: message }) // Send message as JSON
    })
        .then(response => response.text()) // Convert response to text
        .then(data => {
            document.getElementById('response').innerText = data; // Display response in HTML
            document.getElementById('messageInput').value = ''; // Clear input field
        })
        .catch(error => console.error('Error:', error)); // Log any errors
});

// Add event listener to fetch users
document.getElementById('fetchUsers').addEventListener('click', function() {
    window.location.href = 'users.html'; // Redirect to users page
});

// Add event listener to fetch messages from a specific channel
document.getElementById('fetchMessages').addEventListener('click', function() {
    window.location.href = `messages.html?channel=C07DQU7EECC`; // Redirect to messages page with channel ID
});
