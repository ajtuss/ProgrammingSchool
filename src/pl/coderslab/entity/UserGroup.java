package pl.coderslab.entity;

import pl.coderslab.service.DbService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserGroup {

    private int id;
    private String name;


    public UserGroup() {
    }

    public UserGroup(String name) {
        setName(name);
    }

    private UserGroup(int id, String name) {
        setId(id);
        setName(name);
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
            String query = "DELETE FROM user_group WHERE id = ?";
            List<String> params = new ArrayList<>();
            params.add(String.valueOf(getId()));
            int result = DbService.executeQuery(query, params);
            setId(0);
            return result;
        }
        return 0;
    }

    public static List<UserGroup> loadAll() throws Exception {
        String query = "SELECT id, name FROM user_group";
        List<String[]> list = DbService.getData(query, null);
        List<UserGroup> listUserGroup = new ArrayList<>();
        for (String[] tab : list) {
            int id = Integer.parseInt(tab[0]);
            String name = tab[1];
            UserGroup userGroup = new UserGroup(id, name);
            listUserGroup.add(userGroup);
        }

        return listUserGroup;
    }

    public static UserGroup loadById(int userGroupId) throws SQLException {
        String query = "SELECT id, name FROM user_group WHERE id = ?";
        List<String> params = new ArrayList<>();
        params.add(String.valueOf(userGroupId));
        List<String[]> list = DbService.getData(query, params);
        if (list.isEmpty()) {
            return null;
        } else {
            String[] tab = list.get(0);
            int id = Integer.parseInt(tab[0]);
            String name = tab[1];
            return new UserGroup(id, name);
        }
    }

    public static int deleteAll() throws SQLException {
        String query = "DELETE FROM user_group";
        return DbService.executeQuery(query, null);
    }

    private int insert() throws SQLException {
        String query = "INSERT INTO user_group VALUE(null, ? )";
        List<String> params = new ArrayList<>();
        params.add(getName());
        return DbService.insertIntoDatabase(query, params);
    }

    private int update() throws SQLException {
        String query = "UPDATE user_group SET name = ? WHERE id = ?";
        List<String> params = new ArrayList<>();
        params.add(getName());
        params.add(String.valueOf(getId()));
        return DbService.executeQuery(query, params);
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) {
        try {
            System.out.println(Arrays.toString(UserGroup.loadAll().toArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
