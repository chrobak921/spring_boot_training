package io.training.logic;

import io.training.model.Task;
import io.training.model.TaskGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// przykladowy @Service
@Service
public class BasciSrvice {
    @Autowired // nie mamy konstruktora więc musimy użyć @Autowired aby wstrzyknąć beana TaskGroupRepository
    List<String> temp(TaskGroupRepository repository) {
        // FIXME: N + 1 -
        //  problem polega na tym, że TaskGroup ma dla Set<Task> ustawione fetch = FetchType.LAZY
        //  a to powoduje, że najpierw pobieramy selectem wszystkie taskGroup, a dopiero potem kolejnymi selectami
        //  pobieramy listę Task. Lepiej to zrobić za jednym zapytaniem
        return repository.findAll().stream()// tworzymy stream z wyniku findAll()
                .flatMap(taskGroup -> taskGroup.getTasks().stream())// spłaszczamy stream do pojedyńczych TaskGroup (zwykły map zwróciłby Stream<>)
                .map(Task::getDescription) // na każdym obiekcie task robimy getDescription()
                .collect(Collectors.toList()); // zwracamy jako List
    }
}
