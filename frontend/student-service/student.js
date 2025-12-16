document.addEventListener('DOMContentLoaded', () => {
    const createStudentForm = document.getElementById('create-student-form');
    const fetchAllButton = document.getElementById('fetch-all-students');
    const fetchByIdButton = document.getElementById('fetch-student-by-id');
    const studentsTableBody = document.querySelector('#students-table tbody');
    const messageContainer = document.getElementById('message-container');

    // Fetch all students
    fetchAllButton.addEventListener('click', async () => {
        try {
            const students = await apiFetch('student');
            renderStudents(students);
            showMessage('success', 'Fetched all students successfully.');
        } catch (error) {
            showMessage('error', `Error fetching students: ${error.message}`);
        }
    });

    // Fetch student by ID
    fetchByIdButton.addEventListener('click', async () => {
        const studentId = document.getElementById('fetch-student-id').value;
        if (!studentId) {
            showMessage('error', 'Please enter a student ID.');
            return;
        }
        try {
            const student = await apiFetch('student', `/${studentId}`);
            renderStudents([student]);
            showMessage('success', `Fetched student with ID ${studentId} successfully.`);
        } catch (error) {
            showMessage('error', `Error fetching student: ${error.message}`);
        }
    });

    // Create a new student
    createStudentForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const name = document.getElementById('student-name').value;
        const course = document.getElementById('student-course').value;
        const fee = document.getElementById('student-fee').value;

        try {
            await apiFetch('student', '', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name, course, fee })
            });
            showMessage('success', 'Student created successfully.');
            fetchAllButton.click(); // Refresh the table
        } catch (error) {
            showMessage('error', `Error creating student: ${error.message}`);
        }
    });

    function renderStudents(students) {
        studentsTableBody.innerHTML = '';
        if (!Array.isArray(students)) return;

        students.forEach(student => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.course}</td>
                <td>${student.fee}</td>
                <td>
                    <button class="delete-btn" data-id="${student.id}">Delete</button>
                </td>
            `;
            studentsTableBody.appendChild(row);
        });
        
        document.querySelectorAll('.delete-btn').forEach(button => {
            button.addEventListener('click', async (event) => {
                const studentId = event.target.dataset.id;
                try {
                    await apiFetch('student', `/${studentId}`, { method: 'DELETE' });
                    showMessage('success', `Student with ID ${studentId} deleted successfully.`);
                    fetchAllButton.click(); // Refresh the table
                } catch (error) {
                    showMessage('error', `Error deleting student: ${error.message}`);
                }
            });
        });
    }

    function showMessage(type, message) {
        messageContainer.textContent = message;
        messageContainer.className = type;
    }
});