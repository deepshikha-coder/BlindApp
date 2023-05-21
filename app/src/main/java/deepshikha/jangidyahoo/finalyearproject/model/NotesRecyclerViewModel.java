package deepshikha.jangidyahoo.finalyearproject.model;

public class NotesRecyclerViewModel {

    private String notesTitle;
    private String notesBody;

    public NotesRecyclerViewModel(String notesTitle, String notesBody){
        this.notesTitle = notesTitle;
        this.notesBody = notesBody;
    }

    public String getNotesTitle() {return notesTitle;}

    public void setNotesTitle(String notesTitle) {
        this.notesTitle = notesTitle;
    }

    public String getNotesBody() {return notesBody;}

    public void setNotesBody(String notesBody) {
        this.notesBody = notesBody;
    }

}
