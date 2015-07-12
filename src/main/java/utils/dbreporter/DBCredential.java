package utils.dbreporter;

public class DBCredential {

    public DBCredential(String URL, String login, String password) {
        this.login = login;
        this.password = password;
        this.URL = URL;
    }

    public String getLogin() {
        return this.login;
    }
    public String getPassword() {
        return this.password;
    }
    public String getConnectionURL() {
        return this.URL;
    }
    @Override public String toString() {
        return "Credential: {Database login: " + this.login + "; Database password: password: " + this.password + "; Database URL: " + this.URL + ";}";
    }

    private String login;
    private String password;
    private String URL;

}
