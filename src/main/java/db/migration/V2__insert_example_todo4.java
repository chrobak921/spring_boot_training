package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;


// Klasa do migracji bazy danych dla Flyway. Migrację można robić przez skrypty sql lub kod java
public class V2__insert_example_todo4 extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("insert into TASKS (description, done) values ('Learn java migration task', true)");
    }
}
