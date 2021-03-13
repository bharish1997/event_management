package event_management.event_management;

public class Budget {
    private String event_reference_no;
    private String particulars;
    private Integer amount;

	public String getEvent_reference_no() {
		return this.event_reference_no;
	}

	public void setEvent_reference_no(String event_reference_no) {
		this.event_reference_no = event_reference_no;
	}

	public String getParticulars() {
		return this.particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
