package deepshikha.jangidyahoo.finalyearproject.model;

import java.sql.Time;
import java.util.Date;

public class messageModel {
    private String sender;
    private String content;
    private String  date;
    private String  time;



    public messageModel(String sender, String content, String date, String time) {
        this.sender = sender;
        this.content = content;
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

}
