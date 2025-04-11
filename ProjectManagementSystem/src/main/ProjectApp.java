package main;

import dao.ProjectRepositoryImpl;
import entity.Employee;
import entity.ExpenseReport;
import entity.Project;
import entity.Task;
import exception.EmployeeNotFoundException;
import exception.ProjectNotFoundException;
import exception.TaskNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ProjectApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProjectRepositoryImpl repo = new ProjectRepositoryImpl();

        while (true) {
            System.out.println("\n *****PROJECT MANAGEMENT SYSTEM*****");
            System.out.println("1. Add Employee");
            System.out.println("2. Add Project");
            System.out.println("3. Add Task");
            System.out.println("4. Assign Project to Employee");
            System.out.println("5. Assign Task within a Project to an Employee");
            System.out.println("6. Delete Employee");
            System.out.println("7. Delete Task");
            System.out.println("8. List all projects assigned with tasks to an employee");
            System.out.println("9. Update a Project");
            System.out.println("10. View All Projects");
            System.out.println("11. Generate Expense Report");
            System.out.println("12. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1 -> addEmployee(scanner, repo);
                    case 2 -> addProject(scanner, repo);
                    case 3 -> addTask(scanner, repo);
                    case 4 -> assignProjectToEmployee(scanner, repo);
                    case 5 -> assignTaskToEmployee(scanner, repo);
                    case 6 -> deleteEmployee(scanner, repo);
                    case 7 -> deleteTask(scanner, repo);
                    case 8 -> listEmployeeProjects(scanner, repo);
                    case 9 -> updateProject(scanner, repo);
                    case 10 -> viewAllProjects(repo);
                    case 11 -> generateExpenseReport(scanner, repo);
                    case 12 -> {
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice! Try again...");
                }
            } catch (EmployeeNotFoundException | TaskNotFoundException | ProjectNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addEmployee(Scanner scanner, ProjectRepositoryImpl repo) {
        System.out.print("Enter Employee Name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Enter Designation: ");
        String designation = scanner.nextLine();
        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter Salary: ");
        double salary = scanner.nextDouble();
        System.out.print("Enter ID of Project Assigned: ");
        int projectId = scanner.nextInt();

        Employee emp = new Employee(0, name, designation, gender, salary, projectId);
        System.out.println(repo.createEmployee(emp) ? "Employee added successfully!" : "Failed to add employee.");
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
        System.out.println(repo.createProject(project) ? "Project added successfully!" : "Failed to add project.");
    }

    private static void addTask(Scanner scanner, ProjectRepositoryImpl repo) {
        System.out.print("Enter Task Name: ");
        scanner.nextLine();
        String taskName = scanner.nextLine();
        System.out.print("Enter Project ID: ");
        int projectId = scanner.nextInt();
        System.out.print("Enter Employee ID: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine(); // flush
        System.out.print("Enter Task Status (Assigned/Started/Completed): ");
        String status = scanner.nextLine();
        System.out.print("Enter Allocation Date (YYYY-MM-DD): ");
        LocalDate allocationDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter Deadline Date (YYYY-MM-DD): ");
        LocalDate deadline = LocalDate.parse(scanner.nextLine());

        Task task = new Task(0, taskName, projectId, employeeId, status, allocationDate, deadline);
        System.out.println(repo.createTask(task) ? "Task added successfully!" : "Failed to add task.");
    }

    private static void assignProjectToEmployee(Scanner scanner, ProjectRepositoryImpl repo) {
        System.out.print("Enter Project ID: ");
        int projectId = scanner.nextInt();
        System.out.print("Enter Employee ID: ");
        int employeeId = scanner.nextInt();
        System.out.println(repo.assignProjectToEmployee(projectId, employeeId)
                ? "Project assigned to employee successfully!"
                : "Failed to assign project.");
    }

    private static void assignTaskToEmployee(Scanner scanner, ProjectRepositoryImpl repo) {
        System.out.print("Enter Task ID: ");
        int taskId = scanner.nextInt();
        System.out.print("Enter Project ID: ");
        int projectId = scanner.nextInt();
        System.out.print("Enter Employee ID: ");
        int employeeId = scanner.nextInt();
        System.out.println(repo.assignTaskInProjectToEmployee(taskId, projectId, employeeId)
                ? "Task assigned successfully!" : "Failed to assign task.");
    }

    private static void deleteEmployee(Scanner scanner, ProjectRepositoryImpl repo) throws EmployeeNotFoundException {
        System.out.print("Enter Employee ID to delete: ");
        int empId = scanner.nextInt();
        System.out.println(repo.deleteEmployee(empId) ? "Employee deleted successfully." : "Failed to delete employee.");
    }

    private static void deleteTask(Scanner scanner, ProjectRepositoryImpl repo) throws TaskNotFoundException {
        System.out.print("Enter Task ID to delete: ");
        int taskId = scanner.nextInt();
        System.out.println(repo.deleteTask(taskId) ? "Task deleted successfully." : "Failed to delete task.");
    }

    private static void listEmployeeProjects(Scanner scanner, ProjectRepositoryImpl repo) {
        System.out.print("Enter Employee ID: ");
        int empId = scanner.nextInt();
        System.out.print("Enter Project ID: ");
        int projectId = scanner.nextInt();
        System.out.println("Tasks Assigned to Employee in the Project:");
        repo.getAllTasks(empId, projectId).forEach(System.out::println);
    }

    private static void updateProject(Scanner scanner, ProjectRepositoryImpl repo) throws ProjectNotFoundException {
        System.out.print("Enter Project ID to update: ");
        int projectId = scanner.nextInt();
        scanner.nextLine();  // consume newline
        System.out.print("Enter New Project Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter New Description: ");
        String desc = scanner.nextLine();
        System.out.print("Enter New Start Date (YYYY-MM-DD): ");
        LocalDate start = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter New Status: ");
        String status = scanner.nextLine();

        Project p = new Project(projectId, name, desc, start, status);
        System.out.println(repo.updateProject(p) ? "Project updated." : "Failed to update project.");
    }

    private static void viewAllProjects(ProjectRepositoryImpl repo) {
        List<Project> projects = repo.getAllProjects();
        System.out.println("All Projects:");
        projects.forEach(p -> System.out.println(p.getProjectId() + " - " + p.getProjectName()));
    }

    private static void generateExpenseReport(Scanner scanner, ProjectRepositoryImpl repo) {
        System.out.print("Enter Start Date (YYYY-MM-DD): ");
        scanner.nextLine();
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter End Date (YYYY-MM-DD): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());
        List<ExpenseReport> total = repo.generateExpenseReport(startDate, endDate);
        System.out.println("Total Expenses from " + startDate + " to " + endDate + ": " + total);
    }
}
