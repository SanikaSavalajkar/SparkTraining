package entity;
import java.time.LocalDate;

public class Project {
	private int project_id;
	private String project_name;
	private String project_description;
	private LocalDate startDate;
	private String status;
	
	//default constructor
	public Project() {
		
	}
	
	//parameterized constructor
	public Project(int project_id, String project_name, String project_description, LocalDate startDate, String status)
	{
		this.project_id = project_id;
		this.project_name = project_name;
		this.project_description = project_description;
		this.startDate = startDate;
		this.status = status;
	}
	
	//getters and setter methods
	public int getProjectId() {
		return project_id;
	}
	public void setProjectId(int project_id) {
		this.project_id = project_id;
	}
	
	public String getProjectName() {
		return project_name;
	}
	public void setProjectName(String project_name) {
		this.project_name = project_name;
	}
	
	public String getDescription() {
		return project_description;
	}
	public void setDescription(String project_description) {
		this.project_description = project_description;
	}
	
	public LocalDate getStartDate(){
		return startDate;
	}
	public void getStartDate(LocalDate startDate){
		this.startDate = startDate;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		if(status.equalsIgnoreCase("started") || status.equalsIgnoreCase("dev") || status.equalsIgnoreCase("build") ||
		   status.equalsIgnoreCase("test") || status.equalsIgnoreCase("deployed")) {
			this.status = status;
		}
		else {
			throw new IllegalArgumentException("Invalid status value! Must be: started, dev, build, test, deployed");
		}
	}

}