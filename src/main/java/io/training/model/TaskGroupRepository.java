package io.training.model;

import java.util.List;
import java.util.Optional;

public interface TaskGroupRepository {
    boolean existsByDoneIsFalseAndProject_Id(Integer projectId);
    List<TaskGroup> findAll();

    Optional<TaskGroup> findById(Integer id);

    TaskGroup save(TaskGroup entity);

}
