package event_management.event_management;
import org.springframework.web.multipart.MultipartFile;
import java.sql.Time;
public class Invitation {
    // private String event_reference_no;
    private String name;
    private String time;
    private MultipartFile cheif_guest_image;

 



    // public String getEvent_reference_no() {
    //     return this.event_reference_no;
    // }

    // public void setEvent_reference_no(String event_reference_no) {
    //     this.event_reference_no = event_reference_no;
    // }

   
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time= time;
    }
    public MultipartFile getCheif_guest_image() {
        return this.cheif_guest_image;
    }
    public void setCheif_guest_image(MultipartFile cheif_guest_image) {
        this.cheif_guest_image = cheif_guest_image;
    }

 


}
