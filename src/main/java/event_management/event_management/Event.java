package event_management.event_management;

import java.sql.Date;

public class Event {
    private String department_name;
    private Date applied_date;
    private String event_title;
    private String event_reference_no;
    private String event_type;
    private String event_level;
    private Integer no_of_days;
    private String iqac_aegis;
    private String sponsoring_agency;
    private String collaborators;
    private Date from_date;
    private Date to_date;
    private String venue;

	public String getDepartment_name() {
		return this.department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public Date getApplied_date() {
		return this.applied_date;
	}
        
    public void setApplied_date(Date applied_date) {
            this.applied_date = applied_date;
	}

	public String getEvent_title() {
		return this.event_title;
	}

	public void setEvent_title(String event_title) {
		this.event_title = event_title;
	}

	public String getEvent_reference_no() {
		return this.event_reference_no;
	}

	public void setEvent_reference_no(String event_reference_no) {
		this.event_reference_no = event_reference_no;
	}

	public String getEvent_type() {
		return this.event_type;
	}

	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}

	public String getEvent_level() {
		return this.event_level;
	}

	public void setEvent_level(String event_level) {
		this.event_level = event_level;
	}

	public Integer getNo_of_days() {
		return this.no_of_days;
	}

	public void setNo_of_days(Integer no_of_days) {
		this.no_of_days = no_of_days;
	}

	public String getIqac_aegis() {
		return this.iqac_aegis;
	}

	public void setIqac_aegis(String iqac_aegis) {
		this.iqac_aegis = iqac_aegis;
	}

	public String getSponsoring_agency() {
		return this.sponsoring_agency;
	}

	public void setSponsoring_agency(String sponsoring_agency) {
		this.sponsoring_agency = sponsoring_agency;
	}

	public String getCollaborators() {
		return this.collaborators;
	}

	public void setCollaborators(String collaborators) {
		this.collaborators = collaborators;
	}

	public Date getFrom_date() {
		return this.from_date;
	}

	public void setFrom_date(Date from_date) {
		this.from_date = from_date;
	}

	public Date getTo_date() {
		return this.to_date;
	}

	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}

	public String getVenue() {
		return this.venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

    boolean getParticulars() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}