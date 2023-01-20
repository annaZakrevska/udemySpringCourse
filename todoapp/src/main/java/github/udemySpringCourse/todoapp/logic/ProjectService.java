package github.udemySpringCourse.todoapp.logic;

import github.udemySpringCourse.todoapp.TaskConfigurationProperties;
import github.udemySpringCourse.todoapp.model.*;
import github.udemySpringCourse.todoapp.model.projection.GroupReadModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;

    public ProjectService(final ProjectRepository projectRepository,
                          final TaskGroupRepository taskGroupRepository,
                          final TaskConfigurationProperties config) {

        this.projectRepository = projectRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = config;
    }

    public List<Project> readAll() {
        return projectRepository.findAll();
    }

    public Project save(final Project toSave) {
        return projectRepository.save(toSave);
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId) {
        if (!config.getTemplate().isAllowMultipletasks()
                && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one undone group from project ia allowed");
        }
        TaskGroup result = projectRepository.findById(projectId)
                .map(project -> {
                    var res = new TaskGroup();
                    res.setDescription(project.getDescription());
                    res.setTasks
                            (project.getSteps().stream()
                                    .map(step -> new Task(
                                            step.getDescription(),
                                            deadline.plusDays(step.getDaysToDeadline())))
                                    .collect(Collectors.toSet()));
                    return res;


                }).orElseThrow(() -> new IllegalArgumentException("Project with given ID not found"));
        return new GroupReadModel(result);
    }
}
