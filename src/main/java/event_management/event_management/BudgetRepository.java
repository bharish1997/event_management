package event_management.event_management;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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


    public List<Budget> findById(String id){
        return jdbc.query("select * from budgets where event_reference_no=?", 
                                            new Object[]{id},
                                            new BeanPropertyRowMapper<Budget>(Budget.class));
     }

     public int update_budget(Budget budget)
    {
        return jdbc.update("UPDATE budgets set particulars=?,amount=?  where event_reference_no=? and particulars=?;",
                     new Object[]{budget.getParticulars(),budget.getAmount(),budget.getEvent_reference_no(),budget.getParticulars()
                  });
    }
}
