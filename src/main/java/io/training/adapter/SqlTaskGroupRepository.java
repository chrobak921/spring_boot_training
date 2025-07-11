package io.training.adapter;

import io.training.model.TaskGroup;
import io.training.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// przenieśliśmy SqlTaskRepository i SqlTaskGroupRepository do modułu adapter, bo służą one do łączenia naszych zapytań z TaskRepository i TaskGroupRepository z JPA
@Repository
interface SqlTaskGroupRepository extends TaskGroupRepository, JpaRepository<TaskGroup, Integer> {
}
