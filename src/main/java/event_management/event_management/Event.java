package event_management.event_management;

import java.sql.Date;

public class Event {
    private String department_name;
    private Date applied_date;
    private String event_title;
    private String event_reference_no;
    private String event_type;
    private String level;
    private Integer no_of_days;
    private String iqac_aegis;
    private String sponsoring_agency;
    private String collaborators;
    private Date from;
    private Date to;
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

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
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

	public Date getFrom() {
		return this.from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return this.to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public String getVenue() {
		return this.venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

}