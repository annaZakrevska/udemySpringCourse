package github.udemySpringCourse.todoapp.logic;

import github.udemySpringCourse.todoapp.TaskConfigurationProperties;
import github.udemySpringCourse.todoapp.model.TaskGroup;
import github.udemySpringCourse.todoapp.model.TaskGroupRepository;
import github.udemySpringCourse.todoapp.model.TaskRepository;
import github.udemySpringCourse.todoapp.model.projection.GroupReadModel;
import github.udemySpringCourse.todoapp.model.projection.GroupTaskWriteModel;
import github.udemySpringCourse.todoapp.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class TaskGroupService {

    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    public TaskGroupService(final TaskGroupRepository repository, final TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source) {
        TaskGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll() {
        return repository.findAll()
                .stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId) {
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)) {
            throw new IllegalStateException("Group has undone tasks. Done all the tasks first");
        }
        TaskGroup result = repository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Task group with given ID not found"));
        result.setDone(!result.isDone());
    }
}
