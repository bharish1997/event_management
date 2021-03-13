package event_management.event_management;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EventRepository {

    @Autowired
    JdbcTemplate jdbc;

    public List<Event> findAll() {
       return jdbc.query("select * from events", 
                                                new BeanPropertyRowMapper<Event>(Event.class));
    }
    
    public List<Event> findById(String id){
        return jdbc.query("select * from events where event_reference_no=?", 
                                            new Object[]{id},
                                            new BeanPropertyRowMapper<Event>(Event.class));
    }

    public int create_event(Event event){
            return jdbc.update("INSERT INTO events VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);",
            new Object[]{event.getDepartment_name(),event.getApplied_date(),
            event.getEvent_reference_no(),event.getEvent_title(),event.getEvent_type(),event.getLevel(),
            event.getNo_of_days(),event.getIqac_aegis(),event.getSponsoring_agency(),event.getCollaborators(),event.getFrom(),event.getTo(),event.getVenue()});
    }

    public int delete_event(String event_id){
        return jdbc.update("DELETE FROM events WHERE event_reference_no=?;", new Object[]{event_id});
    }

}
