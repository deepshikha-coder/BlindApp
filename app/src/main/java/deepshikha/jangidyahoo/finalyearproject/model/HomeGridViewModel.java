package deepshikha.jangidyahoo.finalyearproject.model;

public class HomeGridViewModel {
    private String CardTitle;
    private int icon;

    public HomeGridViewModel(String CardTitle, int icon) {
        this.CardTitle = CardTitle;
        this.icon = icon;
    }

    public String getCourse_name() {
        return CardTitle;
    }

    public void setCourse_name(String CardTitle) {
        this.CardTitle = CardTitle;
    }

    public int getImgid() {
        return icon;
    }

    public void setImgid(int icon) {
        this.icon = icon;
    }
}
