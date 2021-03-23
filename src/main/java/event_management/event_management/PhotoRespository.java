package event_management.event_management;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PhotoRespository {
    @Autowired
    JdbcTemplate jdbc;

    public List<Photo> findbyid(String id){
        return jdbc.query("select * from photos where event_reference_no=?", 
        new Object[]{id},
        new BeanPropertyRowMapper<Photo>(Photo.class));
    }

    public int insert_photos(String id,String fileNameAndPath){
        return jdbc.update("INSERT INTO photos VALUES(?,?)",
            new Object[]{id,fileNameAndPath});
    }
}
