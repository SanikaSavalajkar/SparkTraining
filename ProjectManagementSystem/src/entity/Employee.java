package entity;

public class Employee {
    private int employee_id;
    private String employee_name;
    private String employee_designation;
    private String employee_gender;
    private double employee_salary;
    private int project_id;

    // Default constructor
    public Employee() {
    }

    // Parameterized constructor
    public Employee(int employee_id, String employee_name, String employee_designation, String employee_gender, double employee_salary, int project_id) {
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.employee_designation = employee_designation;
        this.employee_gender = employee_gender;
        this.employee_salary = employee_salary;
        this.project_id = project_id;
    }

    // Getter and setter methods
    public int getEmployeeId() {
        return employee_id;
    }

    public void setEmployeeId(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployeeName() {
        return employee_name;
    }

    public void setEmployeeName(String employeeName) {
        this.employee_name = employee_name;
    }

    public String getEmployeeDesignation() {
        return employee_designation;
    }

    public void setEmployeeDesignation(String employeeDesignation) {
        this.employee_designation = employee_designation;
    }

    public String getEmployeeGender() {
        return employee_gender;
    }

    public void setEmployeeGender(String employeeGender) {
        this.employee_gender = employee_gender;
    }

    public double getEmployeeSalary() {
        return employee_salary;
    }

    public void setEmployeeSalary(double employeeSalary) {
        this.employee_salary = employee_salary;
    }

    public int getProjectId() {
        return project_id;
    }

    public void setProjectId(int project_id) {
        this.project_id = project_id;
    }
}