package deepshikha.jangidyahoo.finalyearproject.model;

public class CallLogItem {
    private String name;
    private String phoneNumber;
    private String date;
    private String type;
    private String time;


    public CallLogItem(String name, String type ,String phoneNumber, String date, String Time) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.type = type;
        this.time = Time;
    }
    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }
}
