package pl.coderslab.entity;

import pl.coderslab.service.BCrypt;
import pl.coderslab.service.DbService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private User(int id, String username, String email, String password, UserGroup userGroup) {
        setId(id);
        setUsername(username);
        setEmail(email);
        this.password = password;
        setUserGroup(userGroup);
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
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

    public void save() throws SQLException {
        if (getId() == 0) {
            setId(insert());
        } else {
            update();
        }
    }

    public int delete() throws SQLException {
        if (getId() != 0) {
            String query = "DELETE FROM users WHERE id = ?";
            List<String> params = new ArrayList<>();
            params.add(String.valueOf(getId()));
            int result = DbService.executeQuery(query, params);
            setId(0);
            return result;
        }
        return 0;
    }

    public static List<User> loadAll() throws Exception {
        String query = "SELECT id, username, email, password, user_group_id FROM users";
        List<String[]> list = DbService.getData(query, null);
        List<User> listUsers = new ArrayList<>();
        for (String[] tab : list) {
            int id = Integer.parseInt(tab[0]);
            String name = tab[1];
            String email = tab[2];
            String password = tab[3];
            int userGroupId = Integer.parseInt(tab[4]);
            UserGroup userGroup = UserGroup.loadById(userGroupId);
            User user = new User(id, name, email, password, userGroup);
            listUsers.add(user);
        }

        return listUsers;
    }

    public static User loadById(int userId) throws SQLException {
        String query = "SELECT id, username, email, password, user_group_id FROM users WHERE id = ?";
        List<String> params = new ArrayList<>();
        params.add(String.valueOf(userId));
        List<String[]> list = DbService.getData(query, params);
        if (list.isEmpty()) {
            return null;
        } else {
            String[] tab = list.get(0);
            int id = Integer.parseInt(tab[0]);
            String name = tab[1];
            String email = tab[2];
            String password = tab[3];
            int userGroupId = Integer.parseInt(tab[4]);
            UserGroup userGroup = UserGroup.loadById(userGroupId);
            return new User(id, name, email, password, userGroup);
        }
    }

    public static int deleteAll() throws SQLException {
        String query = "DELETE FROM users";
        return DbService.executeQuery(query, null);
    }

    private int insert() throws SQLException {
        String query = "INSERT INTO users VALUE(null, ?, ?, ?, ? )";
        List<String> params = new ArrayList<>();
        params.add(getUsername());
        params.add(getEmail());
        params.add(getPassword());
        params.add(String.valueOf(getUserGroup().getId()));
        return DbService.insertIntoDatabase(query, params);
    }

    private int update() throws SQLException {
        String query = "UPDATE users SET username = ?, email = ?, password = ?, use_group_id = ? WHERE id = ?";
        List<String> params = new ArrayList<>();
        params.add(getUsername());
        params.add(getEmail());
        params.add(getPassword());
        params.add(String.valueOf(getUserGroup().getId()));
        params.add(String.valueOf(getId()));
        return DbService.executeQuery(query, params);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userGroup=" + userGroup +
                '}';
    }

    public boolean checkPassword(String password){
        if (BCrypt.checkpw(password, getPassword())){
            return true;
        }
        return false;

    }

    public static void main(String[] args) {
        try {
            new User("Marek", "marek@gmail.com", "123456", UserGroup.loadById(5)).save();
            System.out.println(Arrays.toString(User.loadAll().toArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
