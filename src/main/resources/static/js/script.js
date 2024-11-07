async function handleSubmit(event) {
    event.preventDefault();

    const formData = new FormData(event.target);
    const input = formData.get('inputField');

    // Log input immediately to console
    console.log(`User input: ${input}`);

    // Send input to server
    try {
        const response = await fetch('/submit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ input })
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        } else {
            console.log('Input successfully sent to the server');
        }
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}
document.addEventListener('DOMContentLoaded', function() {
    const editLogModal = document.getElementById('editLogModal');
    const editLogForm = document.getElementById('editLogForm');

    editLogModal.addEventListener('show.bs.modal', function(event) {
        const button = event.relatedTarget;
        const logId = button.getAttribute('data-log-id');

        fetch(`/dashboard/log/${logId}`)
            .then(response => response.json())
            .then(data => {
                editLogForm.querySelector('[name="id"]').value = data.id;
                editLogForm.querySelector('[name="description"]').value = data.description;
                // Populate additional fields as necessary
            });
    });
});