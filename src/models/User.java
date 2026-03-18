package models;

public class User {
    private String publicId;
    private String email;

    public User(String publicId, String email) {
        this.publicId = publicId;
        this.email = email;
    }

    public String getPublicId() { return publicId; }
    public String getEmail() { return email; }
}