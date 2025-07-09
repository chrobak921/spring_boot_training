package io.training.model;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

// Mówi że będzie obiektem osadzony w innym obiekcie (jego pola i metody)
@Embeddable
class Audit {
    private LocalDateTime createdOn; // jeśli jest cammelcase w nazwie variable to hibernate szuka w bazie kolumny created_on
    private LocalDateTime updatedOn; // jeśli jest cammelcase w nazwie variable to hibernate szuka w bazie kolumny created_on

    @PrePersist
    // adnotacja mówiąca że metoda sie wykona przed zapisem do bazy
    void prePersist() {
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    // adnotacja mówiąca że metoda sie wykona przed update'em do bazy
    void preUpdate() {
        updatedOn = LocalDateTime.now();
    }
}
