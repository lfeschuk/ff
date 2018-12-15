package Objects;

public class UserAdmin {

    public String username;
    public String email;
    public Boolean enable_notification;
    public UserAdmin() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public UserAdmin(UserAdmin ua) {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        this.username = ua.getUsername();
        this.email = ua.getEmail();
        this.enable_notification = ua.getEnable_notification();
    }

    public UserAdmin(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnable_notification() {
        return enable_notification;
    }

    public void setEnable_notification(Boolean enable_notification) {
        this.enable_notification = enable_notification;
    }
}