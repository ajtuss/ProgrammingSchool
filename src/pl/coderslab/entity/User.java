package pl.coderslab.entity;

public class User {

    private int id;
    private String username;
    private String email;
    private String password;
    private UserGroup userGroup;

    public User() {
    }

    public User(String username, String email, String password, UserGroup userGroup) {
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setUserGroup(userGroup);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }
}
