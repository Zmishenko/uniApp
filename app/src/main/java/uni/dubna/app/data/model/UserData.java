package uni.dubna.app.data.model;

import com.google.gson.annotations.SerializedName;

public class UserData {
    private String login;
    private String password;
    private Role role;

    @SerializedName
            ("mail")
    private String email;

    @SerializedName("first_name")
    private String firstName;

    private String surname;

    @SerializedName("fathers_name")
    private String patronymic;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Role getRole() {
        return role;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserData(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public UserData() {}


    public String getFullName() {
        return firstName + " " + surname + " " + patronymic;
    }
}
