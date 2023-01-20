package github.udemySpringCourse.todoapp.adapter;

import github.udemySpringCourse.todoapp.model.Project;
import github.udemySpringCourse.todoapp.model.ProjectRepository;
import github.udemySpringCourse.todoapp.model.TaskGroup;
import github.udemySpringCourse.todoapp.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {
    @Override
    @Query("select distinct p from Project p join fetch p.steps")
    List<Project> findAll();

}
