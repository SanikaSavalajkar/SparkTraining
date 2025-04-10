package unitTest;

import dao.ProjectRepositoryImpl;
import entity.Employee;
import entity.Project;
import entity.Task;
import exception.EmployeeNotFoundException;
import exception.ProjectNotFoundException;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectAppTest {

    private static ProjectRepositoryImpl repo;

    @BeforeAll
    static void initRepo() {
        repo = new ProjectRepositoryImpl();
    }

    private int createProjectAndReturnId() {
        Project project = new Project();
        project.setProjectName("Test Project");
        project.setDescription("For testing");
        project.setStartDate(LocalDate.now());
        project.setStatus("started");

        boolean success = repo.createProject(project);
        if (!success) {
            throw new RuntimeException("Failed to create project");
        }

        Project inserted = repo.getProjectByNameAndDate("Test Project", LocalDate.now());
        if (inserted == null) {
            throw new RuntimeException("No project found with the expected name and date.");
        }

        return inserted.getProjectId();
    }

    private int createEmployeeAndReturnId(int projectId) {
        Employee emp = new Employee(0, "Sanika", "Developer", "Female", 500000, projectId);
        boolean created = repo.createEmployee(emp);
        assertTrue(created, "Employee created");

        Employee inserted = repo.getEmployeeByNameAndProject("Sanika", projectId);
        if (inserted == null) {
            throw new RuntimeException("No employee found after insertion.");
        }
        return inserted.getEmployeeId();
    }

    @Test
    @Order(1)
    public void testCreateEmployeeSuccess() {
        int projectId = createProjectAndReturnId();
        Employee emp = new Employee(0, "Mruganka", "Tester", "Female", 400000, projectId);
        boolean result = repo.createEmployee(emp);
        assertTrue(result, "Employee created successfully");
    }

    @Test
    @Order(2)
    public void testCreateTaskSuccess() {
        int projectId = createProjectAndReturnId();
        int employeeId = createEmployeeAndReturnId(projectId);

        Task task = new Task(0, "Unit Test Task", projectId, employeeId, "assigned");
        boolean result = repo.createTask(task);
        assertTrue(result, "Task created successfully");
    }

    @Test
    @Order(3)
    public void testSearchTasksForEmployeeInProject() {
        int projectId = createProjectAndReturnId();
        int employeeId = createEmployeeAndReturnId(projectId);

        Task task = new Task(0, "Search Task", projectId, employeeId, "started");
        repo.createTask(task);

        List<Task> tasks = repo.getAllTasks(employeeId, projectId);
        assertNotNull(tasks, "Task list should not be null");
        assertTrue(tasks.size() > 0, "There should be at least one task assigned");
    }

    @Test
    @Order(4)
    public void testEmployeeNotFoundExceptionThrown() {
        int invalidEmpId = 9999;
        assertThrows(EmployeeNotFoundException.class, () -> {
            repo.deleteEmployee(invalidEmpId);
        });
    }

    @Test
    @Order(5)
    public void testProjectNotFoundExceptionThrown() {
        int invalidProjectId = -1;
        assertThrows(ProjectNotFoundException.class, () -> {
            repo.deleteProject(invalidProjectId);
        });
    }
    
    @Test
    @Order(6)
    public void testTaskNotFoundExceptionThrown() {
        int invalidTaskId = 9999;
        assertThrows(exception.TaskNotFoundException.class, () -> {
            repo.deleteTask(invalidTaskId);
        });
    }
}