package beans;

import javax.faces.context.FacesContext;
import java.io.IOException;

public class LoginBean {
    private String login;

    public LoginBean(){}

    public void logout(){
        this.login = null;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
