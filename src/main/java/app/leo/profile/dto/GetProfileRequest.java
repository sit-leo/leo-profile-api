package app.leo.profile.dto;

public class GetProfileRequest {
    private long id;
    private String role;

    public GetProfileRequest() {
    }

    public GetProfileRequest(long id, String role) {
        this.id = id;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
