package app.leo.profile.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
public class RecruiterProfile implements Profile{
    @Id
    @Column(name ="id")
    @GeneratedValue
    private long recruiterId;

    private String name;
    private String description;
    private String email;
    private String telNo;
    private String location;
    private long userId;
    @CreationTimestamp
    private Timestamp createAt;
    @LastModifiedDate
    private Timestamp updateAt;

    public RecruiterProfile() {
    }

    public RecruiterProfile(String name, String description, String email) {
        this.name = name;
        this.description = description;
        this.email = email;
    }

    public RecruiterProfile(String name, String description, String email, String telNo, String location) {
        this.name = name;
        this.description = description;
        this.email = email;
        this.telNo = telNo;
        this.location = location;
    }

    public RecruiterProfile(long recruiterId, String name, String description, String email, String telNo, String location,
        Timestamp createAt, Timestamp updateAt) {
        this.recruiterId = recruiterId;
        this.name = name;
        this.description = description;
        this.email = email;
        this.telNo = telNo;
        this.location = location;
        this.createAt = createAt;
        this.updateAt = updateAt;
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

    public String getEmail() {
        return email;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
