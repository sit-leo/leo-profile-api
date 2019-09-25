package app.leo.profile.dto;

public class User {

    private long id;
    private String role;

    public User() {
    }

		public User(long userId, String role) {
				this.id = userId;
				this.role = role;
		}

	public long getId() {
		return this.id;
	}

	public String getRole() {
		return this.role;
	}
}
