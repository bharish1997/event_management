package event_management.event_management;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

import org.dom4j.dom.DOMNodeHelper.EmptyNodeList;
import org.jfree.util.ObjectList;
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
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
    public RedirectView Submit_budget(@ModelAttribute("budgetList") Budget budgets){
        // System.out.println(budgets);
        //  this.Current_Budget=budget;
        //  this.Current_Budget.setEvent_reference_no(this.Current_Event.getEvent_reference_no());
        //  eventrepository.create_event(Current_Event);
        //  guestrepository.create_guests(Current_Guest);
        //  budgetrepository.create_budgets(Current_Budget);
         return new RedirectView("/");
       
    }
  
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public RedirectView delete_event(@RequestParam(name="event_id") String event_id){
        eventrepository.delete_event(event_id);
        return new RedirectView("/");
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

    @RequestMapping("/download") 
    public ResponseEntity<byte[]> download(@RequestParam(name="event_id") String event_id){ 
    try{ 
        String filepath= ResourceUtils.getFile("classpath:event_pdf.jrxml").getAbsolutePath();
        
    //   User user1=new User();
    //   user1.setId(1);
    //   user1.setEmail("check@gmail.com");
    //   user1.setName("test");

    //   JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(list);
        // List<Event> event=new ArrayList<Event>();
        // event.addAll(eventrepository.findAll());
        // System.out.println();
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

      headers.setContentDispositionFormData("filename", "report.pdf");

      return new ResponseEntity<byte[]>(byteArray, headers, HttpStatus.OK);

    }
    catch(Exception e){
        System.out.println("Exception while creating report");
        return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
    
    
}
