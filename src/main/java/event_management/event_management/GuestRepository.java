package event_management.event_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GuestRepository {

    @Autowired
    JdbcTemplate jdbc;

    public int create_guests(Guest guest){
        return jdbc.update("INSERT INTO guests VALUES(?,?,?,?,?)",
            new Object[]{guest.getEvent_reference_no(),guest.getCategory(),guest.getName(),
                guest.getDesignation(),guest.getOrganization()});
    }
}
