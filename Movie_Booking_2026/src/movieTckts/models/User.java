package movieTckts.models;

public class User {
    private String name;
    private String email;
    private String phone;
    private String password;

    
    public User(String name, String email, String phone, String password) {
        if (name == null || email == null) {
            throw new IllegalArgumentException("Name and Email cannot be null");
        }
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    
    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public boolean login(String inputEmail, String inputPassword) {
        return this.email.equalsIgnoreCase(inputEmail) && verifyPassword(inputPassword);
    }

    
    @Override
    public String toString() {
        return "User: " + name + " | Contact: " + email;
    }
}