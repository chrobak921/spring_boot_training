package io.training.adapter;

import io.training.model.TaskGroup;
import io.training.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// przenieśliśmy SqlTaskRepository i SqlTaskGroupRepository do modułu adapter, bo służą one do łączenia naszych zapytań z TaskRepository i TaskGroupRepository z JPA
@Repository
interface SqlTaskGroupRepository extends TaskGroupRepository, JpaRepository<TaskGroup, Integer> {
    @Override
    // Robimy zapytanie na HQL (Hibernate query language), czyli na encjach a nie na tabelach bazodanowych - nie używamy nativeQuery = true
    // Działa to podobnie do java - TaskGroup g - typ i nazwa obiektu oraz g.tasks - obiekt i nazwa pola
    // join fetch join fetch służy do ładowania powiązanych encji "z góry" (eagerly) w ramach jednego zapytania
    // co zapobiega problemowi N + 1 czyli ładowaniu taskGroups i potem kolejnemu ładowaniu tasks dla pojedyńczego taskGroup
    // Można by też ustawić w TaskGroup fetch = FetchType.EAGER ale wtedy wszystkie zapytania ładowały by wszystkie taski, a w np findById nam na tym nie zależy
    @Query("from TaskGroup g join fetch g.tasks") // można też 'select * from .. ' zapisać tylko jako 'from ...'
    List<TaskGroup> findAll();
}
