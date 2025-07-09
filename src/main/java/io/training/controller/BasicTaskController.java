package io.training.controller;

//import io.training.model.Task;
//import io.training.model.BasicTaskRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.rest.webmvc.RepositoryRestController;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;

// RepositoryRestController służy nam do obsługi podstawowego BasicTaskRepository, które służyło do pierwszych kroków szkolenia i raczej
// polegało na powierzchownych zmianach istniejacego Data Rest Repository

//@RepositoryRestController
//public class BasicTaskController {
//    private static final Logger logger = LoggerFactory.getLogger(BasicTaskController.class);
//    private final BasicTaskRepository repository;
//
//    public BasicTaskController(final BasicTaskRepository repository) {
//        this.repository = repository;
//    }
//
//    //@RequestMapping(method = RequestMethod.GET, path = "/tasks") // mówi springowi o nadpisaniu mapowania oryginalnego requesta findAll() - GET /tasks
//    //Przy naszym controlerze tracimy otoczkę HATEOAS, tracimy również wcześniejszą możliwość sortowania i paging
//    //Custom controller eliminuje możliwość automatu dla sort/paging/size więc eliminujemy te requesty z naszego controlera, a odwołanie do nich odeśle nas do wbudowanego controlera z JPA - chyba że mamy kontroler obsługujący pageable i sort
//    @GetMapping(value = "/tasks", params = {"!sort", "!paging", "!size"}) // tu forma RequestMapping dedykowana dla requesta GET
//    //ResponseEntity<?> readAllTasks() { // znak zapytania obsłuży nam wszysko zwracane, ale readAllTasks() powinien zwracać listę tasków czyli List<Task>
//    ResponseEntity<List<Task>> readAllTasks() {// @ResponseEntity - abstrakcja reprezentująca odpowiedź (np można ustawić status code 201)
//        logger.warn("Exposing all the tasks!");// w ten sposób do istniejącego requesta TestRepository można dodać np loggera
//        return ResponseEntity.ok(repository.findAll());// tu zwracamy wywołanie istniejącego TestRepository.findAll()
//    }
//    // ta sama metoda ale użyta z obiektem pageable, który pozwoli stronicować i sortować - tu spring próbuje podać nam dodatkowe metadane jak HATEOAS
//    @GetMapping(value = "/tasks")
//    //ResponseEntity<?> readAllTasks(Pageable page) { // tu też ? zwracał nam wszystko, jednak my tu dokładniej
//    // możem użyć ResponseEntity<Page<Task>> i wtedy ładować strony (page) tasków
//    // jeśli jednak chcemy zwracać List<Task>, musimy użyć metody getContent(), wziętej z interfejsu Slice<T> który jest rozszerzeniem dla interfejsu Page<T>
//    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
//        logger.info("Custom pageable");
//        return ResponseEntity.ok(repository.findAll(page).getContent()); // nasze TaskRepritory dziedziczy z JPARepository, a to z PagingAndSortingRepository gdzie jest metoda findAll(Pageable)
//    }
//}
