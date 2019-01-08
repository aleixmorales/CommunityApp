package aleix.morales.communityapp;

import java.util.Date;

public class Resposta {

    private String text_resposta, user;
    private Date data;

    Resposta() {}

    public  Resposta( String text_resposta, String user, Date data){
        this.text_resposta = text_resposta;
        this.user = user;
        this.data = data;
    }

    public String getText_resposta() {
        return text_resposta;
    }

    public void setText_resposta(String text_resposta) {
        this.text_resposta = text_resposta;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }


}
