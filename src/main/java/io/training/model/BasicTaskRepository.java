package io.training.model;

//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.data.rest.core.annotation.RestResource;
//
//import java.util.List;

// RepositoryRestResource służy głównie do niewielkich zmian w istniejącym data rest repository (JPA) i raczej służy bardzo powierzchownym zmianom
// jest to dobry przykład na start, jednak bardziej skomplikowane aplikacje raczej używają @Repository stąd kod rozwijany początkowo w tej klasie
// został przeniesiony i zmodyfikowany do TaskRepository i @Repository



//udostępniamy na zewnątrz wszystkie metody z JpaRepository
//dodatkowymi parametrami @RepositoryRestResource() są np: path - URI pod jakim zapytanie rest jest dostępne
//                                                         collectionResourceRel - nazwa zwróconej kolekcji wyświetlana w response
//@RepositoryRestResource
//public interface BasicTaskRepository extends JpaRepository<Task, Integer> { // rozszerza JpaRepository po danej encji Task i typie identyfikatora encji -> Id
//
//
//    @Override
//    @RestResource(exported = false) // metoda nadpisana w celu eliminacji tego endpointa z usług rest - deleteById - nie można skasować taska po id
//    void deleteById(Integer integer);
//
//    @Override
//    @RestResource(exported = false) // metoda nadpisana w celu eliminacji tego endpointa z usług rest - delete - nie można skasować taska
//    void delete(Task entity);
//
//    @RestResource(path = "done", rel = "done") // path ustawia uri na url/done, rel zmienia nazwę zwróconej kolekcji na "done"
//    List<Task> findByDone(@Param("state") boolean done);// w naszym requescie parametr done będzie sie nazywał state czyli localhost:8080/tasks/search/done
//}