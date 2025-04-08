package main;
import dao.ProjectRepositoryImpl;
import entity.Project;
import entity.Employee;
import entity.Task;
import exception.TaskNotFoundException;
import exception.EmployeeNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

public class ProjectApp {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ProjectRepositoryImpl repo = new ProjectRepositoryImpl();
		
		while(true) {
			System.out.println("\n *****PROJECT MANAGEMENT SYSTEM*****");
			System.out.println("1. Add Employee");
			System.out.println("2. Add Project");
			System.out.println("3. Add Task");
			System.out.println("4. Assign Project to Employee");
			System.out.println("5. Assign Task within a Project to an Employee");
			System.out.println("6. Delete Employee");
			System.out.println("7. Delete Task");
			System.out.println("8. List all projects assigned with tasks to an employee");
			System.out.println("9. Exit");
			System.out.println("Enter your choice: ");
			int choice = scanner.nextInt();
			
			try {
				switch(choice) {
				case 1:
					addEmployee(scanner,repo);
					break;
				case 2:
					addProject(scanner,repo);
					break;
				case 3:
					addTask(scanner,repo);
					break;
				case 4:
					assignProjectToEmployee(scanner,repo);
					break;
				case 5:
					assignTaskToEmployee(scanner,repo);
					break;
				case 6:
					deleteEmployee(scanner,repo);
					break;
				case 7:
					deleteTask(scanner,repo);
					break;
				case 8:
					listEmployeeProjects(scanner,repo);
					break;
				case 9:
					System.out.println("Exiting...");
					scanner.close();
					return;
				default:
					System.out.println("Invalid choice! Try again...");
				}
			}
			catch(EmployeeNotFoundException | TaskNotFoundException e) {
				System.out.println("Error: " + ((Throwable) e).getMessage());
			}
		}
	}
	
	  private static void addEmployee(Scanner scanner, ProjectRepositoryImpl repo) {
	        System.out.print("Enter Employee Name: ");
	        scanner.nextLine();  // Consume newline
	        String name = scanner.nextLine();
	        System.out.print("Enter Designation: ");
	        String designation = scanner.nextLine();
	        System.out.print("Enter Gender: ");
	        String gender = scanner.nextLine();
	        System.out.print("Enter Salary: ");
	        double salary = scanner.nextDouble();
	        System.out.println("Enter ID of Project Assigned");
	        int projectId = scanner.nextInt();
	        Employee emp = new Employee(0, name, designation, gender, salary, projectId);
	        if (repo.createEmployee(emp)) {
	            System.out.println("Employee added successfully!");
	        } else {
	            System.out.println("Failed to add employee.");
	        }
	    }

	    private static void addProject(Scanner scanner, ProjectRepositoryImpl repo) {
	        System.out.print("Enter Project Name: ");
	        scanner.nextLine();  
	        String projectName = scanner.nextLine();
	        System.out.print("Enter Project Description: ");
	        String description = scanner.nextLine();
	        System.out.print("Enter Start Date (YYYY-MM-DD): ");
	        LocalDate startDate = LocalDate.parse(scanner.nextLine());
	        System.out.print("Enter Status (started/dev/build/test/deployed): ");
	        String status = scanner.nextLine();

	        Project project = new Project(0, projectName, description, startDate, status);
	        if (repo.createProject(project)) {
	            System.out.println("Project added successfully!");
	        } else {
	            System.out.println("Failed to add project.");
	        }
	    }

	    private static void addTask(Scanner scanner, ProjectRepositoryImpl repo) {
	        System.out.print("Enter Task Name: ");
	        scanner.nextLine();  
	        String taskName = scanner.nextLine();
	        System.out.print("Enter Project ID: ");
	        int projectId = scanner.nextInt();
	        System.out.print("Enter Employee ID: ");
	        int employeeId = scanner.nextInt();
	        System.out.print("Enter Task Status (Assigned/Started/Completed): ");
	        scanner.nextLine();
	        String status = scanner.nextLine();

	        Task task = new Task(0, taskName, projectId, employeeId, status);
	        if (repo.createTask(task)) {
	            System.out.println("Task added successfully!");
	        } else {
	            System.out.println("Failed to add task.");
	        }
	    }

	    private static void assignProjectToEmployee(Scanner scanner, ProjectRepositoryImpl repo) {
	        System.out.print("Enter Project ID: ");
	        int projectId = scanner.nextInt();
	        System.out.print("Enter Employee ID: ");
	        int employeeId = scanner.nextInt();

	        if (repo.assignProjectToEmployee(projectId, employeeId)) {
	            System.out.println("Project assigned to employee successfully!");
	        } else {
	            System.out.println("Failed to assign project.");
	        }
	    }

	    private static void assignTaskToEmployee(Scanner scanner, ProjectRepositoryImpl repo) {
	        System.out.print("Enter Task ID: ");
	        int taskId = scanner.nextInt();
	        System.out.print("Enter Project ID: ");
	        int projectId = scanner.nextInt();
	        System.out.print("Enter Employee ID: ");
	        int employeeId = scanner.nextInt();

	        if (repo.assignTaskInProjectToEmployee(taskId, projectId, employeeId)) {
	            System.out.println("Task assigned successfully!");
	        } else {
	            System.out.println("Failed to assign task.");
	        }
	    }

	    private static void deleteEmployee(Scanner scanner, ProjectRepositoryImpl repo) throws EmployeeNotFoundException {
	        System.out.print("Enter Employee ID to delete: ");
	        int empId = scanner.nextInt();
	        if (repo.deleteEmployee(empId)) {
	            System.out.println("Employee deleted successfully.");
	        } else {
	            System.out.println("Failed to delete employee.");
	        }
	    }

	    private static void deleteTask (Scanner scanner, ProjectRepositoryImpl repo) throws TaskNotFoundException{
	        System.out.print("Enter Task ID to delete: ");
	        int taskId = scanner.nextInt();  // Variable is declared as taskId
	        if (repo.deleteTask(taskId)) {   // Use taskId (not task_id)
	            System.out.println("Task deleted successfully.");
	        } else {
	            System.out.println("Failed to delete task.");
	        }
	    }


	    private static void listEmployeeProjects(Scanner scanner, ProjectRepositoryImpl repo) {
	        System.out.print("Enter Employee ID: ");
	        int empId = scanner.nextInt();
	        System.out.print("Enter Project ID: ");
	        int projectId = scanner.nextInt();

	        System.out.println("Tasks Assigned to Employee in the Project:");
	        repo.getAllTasks(empId, projectId).forEach(System.out::println);
	    }
	
}
