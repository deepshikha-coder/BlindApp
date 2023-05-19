package deepshikha.jangidyahoo.finalyearproject.model;

public class CallLogItem {
    private String name;
    private String phoneNumber;
    private String dateTime;

    public CallLogItem(String name, String phoneNumber, String dateTime) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.dateTime = dateTime;
    }
    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDateTime() {
        return dateTime;
    }

}
