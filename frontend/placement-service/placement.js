document.addEventListener('DOMContentLoaded', () => {
    const createPlacementForm = document.getElementById('create-placement-form');
    const fetchAllButton = document.getElementById('fetch-all-placements');
    const fetchByIdButton = document.getElementById('fetch-placement-by-id');
    const placementsTableBody = document.querySelector('#placements-table tbody');
    const messageContainer = document.getElementById('message-container');

    // Fetch all placements
    fetchAllButton.addEventListener('click', async () => {
        try {
            const placements = await apiFetch('placement');
            renderPlacements(placements);
            showMessage('success', 'Fetched all placements successfully.');
        } catch (error) {
            showMessage('error', `Error fetching placements: ${error.message}`);
        }
    });

    // Fetch placement by ID
    fetchByIdButton.addEventListener('click', async () => {
        const placementId = document.getElementById('fetch-placement-id').value;
        if (!placementId) {
            showMessage('error', 'Please enter a placement ID.');
            return;
        }
        try {
            const placement = await apiFetch('placement', `/${placementId}`);
            renderPlacements([placement]);
            showMessage('success', `Fetched placement with ID ${placementId} successfully.`);
        } catch (error) {
            showMessage('error', `Error fetching placement: ${error.message}`);
        }
    });

    // Create a new placement
    createPlacementForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const title = document.getElementById('placement-title').value;
        const description = document.getElementById('placement-description').value;
        const companyId = document.getElementById('placement-company-id').value;
        const jobType = document.getElementById('placement-job-type').value;

        try {
            await apiFetch('placement', '', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ title, description, companyId, jobType })
            });
            showMessage('success', 'Placement created successfully.');
            fetchAllButton.click(); // Refresh the table
        } catch (error) {
            showMessage('error', `Error creating placement: ${error.message}`);
        }
    });

    function renderPlacements(placements) {
        placementsTableBody.innerHTML = '';
        if (!Array.isArray(placements)) return;

        placements.forEach(placement => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${placement.id}</td>
                <td>${placement.title}</td>
                <td>${placement.description}</td>
                <td>${placement.companyId}</td>
                <td>${placement.jobType}</td>
                <td>
                    <button class="delete-btn" data-id="${placement.id}">Delete</button>
                </td>
            `;
            placementsTableBody.appendChild(row);
        });

        document.querySelectorAll('.delete-btn').forEach(button => {
            button.addEventListener('click', async (event) => {
                const placementId = event.target.dataset.id;
                try {
                    await apiFetch('placement', `/${placementId}`, { method: 'DELETE' });
                    showMessage('success', `Placement with ID ${placementId} deleted successfully.`);
                    fetchAllButton.click(); // Refresh the table
                } catch (error) {
                    showMessage('error', `Error deleting placement: ${error.message}`);
                }
            });
        });
    }

    function showMessage(type, message) {
        messageContainer.textContent = message;
        messageContainer.className = type;
    }
});