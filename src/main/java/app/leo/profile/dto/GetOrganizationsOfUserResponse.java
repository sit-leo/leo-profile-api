package app.leo.profile.dto;

public class GetOrganizationsOfUserResponse {

    private long id;
    private String name;
    private String description;
    private int numberOfApplicant;
    private int numberOfRecruiter;
    private long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfApplicant() {
        return numberOfApplicant;
    }

    public void setNumberOfApplicant(int numberOfApplicant) {
        this.numberOfApplicant = numberOfApplicant;
    }

    public int getNumberOfRecruiter() {
        return numberOfRecruiter;
    }

    public void setNumberOfRecruiter(int numberOfRecruiter) {
        this.numberOfRecruiter = numberOfRecruiter;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
