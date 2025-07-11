package io.training.adapter;

import io.training.model.Task;
import io.training.model.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// SqlTaskRepository rozszerza TaskRepository i JpaRepository. To powoduje, że stworzony zostanie bean sqltaskrepository
// czyli jeden i ten sam obiekt dla SqlTaskRepository, TaskRepository, JpaRepository
// a to nam daje taki myk że możemy wstrzyknąć TaskRepository, jeśli SqlTaskRepository ma dostęp pakietowy i przysłonimy inne requesty z JpaRepository
@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> { // rozszerza JpaRepository po danej encji Task i typie identyfikatora encji -> Id
    // Pierwsza metoda wyciągania parametru następuje dla numeru podanego argumentu
//    @Override // nadpisujemy istniejącą metodę
//    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=?1 ") // mówi że query będzie czysto sql'owe, podajemy również samo query a param mówi że ma być to pierwszy argument (tu Intiger id)
//    boolean existsById(Integer id);
    // Druga metoda na wyciągnięcie parametru z argumentu z użyciem @Param
    @Override // nadpisujemy istniejącą metodę
    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=:id ") // mówi że query będzie czysto sql'owe, podajemy również samo query a param mówi że ma być to param o podanej nazwie
    boolean existsById(@Param("id") Integer id);

    @Override
    boolean existsByDoneIsFalseAndAndGroup_Id(Integer id);
}