package aleix.morales.communityapp;

public class Fil {

    private String titol;
    private Integer likes;

    Fil() {}

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Fil(String titol, int likes){
        this.titol = titol;
        this.likes = likes;


    }

}
