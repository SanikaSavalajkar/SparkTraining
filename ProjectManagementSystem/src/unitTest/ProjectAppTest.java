package unitTest;

import dao.ProjectRepositoryImpl;
import entity.Employee;
import entity.Project;
import entity.Task;
import exception.EmployeeNotFoundException;
import exception.ProjectNotFoundException;
import exception.TaskNotFoundException;

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
        assertTrue(success, "Project creation should succeed");

        Project inserted = repo.getProjectByNameAndDate("Test Project", LocalDate.now());
        assertNotNull(inserted, "Inserted project should be retrievable");
        return inserted.getProjectId();
    }

    private int createEmployeeAndReturnId(int projectId) {
        Employee emp = new Employee(0, "Sanika", "Developer", "Female", 500000, projectId);
        boolean created = repo.createEmployee(emp);
        assertTrue(created, "Employee creation should succeed");

        Employee inserted = repo.getEmployeeByNameAndProject("Sanika", projectId);
        assertNotNull(inserted, "Inserted employee should be retrievable");
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

        Task task = new Task(0, "Unit Test Task", projectId, employeeId, "assigned", LocalDate.now(), LocalDate.now().plusDays(7));
        boolean result = repo.createTask(task);
        assertTrue(result, "Task created successfully");
    }

    @Test
    @Order(3)
    public void testSearchTasksForEmployeeInProject() {
        int projectId = createProjectAndReturnId();
        int employeeId = createEmployeeAndReturnId(projectId);

        Task task = new Task(0, "Search Task", projectId, employeeId, "started", LocalDate.now(), LocalDate.now().plusDays(5));
        boolean created = repo.createTask(task);
        assertTrue(created, "Task should be created for searching");

        List<Task> tasks = repo.getAllTasks(employeeId, projectId);
        assertNotNull(tasks, "Task list should not be null");
        assertFalse(tasks.isEmpty(), "Task list should not be empty");
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
        assertThrows(TaskNotFoundException.class, () -> {
            repo.deleteTask(invalidTaskId);
        });
    }
}
