package io.training.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

//Jeden z przykładów jak można wynieść powtarzalne pola do klasy wyżej i współdzielić je - inny przykład to klasa Audit z @Embeddable
@MappedSuperclass  // klasa bazowa, która będzie pozwalała mapować powtarzalne pola do klas podrzędnych
abstract class BaseAuditableEntity {
    // Dla pól createdOn i updatedOn można by użyć @CreatedDate czy @LastModifiedDate jeśli włączymy @EnableJpaAuditing (zwykle w klasie konfiguracyjnej lub głównej @SpringBootApplication)
    // w tym przykładzie jednak używamy @PreCrete i @PreUpdate na metodach ustawiających te pola
    private LocalDateTime createdOn; // jeśli jest cammelcase w nazwie variable to hibernate szuka w bazie kolumny created_on
    private LocalDateTime updatedOn; // jeśli jest cammelcase w nazwie variable to hibernate szuka w bazie kolumny created_on

    @PrePersist// adnotacja mówiąca że metoda sie wykona przed zapisem do bazy
    void prePersist() {
        createdOn = LocalDateTime.now();
    }

    @PreUpdate // adnotacja mówiąca że metoda sie wykona przed update'em do bazy
    void preUpdate() {
        updatedOn = LocalDateTime.now();
    }
}
