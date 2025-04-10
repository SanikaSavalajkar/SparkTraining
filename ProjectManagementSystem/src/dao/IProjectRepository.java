package dao;
import entity.Employee;
import entity.Project;
import entity.Task;
import java.util.List;
import exception.EmployeeNotFoundException;
import exception.ProjectNotFoundException;
import exception.TaskNotFoundException;

public interface IProjectRepository {

	boolean createEmployee(Employee emp);
	boolean createProject(Project pj);
	boolean createTask(Task tk);
	boolean assignProjectToEmployee(int project_id, int employee_id);
	boolean assignTaskInProjectToEmployee(int task_id, int project_id, int employee_id);
	public boolean deleteEmployee(int employee_id) throws EmployeeNotFoundException;
	public boolean deleteProject(int project_id) throws ProjectNotFoundException;
	public boolean deleteTask(int task_id) throws TaskNotFoundException;
	List<Task> getAllTasks(int employee_id, int project_id);
	List<Project> getAllProjects();
	List<Employee> getAllEmployees();
}