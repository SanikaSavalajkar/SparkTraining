package dao;
import entity.Employee;
import entity.ExpenseReport;
import entity.Task;
import entity.Project;
import exception.EmployeeNotFoundException;
import exception.ProjectNotFoundException;
import exception.TaskNotFoundException;
import util.DBConnUtil;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepositoryImpl implements IProjectRepository {

	private Connection conn;

    public ProjectRepositoryImpl() {
        this.conn = DBConnUtil.getDbConnection();
        System.out.println("DB connection in constructor: " + this.conn);
    }

    @Override
    public boolean createEmployee(Employee emp) {
        String sql = "INSERT INTO employee(employee_name, employee_designation, employee_gender, employee_salary, project_id) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emp.getEmployeeName());
            stmt.setString(2, emp.getEmployeeDesignation());
            stmt.setString(3, emp.getEmployeeGender());
            stmt.setDouble(4, emp.getEmployeeSalary()); 
            stmt.setInt(5, emp.getProjectId()); 

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean createProject(Project pj) {
        String sql = "INSERT INTO project(project_name, project_description, start_date, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pj.getProjectName());
            stmt.setString(2, pj.getDescription());
            stmt.setDate(3, java.sql.Date.valueOf(pj.getStartDate()));
            stmt.setString(4, pj.getStatus());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean createTask(Task tk) {
    	String sql = "INSERT INTO task(task_name, project_id, employee_id, status, allocation_date, deadline) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setString(1, tk.getTaskName());
        	stmt.setInt(2, tk.getProjectId());
        	stmt.setInt(3, tk.getEmployeeId());
        	stmt.setString(4, tk.getStatus());
        	stmt.setDate(5, Date.valueOf(tk.getAllocationDate()));
        	stmt.setDate(6, Date.valueOf(tk.getDeadline()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean assignProjectToEmployee(int project_id, int employee_id) {
        String sql = "UPDATE employee SET project_id = ? WHERE employee_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, project_id);
            stmt.setInt(2, employee_id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean assignTaskInProjectToEmployee(int task_id, int project_id, int employee_id) {
        String sql = "UPDATE task SET project_id = ?, employee_id = ? WHERE task_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, project_id);
            stmt.setInt(2, employee_id);
            stmt.setInt(3, task_id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteEmployee(int employee_id) throws EmployeeNotFoundException {
        String checkSql = "SELECT employee_id FROM employee WHERE employee_id = ?";
        String deleteExpensesSql = "DELETE FROM expense WHERE employee_id = ?";
        String deleteTasksSql = "DELETE FROM task WHERE employee_id = ?";
        String deleteEmployeeSql = "DELETE FROM employee WHERE employee_id = ?";

        try (Connection con = DBConnUtil.getDbConnection()) {
            // Check if employee exists
            try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
                checkStmt.setInt(1, employee_id);
                ResultSet rs = checkStmt.executeQuery();
                if (!rs.next()) {
                    throw new EmployeeNotFoundException("Employee with ID " + employee_id + " not found.");
                }
            }

            // ✅ Delete expenses first
            try (PreparedStatement deleteExpensesStmt = con.prepareStatement(deleteExpensesSql)) {
                deleteExpensesStmt.setInt(1, employee_id);
                deleteExpensesStmt.executeUpdate();
            }

            // Delete associated tasks
            try (PreparedStatement deleteTasksStmt = con.prepareStatement(deleteTasksSql)) {
                deleteTasksStmt.setInt(1, employee_id);
                deleteTasksStmt.executeUpdate();
            }

            // Delete the employee
            try (PreparedStatement deleteEmployeeStmt = con.prepareStatement(deleteEmployeeSql)) {
                deleteEmployeeStmt.setInt(1, employee_id);
                return deleteEmployeeStmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean deleteProject(int project_id) throws ProjectNotFoundException {
        String checkSql = "SELECT project_name FROM project WHERE project_id = ?";
        String deleteSql = "DELETE FROM project WHERE project_id = ?";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, project_id);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                throw new ProjectNotFoundException("Project with id " + project_id + " not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
            deleteStmt.setInt(1, project_id);
            return deleteStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    
    @Override
    public boolean deleteTask(int task_id) throws TaskNotFoundException{
        String checkSql = "SELECT task_name FROM task WHERE task_id = ?";
        String deleteSql = "DELETE FROM task WHERE task_id = ?";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, task_id);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                throw new TaskNotFoundException("Task with id " + task_id + " not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
            deleteStmt.setInt(1, task_id);
            return deleteStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    
    @Override
    public List<Task> getAllTasks(int employee_id, int project_id) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task WHERE employee_id = ? AND project_id = ?";

        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employee_id);
            stmt.setInt(2, project_id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add(new Task(
                        rs.getInt("task_id"),
                        rs.getString("task_name"),
                        rs.getInt("project_id"),
                        rs.getInt("employee_id"),
                        rs.getString("status"),
                        rs.getDate("allocation_date").toLocalDate(),
                        rs.getDate("deadline").toLocalDate()
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }
    
    public List<Project> getAllProjects() {
        List<Project> projectList = new ArrayList<>();
        String query = "SELECT * FROM Project";

        try (Connection con = DBConnUtil.getDbConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Project p = new Project(
                	    rs.getInt("project_id"),
                	    rs.getString("project_name"),
                	    rs.getString("project_description"),
                	    rs.getDate("start_date").toLocalDate(),
                	    rs.getString("status")
                	);
                projectList.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projectList;
    }

	@Override
	public List<Employee> getAllEmployees() {
	    List<Employee> employeeList = new ArrayList<>();
	    String query = "SELECT * FROM employee";

	    try (Connection con = DBConnUtil.getDbConnection();
	         Statement stmt = con.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {

	        while (rs.next()) {
	            Employee emp = new Employee(
	                rs.getInt("employeeId"),
	                rs.getString("employeeName"),
	                rs.getString("role"),
	                rs.getString("gender"),
	                rs.getDouble("salary"),
	                rs.getInt("projectId")
	            );
	            employeeList.add(emp);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return employeeList;
	}
	
	public Project getProjectByNameAndDate(String name, LocalDate date) {
	    String query = "SELECT * FROM project WHERE project_name = ? AND start_date = ? ORDER BY project_id DESC LIMIT 1";
	    try (Connection conn = DBConnUtil.getDbConnection();
	         PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setString(1, name);
	        ps.setDate(2, Date.valueOf(date));
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            Project p = new Project();
	            p.setProjectId(rs.getInt("project_id"));
	            p.setProjectName(rs.getString("project_name"));
	            p.setDescription(rs.getString("project_description"));
	            p.setStartDate(rs.getDate("start_date").toLocalDate());
	            p.setStatus(rs.getString("status"));
	            return p;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	public Employee getEmployeeByNameAndProject(String name, int projectId) {
	    String query = "SELECT * FROM employee WHERE employee_name = ? AND project_id = ? ORDER BY employee_id DESC LIMIT 1";
	    try (Connection conn = DBConnUtil.getDbConnection();
	         PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setString(1, name);
	        ps.setInt(2, projectId);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return new Employee(
	                rs.getInt("employee_id"),
	                rs.getString("employee_name"),
	                rs.getString("employee_designation"),
	                rs.getString("employee_gender"),
	                rs.getFloat("employee_salary"),
	                rs.getInt("project_id")
	            );
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	@Override
	public boolean updateProject(Project pj) throws ProjectNotFoundException {
	    String checkSql = "SELECT * FROM project WHERE project_id = ?";
	    String updateSql = "UPDATE project SET project_name = ?, project_description = ?, start_date = ?, status = ? WHERE project_id = ?";

	    try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
	        checkStmt.setInt(1, pj.getProjectId());
	        ResultSet rs = checkStmt.executeQuery();
	        if (!rs.next()) {
	            throw new ProjectNotFoundException("Project with ID " + pj.getProjectId() + " not found.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
	        updateStmt.setString(1, pj.getProjectName());
	        updateStmt.setString(2, pj.getDescription());
	        updateStmt.setDate(3, Date.valueOf(pj.getStartDate()));
	        updateStmt.setString(4, pj.getStatus());
	        updateStmt.setInt(5, pj.getProjectId());

	        return updateStmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	
	@Override
	public List<ExpenseReport> generateExpenseReport(LocalDate start, LocalDate end) {
	    List<ExpenseReport> reports = new ArrayList<>();
	    String sql = "SELECT employee_id, amount, expense_date FROM expense WHERE expense_date BETWEEN ? AND ?";

	    try (Connection conn = DBConnUtil.getDbConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setDate(1, java.sql.Date.valueOf(start));
	        ps.setDate(2, java.sql.Date.valueOf(end));

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                reports.add(new ExpenseReport(
	                    rs.getInt("employee_id"),
	                    rs.getDouble("amount"),
	                    rs.getDate("expense_date").toLocalDate()
	                ));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return reports;
	}
}