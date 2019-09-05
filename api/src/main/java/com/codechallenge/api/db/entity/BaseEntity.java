package com.codechallenge.api.db.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@MappedSuperclass
public abstract class BaseEntity {

    @Getter
    private Date createdOn;
    @Getter
    private Date updatedOn;

    @PrePersist
    void onCreate() {
        this.createdOn = new Date();
        this.updatedOn = new Date();
    }

    @PreUpdate
    void onPersist() {
        this.updatedOn = new Date();
    }

}