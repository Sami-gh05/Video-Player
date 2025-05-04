package model;

public abstract class Account {
    private static int idCounter = 100;
    protected String username;
    protected String password;
    protected String fullName;
    protected String email;
    protected String phoneNumber;
    protected String profileCover;
    private String id;
    protected boolean isUser;
    public Account(String username, String password, String fullName, String email, String phoneNumber, String profileCover, boolean isUser) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileCover = profileCover;
        this.id = String.valueOf(idCounter++);
        this.isUser = isUser;
    }
    public Account(){
        this.id = String.valueOf(idCounter++);
    }

    // Getters and setters
    public String getId() { return id; }
    protected void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getProfileCover() { return profileCover; }
    public void setProfileCover(String profileCover) { this.profileCover = profileCover; }

    public boolean isUser() {
        return isUser;
    }
}