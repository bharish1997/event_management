package event_management.event_management;

public class Guest {
    private String event_reference_no;
    private String category;
    private String name;
    private String designation;
    private String organization;
    private String about;

    public String getEvent_reference_no() {
        return this.event_reference_no;
    }

    public void setEvent_reference_no(String event_reference_no) {
        this.event_reference_no = event_reference_no;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOrganization() {
        return this.organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getAbout() {
		return this.about;
	}

	public void setAbout(String about) {
		this.about = about;
	}
    
}
