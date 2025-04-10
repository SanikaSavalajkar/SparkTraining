package entity;

public class Task {

	private int task_id;
	private String task_name;
	private int project_id;
	private int employee_id;
	private String status;
	
	//default constructor
	public Task() {
		
	}
	
	//parameterized constructor
	public Task(int task_id, String task_name, int project_id, int employee_id, String status)
	{
		this.task_id = task_id;
		this.task_name = task_name;
		this.project_id = project_id;
		this.employee_id = employee_id;
		this.status = status;
	}
	
	//getter and setter methods
	public int getTaskId() {
		return task_id;
	}
	public void setTakId(int task_id) {
		this.task_id = task_id;
	}
	
	public String getTaskName() {
		return task_name;
	}
	public void setTaskName(String task_name) {
		this.task_name = task_name;
	}
	
	public int getProjectId() {
		return project_id;
	}
	public void setProjectId(int project_id) {
		this.project_id = project_id;
	}
	
	public int getEmployeeId() {
		return employee_id;
	}
	public void setEmployeeId(int employee_id) {
		this.employee_id = employee_id;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		if(status.equalsIgnoreCase("assigned") || status.equalsIgnoreCase("started") || status.equalsIgnoreCase("completed"))
		{
			this.status = status;
		}
		else {
			throw new IllegalArgumentException("Invalid status value! Must be: assigned, started, completed");
		}
	}
	
	@Override
	public String toString() {
	    return "Task ID: " + task_id +
	           ", Task Name: " + task_name +
	           ", Project ID: " + project_id +
	           ", Employee ID: " + employee_id +
	           ", Status: " + status;
	}
}