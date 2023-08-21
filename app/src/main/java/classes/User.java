package classes;

import java.io.Serializable;

public class User implements Serializable {
    private int document;
    private String user;
    private String names;
    private String lastNames;
    private String pass;
    private int status = 1;

    public User() {
        this.document = document;
        this.user = user;
        this.names = names;
        this.lastNames = lastNames;
        this.pass = pass;
        this.status = status;
    }

    public int getDocument() {
        return document;
    }

    public void setDocument(int document) {
        this.document = document;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Documento: " + getDocument() +
                ", Nombre: " + getNames() +
                ", Apellido: " + getLastNames() +
                ", Usuario: " + getUser();
    }

}

