package app.leo.profile.models;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class RecruiterProfile implements Profile{
    @Id
    @Column(name ="id")
    private long recruiterId;

    private String name;
    private String description;
    @CreationTimestamp
    private Timestamp createAt;
    @LastModifiedDate
    private Timestamp updateAt;

    public RecruiterProfile() {
    }

    public RecruiterProfile(long recruiterId, String name, String description) {
        this.recruiterId = recruiterId;
        this.name = name;
        this.description = description;
    }

    public long getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(long recruiterId) {
        this.recruiterId = recruiterId;
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

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }
}
