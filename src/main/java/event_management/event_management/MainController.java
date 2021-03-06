package event_management.event_management;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.text.SimpleDateFormat; 
import java.text.DateFormat;   
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class MainController {
    @Autowired
    EventRepository eventrepository;
    @Autowired
    GuestRepository guestrepository;
    @Autowired
    BudgetRepository budgetrepository;
    @Autowired
    PhotoRespository photorespository;

    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    ResourcePatternResolver resolver;

    @Autowired
    ServletContext servletContext;

    Event Current_Event;
    Guest Current_Guest;
    Budget Current_Budget;

    @GetMapping("/")
    public String dashboard(Model model){
        model.addAttribute("events", eventrepository.findAll());
        return "index";
    }


    @GetMapping("/find")
    public String event_find(){
        return "find"; 
    }   

    @GetMapping("/find/{eventid}")
    public String event_found(@PathVariable String eventid,Model model){
        model.addAttribute("events", eventrepository.findById(eventid));
        return "index";
    }


    @GetMapping("/create")
    public String create_event(){
            return "event_details";
    }

    @PostMapping("/create")
    public RedirectView Submit_event(@ModelAttribute("events") Event event){
        this.Current_Event=event;
        return new RedirectView("/guest");
    }

    @GetMapping("/guest")
    public String create_guest(){
        return "guest_details";
    }

    @PostMapping("/guest")
    public RedirectView Submit_guest(@ModelAttribute("guests") Guest guest){
        this.Current_Guest=guest;
        this.Current_Guest.setEvent_reference_no(this.Current_Event.getEvent_reference_no());
        return new RedirectView("/budget");
    }

    @GetMapping("/budget")
    public String create_budget(){
        return "budget_details";
    }

    @PostMapping("/budget")
    public RedirectView Submit_budget(@ModelAttribute("budgetList") Budget budget){
         this.Current_Budget=budget;
         this.Current_Budget.setEvent_reference_no(this.Current_Event.getEvent_reference_no());
         eventrepository.create_event(Current_Event);
         guestrepository.create_guests(Current_Guest);
         budgetrepository.create_budgets(Current_Budget);
         File file = new File("C:/event_management/src/main/resources/static/photos/"+this.Current_Event.getEvent_reference_no());
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
         return new RedirectView("/");
    }
  
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public RedirectView delete_event(@RequestParam(name="event_id") String event_id){
        eventrepository.delete_event(event_id);
        return new RedirectView("/");
    }

    @PostMapping("/details")
    public String upload(@RequestParam(name="event_id") String event_id,Model model, @ModelAttribute("photos") MultipartFile[] files, MultipartFile imagefile) throws IOException{
        String uploadDirectory = "C:/event_management/src/main/resources/static/photos/" + event_id+ "/";
        StringBuilder fileNames = new StringBuilder();
        for(MultipartFile file: files){
            Path fileNameAndPath=Paths.get(uploadDirectory, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
            photorespository.insert_photos(event_id,"/photos/"+event_id+"/"+file.getOriginalFilename());
        }
        model.addAttribute("events", eventrepository.findById(event_id));
        model.addAttribute("guests", guestrepository.findById(event_id));
        model.addAttribute("budgets", budgetrepository.findById(event_id));
        model.addAttribute("photos", photorespository.findbyid(event_id));
        return "details";
    }

    @GetMapping("/details")
    public String details(@RequestParam(name="event_id") String event_id,Model model) throws IOException{
        model.addAttribute("events", eventrepository.findById(event_id));
        model.addAttribute("guests", guestrepository.findById(event_id));
        model.addAttribute("budgets", budgetrepository.findById(event_id));
        model.addAttribute("photos", photorespository.findbyid(event_id));
        return "details"; 
    }


    @GetMapping("/edit")
    public String update_event(@RequestParam(name="event_id") String event_id,Model model){
        model.addAttribute("events", eventrepository.findById(event_id));
        return "update_event_form";
    }


    @PostMapping("/update")
    public String  update_event(@ModelAttribute("events") Event event,Model model){
        this.Current_Event=event;
        model.addAttribute("guests",guestrepository.findById(this.Current_Event.getEvent_reference_no()));
        return "update_guest_form";
    }
 
    @PostMapping("/update_guest")
    public String update_guest(@ModelAttribute("guests") Guest guest,Model model){
        this.Current_Guest=guest;
        this.Current_Guest.setEvent_reference_no(this.Current_Event.getEvent_reference_no());
        model.addAttribute("budgets",budgetrepository.findById(this.Current_Event.getEvent_reference_no()));
        return "update_budget_form";
    }
    
    @PostMapping("/update_all")
    public RedirectView update_all(@ModelAttribute("budgets") Budget budget){
         this.Current_Budget=budget;
         this.Current_Budget.setEvent_reference_no(this.Current_Event.getEvent_reference_no());
         eventrepository.update_event(Current_Event);
         guestrepository.update_guest(Current_Guest);
         budgetrepository.update_budget(Current_Budget);
         return new RedirectView("/");
    }

    @PostMapping("/budget_update")
    public RedirectView update_budget(@ModelAttribute("budgets") Budget budget,@RequestParam(name="event_id") String event_id, Model model){
        budget.setEvent_reference_no(event_id);
        budgetrepository.create_budgets(budget);
        model.addAttribute("events", eventrepository.findById(event_id));
        model.addAttribute("guests", guestrepository.findById(event_id));
        model.addAttribute("budgets", budgetrepository.findById(event_id));
        model.addAttribute("photos", photorespository.findbyid(event_id));
        return new RedirectView("/details?event_id="+event_id);
    }

    @PostMapping("/guest_update")
    public RedirectView update_guest(@ModelAttribute("guests") Guest guest,@RequestParam(name="event_id") String event_id, Model model){
        guest.setEvent_reference_no(event_id);
        guestrepository.create_guests(guest);
        model.addAttribute("events", eventrepository.findById(event_id));
        model.addAttribute("guests", guestrepository.findById(event_id));
        model.addAttribute("budgets", budgetrepository.findById(event_id));
        model.addAttribute("photos", photorespository.findbyid(event_id));
        return new RedirectView("/details?event_id="+event_id);
    }

    @RequestMapping("/download") 
    public ResponseEntity<byte[]> download(@RequestParam(name="event_id") String event_id){ 
    try{ 
        String filepath= ResourceUtils.getFile("classpath:event_pdf.jrxml").getAbsolutePath();
      
        List<Event> events= new ArrayList<Event>(eventrepository.findById(event_id));
        for (Event event : events) {
            this.Current_Event=event;
        }
      JRDataSource dataSource= new JREmptyDataSource();
      Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("department_name", this.Current_Event.getDepartment_name());
        parameter.put("applied_date", this.Current_Event.getApplied_date());
        parameter.put("event_reference_no", this.Current_Event.getEvent_reference_no());
        parameter.put("event_type", this.Current_Event.getEvent_type());
        parameter.put("event_title", this.Current_Event.getEvent_title());
        parameter.put("iqac_aegis", this.Current_Event.getIqac_aegis());
        parameter.put("sponsering_agency", this.Current_Event.getSponsoring_agency());
        parameter.put("level", this.Current_Event.getEvent_level());
        parameter.put("collaborators", this.Current_Event.getCollaborators());
        parameter.put("no_of_days", this.Current_Event.getNo_of_days());
        parameter.put("venue", this.Current_Event.getVenue());
        parameter.put("from", this.Current_Event.getFrom_date());
        parameter.put("to", this.Current_Event.getTo_date());

      JasperReport report = JasperCompileManager.compileReport(filepath);

      JasperPrint print= JasperFillManager.fillReport(report, parameter, dataSource);

      byte[] byteArray= JasperExportManager.exportReportToPdf(print);

      HttpHeaders headers = new HttpHeaders();

      headers.setContentType(MediaType.APPLICATION_PDF);

      headers.setContentDispositionFormData("filename", "Event Report.pdf");

      return new ResponseEntity<byte[]>(byteArray, headers, HttpStatus.OK);

    }
    catch(Exception e){
        System.out.println("Exception while creating report");
        return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
    
  @RequestMapping("/budget_list")
  public ResponseEntity<byte[]> budget_list(@RequestParam(name="event_id") String event_id){ 
    try{ 
        String filepath= ResourceUtils.getFile("classpath:budget_pdf.jrxml").getAbsolutePath();
        List<Budget> budgets= new ArrayList<Budget>();
        
        budgets.addAll(budgetrepository.findById(event_id));

      JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(budgets);
      Map<String, Object> parameter = new HashMap<String, Object>();
      
      parameter.put("budgetlist",dataSource);
        
      JasperReport report = JasperCompileManager.compileReport(filepath);
      System.out.println("inside");
      JasperPrint print= JasperFillManager.fillReport(report, parameter, new JREmptyDataSource());
      System.out.println("out");
      byte[] byteArray= JasperExportManager.exportReportToPdf(print);

      HttpHeaders headers = new HttpHeaders();

      headers.setContentType(MediaType.APPLICATION_PDF);

      headers.setContentDispositionFormData("filename", "report.pdf");

      return new ResponseEntity<byte[]>(byteArray, headers, HttpStatus.OK);

    }
    catch(Exception e){
        System.out.println("Exception while creating report");
        return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping("/guest_list")
  public ResponseEntity<byte[]> guest_list(@RequestParam(name="event_id") String event_id){ 
    try{ 
        String filepath= ResourceUtils.getFile("classpath:guest_pdf.jrxml").getAbsolutePath();
        List<Guest> guests= new ArrayList<Guest>();
        
        guests.addAll(guestrepository.findById(event_id));

      JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(guests);
      Map<String, Object> parameter = new HashMap<String, Object>();
      
      parameter.put("guestlist",dataSource);
        
      JasperReport report = JasperCompileManager.compileReport(filepath);
      System.out.println("inside");
      JasperPrint print= JasperFillManager.fillReport(report, parameter, new JREmptyDataSource());
      System.out.println("out");
      byte[] byteArray= JasperExportManager.exportReportToPdf(print);

      HttpHeaders headers = new HttpHeaders();

      headers.setContentType(MediaType.APPLICATION_PDF);

      headers.setContentDispositionFormData("filename", "Guest Report.pdf");

      return new ResponseEntity<byte[]>(byteArray, headers, HttpStatus.OK);

    }
    catch(Exception e){
        System.out.println("Exception while creating report");
        return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/create_invitation")
  public ResponseEntity<byte[]>  create_invitation(@ModelAttribute("invitations") Invitation invitation,@RequestParam(name="department_name") String department_name,@RequestParam(name="venue") String venue,@RequestParam(name="event_title") String event_title,@RequestParam(name="from_date") String from_date,@RequestParam(name="event_id") String event_id,Model model){
        List<Guest> guest= new ArrayList<Guest>(guestrepository.find_by_guest_name(event_id,invitation.getName()));
        for(Guest g:guest)
        {
            this.Current_Guest=g;
        }

        try{ 
            DateFormat from_format = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat to_format = new SimpleDateFormat("d MMM, yyyy");
            String converted_date=to_format.format(from_format.parse(from_date));

            
            DateFormat from_time_format=new SimpleDateFormat("HH:mm");
            DateFormat to_time_format=new SimpleDateFormat("hh:mm a");
            String converted_time=to_time_format.format(from_time_format.parse(invitation.getTime()));

            String filepath= ResourceUtils.getFile("classpath:invitation.jrxml").getAbsolutePath();
            JRDataSource dataSource= new JREmptyDataSource();
            Map<String, Object> parameter = new HashMap<String, Object>();

            String uploadDirectory = "C:/event_management/src/main/resources/static/photos";
            MultipartFile file=invitation.getCheif_guest_image();
            StringBuilder fileNames = new StringBuilder();

            Path fileNameAndPath=Paths.get(uploadDirectory, invitation.getCheif_guest_image().getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());

            
            String filename = invitation.getCheif_guest_image().getOriginalFilename();
            String displayimage="C:/event_management/src/main/resources/static/photos/"+filename;
            parameter.put("department_name",department_name);
            parameter.put("event_name",event_title);
            parameter.put("guest_name",invitation.getName());
            parameter.put("event_name",event_title);
            parameter.put("designation",this.Current_Guest.getDesignation());
            parameter.put("organization",this.Current_Guest.getOrganization());
            parameter.put("date",converted_date);
            parameter.put("time",converted_time);
            parameter.put("venue",venue);
            parameter.put("photo",displayimage);

          JasperReport report = JasperCompileManager.compileReport(filepath);
    
          JasperPrint print= JasperFillManager.fillReport(report, parameter, dataSource);

          byte[] byteArray= JasperExportManager.exportReportToPdf(print);
    
          HttpHeaders headers = new HttpHeaders();
    
          headers.setContentType(MediaType.APPLICATION_PDF);
    
          headers.setContentDispositionFormData("filename", "invitation.pdf");

          File f=new File(displayimage);
          f.delete();
    
          return new ResponseEntity<byte[]>(byteArray, headers, HttpStatus.OK);
    
        }
        catch(Exception e){
            System.out.println("Exception while creating report");
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
  }


}
