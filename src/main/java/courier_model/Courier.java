package courier_model;

private String login;
private String password;
private String firstName;
public class Courier {

    private final String login;
    private final String firstName;
    private String password;

    public Courier() {
        }

        public Courier(String login, String password, String firstName) {
            this.login = login;
            this.password = password;
            this.firstName = firstName;
        }

        public Courier(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public Courier(String password) {
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public void setLoginAndPassword(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
}
