package pl.coderslab.dao;

import pl.coderslab.entity.UserGroup;
import pl.coderslab.service.DbService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserGroupDao {

    public static int save(UserGroup userGroup) throws SQLException {
        if (userGroup.getId() == 0) {
            return insert(userGroup);
        } else {
            return update(userGroup);
        }
    }

    public static int delete(UserGroup userGroup) throws SQLException {
        if (userGroup.getId() != 0) {
            String query = "DELETE FROM user_group WHERE id = ?";
            List<String> params = new ArrayList<>();
            params.add(String.valueOf(userGroup.getId()));
            return DbService.executeQuery(query, params);
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

    private static int insert(UserGroup userGroup) throws SQLException {
        String query = "INSERT INTO user_group VALUE(null, ? )";
        List<String> params = new ArrayList<>();
        params.add(userGroup.getName());
        return DbService.insertIntoDatabase(query, params);
    }

    private static int update(UserGroup userGroup) throws SQLException {
        String query = "UPDATE user_group SET name = ? WHERE id = ?";
        List<String> params = new ArrayList<>();
        params.add(userGroup.getName());
        params.add(String.valueOf(userGroup.getId()));
        return DbService.insertIntoDatabase(query, params);
    }

}
