package entity;

import java.time.LocalDate;

public class Task {
    private int task_id;
    private String task_name;
    private int project_id;
    private int employee_id;
    private String status;
    private LocalDate allocationDate;
    private LocalDate deadline;

    public Task() {}

    public Task(int task_id, String task_name, int project_id, int employee_id, String status, LocalDate allocationDate, LocalDate deadline) {
        this.task_id = task_id;
        this.task_name = task_name;
        this.project_id = project_id;
        this.employee_id = employee_id;
        this.status = status;
        this.allocationDate = allocationDate;
        this.deadline = deadline;
    }

    public int getTaskId() { return task_id; }
    public void setTaskId(int taskId) { this.task_id = taskId; }

    public String getTaskName() { return task_name; }
    public void setTaskName(String taskName) { this.task_name = taskName; }

    public int getProjectId() { return project_id; }
    public void setProjectId(int projectId) { this.project_id = projectId; }

    public int getEmployeeId() { return employee_id; }
    public void setEmployeeId(int employeeId) { this.employee_id = employeeId; }

    public String getStatus() { return status; }
    public void setStatus(String status) {
        if (status.equalsIgnoreCase("assigned") || status.equalsIgnoreCase("started") || status.equalsIgnoreCase("completed")) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid status. Must be: assigned/started/completed");
        }
    }

    public LocalDate getAllocationDate() { return allocationDate; }
    public void setAllocationDate(LocalDate allocationDate) { this.allocationDate = allocationDate; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    @Override
    public String toString() {
        return task_id + " - " + task_name + " - " + status + " | Start: " + allocationDate + ", Deadline: " + deadline;
    }
}