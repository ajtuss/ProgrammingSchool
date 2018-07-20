package pl.coderslab.entity;

import java.util.Date;

public class Solution {

    private int id;
    private Date created;
    private Date updated;
    private String description;
    private Exercise exercise;
    private User user;

    public Solution() {
    }

    public Solution(Date created, Date updated, String description, Exercise exercise, User user) {
        setCreated(created);
        setUpdated(updated);
        setDescription(description);
        setExercise(exercise);
        setUser(user);
    }

    public Solution(int id, Date created, Date updated, String description, Exercise exercise, User user) {
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


    public void setId(int id) {
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
}
