document.getElementById('slackForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const message = document.getElementById('messageInput').value;

    fetch('/sendSlackMessage', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ text: message })
    })
        .then(response => response.text())
        .then(data => {
            document.getElementById('response').innerText = data;
            document.getElementById('messageInput').value = '';
        })
        .catch(error => console.error('Error:', error));
});

document.getElementById('fetchUsers').addEventListener('click', function() {
    window.location.href = 'users.html';
});

document.getElementById('fetchMessages').addEventListener('click', function() {

        window.location.href = `messages.html?channel=C07DQU7EECC`;

});
