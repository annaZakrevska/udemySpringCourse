package github.udemySpringCourse.todoapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "project_steps")
public class ProjectStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Task's description must not be empty")
    private String description;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private int daysToDeadline;

    public int getId() {
        return id;
    }

     void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

     void setDescription(String description) {
        this.description = description;
    }

     Project getProject() {
        return project;
    }

     void setProject(Project project) {
        this.project = project;
    }

    public int getDaysToDeadline() {
        return daysToDeadline;
    }

     void setDaysToDeadline(int daysToDeadline) {
        this.daysToDeadline = daysToDeadline;
    }
}
