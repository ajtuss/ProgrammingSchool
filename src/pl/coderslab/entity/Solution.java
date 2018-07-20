package pl.coderslab.entity;

import pl.coderslab.service.DbService;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Solution {

    private int id;
    private Date created;
    private Date updated;
    private String description;
    private Exercise exercise;
    private User user;

    public Solution() {
    }

    public Solution(String description, Exercise exercise, User user) {
        setDescription(description);
        setExercise(exercise);
        setUser(user);
    }

    private Solution(int id, Date created, Date updated, String description, Exercise exercise, User user) {
        setId(id);
        setCreated(created);
        setUpdated(updated);
        setDescription(description);
        setExercise(exercise);
        setUser(user);
    }

    public int getId() {
        return id;
    }


    private void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
            String query = "DELETE FROM solution WHERE id = ?";
            List<String> params = new ArrayList<>();
            params.add(String.valueOf(getId()));
            int result = DbService.executeQuery(query, params);
            setId(0);
            return result;
        }
        return 0;
    }

    public static List<Solution> loadAll() throws Exception {
        String query = "SELECT id, created, updated, description, exercise_id, users_id FROM solution";
        List<String[]> list = DbService.getData(query, null);
        List<Solution> solutions = new ArrayList<>();
        for (String[] tab : list) {
            int id = Integer.parseInt(tab[0]);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date created = sdf.parse(tab[1]);
            Date updated = sdf.parse(tab[2]);
            String description = tab[3];
            int exerciseId = Integer.parseInt(tab[4]);
            int userId = Integer.parseInt(tab[5]);
            Exercise exercise = Exercise.loadById(exerciseId);
            User user = User.loadById(userId);
            Solution solution = new Solution(id, created, updated, description, exercise, user);
            solutions.add(solution);
        }

        return solutions;
    }

    public static Solution loadById(int userGroupId) throws Exception {
        String query = "SELECT id, created, updated, description, exercise_id, users_id FROM solution WHERE id = ?";
        List<String> params = new ArrayList<>();
        params.add(String.valueOf(userGroupId));
        List<String[]> list = DbService.getData(query, params);
        if (list.isEmpty()) {
            return null;
        } else {
            String[] tab = list.get(0);
            int id = Integer.parseInt(tab[0]);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date created = sdf.parse(tab[1]);
            Date updated = sdf.parse(tab[2]);
            String description = tab[3];
            int exerciseId = Integer.parseInt(tab[4]);
            int userId = Integer.parseInt(tab[5]);
            Exercise exercise = Exercise.loadById(exerciseId);
            User user = User.loadById(userId);
            return new Solution(id, created, updated, description, exercise, user);
        }
    }

    public static int deleteAll() throws SQLException {
        String query = "DELETE FROM solution";
        return DbService.executeQuery(query, null);
    }

    private int insert() throws SQLException {
        String query = "INSERT INTO solution VALUE(null, now(), now(), ?, ?, ? )";
        List<String> params = new ArrayList<>();
        params.add(getDescription());
        int exerciseId = getExercise().getId();
        params.add(String.valueOf(exerciseId));
        int userId = getUser().getId();
        params.add(String.valueOf(userId));
        return DbService.insertIntoDatabase(query, params);
    }

    private int update() throws SQLException {
        String query = "UPDATE solution SET updated = now(), description = ?, exercise_id = ?, users_id = ? WHERE id = ?";
        List<String> params = new ArrayList<>();
        params.add(getDescription());
        int exerciseId = getExercise().getId();
        params.add(String.valueOf(exerciseId));
        int userId = getUser().getId();
        params.add(String.valueOf(userId));
        params.add(String.valueOf(getId()));
        return DbService.executeQuery(query, params);
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", created=" + created +
                ", updated=" + updated +
                ", description='" + description + '\'' +
                ", exercise=" + exercise +
                ", user=" + user +
                '}';
    }

    public static void main(String[] args) {
        try {
            Solution solution = Solution.loadById(2);
            solution.setDescription("jakis tekst dwa");
            solution.save();
            System.out.println(Arrays.toString(Solution.loadAll().toArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
