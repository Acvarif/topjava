package ru.javawebinar.topjava.to;

public class UserTo {
    private int id;

    private String name;

    private String email;

    private String password;

    private int calories;

    public UserTo(String name, String email, String password, int calories) {
        this(null, name, email, password, calories);
    }

    public UserTo(Integer id, String name, String email, String password, int calories) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.calories = calories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
