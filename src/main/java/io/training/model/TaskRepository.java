package io.training.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
// tu bean powstaje przez stworzenie beana dla SqlTaskrepository,a poniważ dodatkowo jest on typu TaskRepository oraz JpaRepository
// to gdy wstrzykniemy TaskRepository do controller, to Jpa stworzy nam metody dla SqlTastRepo, natomiast TaskRepo uszcupli je tylko do tych w TaskRepo
public interface TaskRepository {
    List<Task> findAll();

    Page<Task> findAll(Pageable page);

    Optional<Task> findById(Integer id);

    boolean existsByDoneIsFalseAndAndGroup_Id(Integer id);

    boolean existsById(Integer id);

    List<Task> findByDone(boolean done);

    Task save(Task entity);
}
