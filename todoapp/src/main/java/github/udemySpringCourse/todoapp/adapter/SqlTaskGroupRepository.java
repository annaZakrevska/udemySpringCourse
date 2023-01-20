package github.udemySpringCourse.todoapp.adapter;

import github.udemySpringCourse.todoapp.model.TaskGroup;
import github.udemySpringCourse.todoapp.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
 interface SqlTaskGroupRepository extends TaskGroupRepository, JpaRepository<TaskGroup,Integer> {
 @Override
 @Query("select distinct g from TaskGroup g join fetch g.tasks")
 List<TaskGroup> findAll();

 @Override
 boolean existsByDoneIsFalseAndProject_Id(Integer groupId);
}
