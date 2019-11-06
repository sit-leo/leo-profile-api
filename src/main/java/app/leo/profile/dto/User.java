package app.leo.profile.dto;

public class User {

    private long id;
    private String role;
    private long profileId;

    public User() {
    }

	public User(long id, String role, long profileId) {
		this.id = id;
		this.role = role;
		this.profileId = profileId;
	}

	public long getId() {
		return this.id;
	}

	public String getRole() {
		return this.role;
	}

	public long getProfileId() {
		return profileId;
	}
}
