package event_management.event_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BudgetRepository {
    @Autowired
    JdbcTemplate jdbc;

    public int create_budgets(Budget budget){
        return jdbc.update("INSERT INTO budgets VALUES(?,?,?)",
            new Object[]{budget.getEvent_reference_no(),budget.getParticulars(),budget.getAmount()});
    }
}
