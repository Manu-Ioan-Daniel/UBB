-- Up: Create projects table with FK to materii (departments implied as materii for example)
CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    start_date DATE,
    department_id INTEGER,
    CONSTRAINT fk_projects_department FOREIGN KEY (department_id) REFERENCES materii(id) ON DELETE SET NULL
);

-- Example seed data (separate data migration could be used)
INSERT INTO projects(name, description, start_date, department_id)
VALUES
('Project A', 'Descriere proiect A', '2024-01-01', 1),
('Project B', 'Descriere proiect B', '2024-02-01', 2);

