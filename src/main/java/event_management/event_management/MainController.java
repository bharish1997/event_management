package event_management.event_management;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


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
    Budget Currency_Budget;

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
        // eventrepository.create_event(event);
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
    public RedirectView Submit_budget(@ModelAttribute("budgets") Budget budget){
        // this.Currency_Budget=budget;
        // this.Currency_Budget.setEvent_reference_no(this.Current_Event.getEvent_reference_no());
        // System.out.print(Current_Event.getEvent_reference_no());
        // System.out.print(Current_Guest.getEvent_reference_no());
        // System.out.println(Currency_Budget.getEvent_reference_no());
        System.out.println(budget.getParticulars());
        return new RedirectView("/");
    }
  
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public RedirectView delete_event(@RequestParam(name="event_id") String event_id){
        System.out.println(event_id);
        eventrepository.delete_event(event_id);
        return new RedirectView("/");
    }
    

}
