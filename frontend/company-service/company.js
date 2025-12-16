document.addEventListener('DOMContentLoaded', () => {
    const createCompanyForm = document.getElementById('create-company-form');
    const fetchAllButton = document.getElementById('fetch-all-companies');
    const fetchByIdButton = document.getElementById('fetch-company-by-id');
    const companiesTableBody = document.querySelector('#companies-table tbody');
    const messageContainer = document.getElementById('message-container');

    // Fetch all companies
    fetchAllButton.addEventListener('click', async () => {
        try {
            const companies = await apiFetch('company');
            renderCompanies(companies);
            showMessage('success', 'Fetched all companies successfully.');
        } catch (error) {
            showMessage('error', `Error fetching companies: ${error.message}`);
        }
    });

    // Fetch company by ID
    fetchByIdButton.addEventListener('click', async () => {
        const companyId = document.getElementById('fetch-company-id').value;
        if (!companyId) {
            showMessage('error', 'Please enter a company ID.');
            return;
        }
        try {
            const company = await apiFetch('company', `/${companyId}`);
            renderCompanies([company]);
            showMessage('success', `Fetched company with ID ${companyId} successfully.`);
        } catch (error) {
            showMessage('error', `Error fetching company: ${error.message}`);
        }
    });

    // Create a new company
    createCompanyForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const name = document.getElementById('company-name').value;
        const description = document.getElementById('company-description').value;
        const website = document.getElementById('company-website').value;

        try {
            await apiFetch('company', '', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name, description, website })
            });
            showMessage('success', 'Company created successfully.');
            fetchAllButton.click(); // Refresh the table
        } catch (error) {
            showMessage('error', `Error creating company: ${error.message}`);
        }
    });

    function renderCompanies(companies) {
        companiesTableBody.innerHTML = '';
        if (!Array.isArray(companies)) return;

        companies.forEach(company => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${company.id}</td>
                <td>${company.name}</td>
                <td>${company.description}</td>
                <td>${company.website}</td>
                <td>
                    <button class="delete-btn" data-id="${company.id}">Delete</button>
                </td>
            `;
            companiesTableBody.appendChild(row);
        });

        document.querySelectorAll('.delete-btn').forEach(button => {
            button.addEventListener('click', async (event) => {
                const companyId = event.target.dataset.id;
                try {
                    await apiFetch('company', `/${companyId}`, { method: 'DELETE' });
                    showMessage('success', `Company with ID ${companyId} deleted successfully.`);
                    fetchAllButton.click(); // Refresh the table
                } catch (error) {
                    showMessage('error', `Error deleting company: ${error.message}`);
                }
            });
        });
    }

    function showMessage(type, message) {
        messageContainer.textContent = message;
        messageContainer.className = type;
    }
});