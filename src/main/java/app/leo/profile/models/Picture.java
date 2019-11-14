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
    private String imageURL;
    private long profileId;

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

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public String getGenaratedName() {
        return genaratedName;
    }

    public void setGenaratedName(String genaratedName) {
        this.genaratedName = genaratedName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
