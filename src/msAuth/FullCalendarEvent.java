package msAuth;

public class FullCalendarEvent {
	private String id;
	private String title;
	private String start;
	private String end;
	private boolean allDay;
	private String color;
	private String studentEmail;
	private String profEmail;
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getStart(){
		return start;
	}
	
	public void setStart(String start){
		this.start = start;
	}
	
	public String getEnd(){
		return end;
	}
	
	public void setEnd(String end){
		this.end = end;
	}
	
	public boolean getAllDay(){
		return allDay;
	}
	
	
	public void setAllDay(boolean allDay){
		this.allDay = allDay;
	}
	
	public String getColor(){
		return color;
	}
	
	public void setColor(String color){
		this.color = color;
	}
	
	public String getStudentEmail(){
		return studentEmail;
	}
	
	public void setStudentEmail(String studentEmail){
		this.studentEmail = studentEmail;
	}
	
	public String getProfEmail(){
		return profEmail;
	}
	
	public void setProfEmail(String profEmail){
		this.profEmail = profEmail;
	}
}
