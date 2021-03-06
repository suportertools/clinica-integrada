package br.com.clinicaintegrada.utils;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class Polling {
    
    private boolean stop = false;

    public void existeUsuarioSessao() throws IOException {
        if (!Sessions.exists("sessaoUsuario")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Sindical/indexLogin.jsf");
        }
    }
    
    public void habilita() {
        stop = Sessions.exists("sessaoUsuario");
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

}
