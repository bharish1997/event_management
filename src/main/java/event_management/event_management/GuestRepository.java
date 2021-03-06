package event_management.event_management;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
    public List<Guest> findById(String id){
        return jdbc.query("select * from guests where event_reference_no=?", 
                                            new Object[]{id},
                                            new BeanPropertyRowMapper<Guest>(Guest.class));
    }
     public int update_guest(Guest guest)
    {
        return jdbc.update("UPDATE guests set category=?, name=?, designation=?, organization=? where event_reference_no=?;",
                     new Object[]{guest.getCategory(),guest.getName(),
                    guest.getDesignation(),guest.getOrganization(),guest.getEvent_reference_no()});

    }
    public List<Guest> find_by_guest_name(String event_id,String name)
    {
        return jdbc.query("select * from guests where event_reference_no=? AND name=?",
                                                    new Object[]{event_id,name},
                                                    new BeanPropertyRowMapper<Guest>(Guest.class));
    }
  }
