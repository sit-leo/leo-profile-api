package app.leo.profile.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Picture {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String genaratedName;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getGenaratedName() {
        return genaratedName;
    }

    public void setGenaratedName(String genaratedName) {
        this.genaratedName = genaratedName;
    }
}
