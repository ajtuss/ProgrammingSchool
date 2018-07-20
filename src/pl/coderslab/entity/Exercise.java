package pl.coderslab.entity;

import pl.coderslab.service.DbService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exercise {

    private int id;
    private String title;
    private String description;

    public Exercise() {
    }

    public Exercise(String title, String description) {
        setTitle(title);
        setDescription(description);
    }

    public Exercise(int id, String title, String description) {
        setId(id);
        setTitle(title);
        setDescription(description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        this.description = description;
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
            String query = "DELETE FROM exercise WHERE id = ?";
            List<String> params = new ArrayList<>();
            params.add(String.valueOf(getId()));
            int result = DbService.executeQuery(query, params);
            setId(0);
            return result;
        }
        return 0;
    }

    public static List<Exercise> loadAll() throws Exception {
        String query = "SELECT id, title, description FROM exercise";
        List<String[]> list = DbService.getData(query, null);
        List<Exercise> listUsers = new ArrayList<>();
        for (String[] tab : list) {
            int id = Integer.parseInt(tab[0]);
            String title = tab[1];
            String description = tab[2];
            Exercise exercise = new Exercise(id, title, description);
            listUsers.add(exercise);
        }

        return listUsers;
    }

    public static Exercise loadById(int exerciseId) throws SQLException {
        String query = "SELECT id, title, description FROM exercise WHERE id = ?";
        List<String> params = new ArrayList<>();
        params.add(String.valueOf(exerciseId));
        List<String[]> list = DbService.getData(query, params);
        if (list.isEmpty()) {
            return null;
        } else {
            String[] tab = list.get(0);
            int id = Integer.parseInt(tab[0]);
            String title = tab[1];
            String description = tab[2];
            return new Exercise(id, title, description);
        }
    }

    public static int deleteAll() throws SQLException {
        String query = "DELETE FROM exercise";
        return DbService.executeQuery(query, null);
    }

    private int insert() throws SQLException {
        String query = "INSERT INTO exercise VALUE(null, ?, ?)";
        List<String> params = new ArrayList<>();
        params.add(getTitle());
        params.add(getDescription());
        return DbService.insertIntoDatabase(query, params);
    }

    private int update() throws SQLException {
        String query = "UPDATE exercise SET title = ?, description = ? WHERE id = ?";
        List<String> params = new ArrayList<>();
        params.add(getTitle());
        params.add(getDescription());
        params.add(String.valueOf(getId()));
        return DbService.executeQuery(query, params);
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static void main(String[] args) {

        try {
            new Exercise("Ćwiczenie nr 4", "Opis ćwiczenia").save();
            System.out.println(Arrays.toString(Exercise.loadAll().toArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
