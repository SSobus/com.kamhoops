package com.kamhoops.data.domain.base;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Abstract Entity
 * <p/>
 * Base class for all entities for common fields
 */
@MappedSuperclass
public abstract class AbstractEntity implements EntityIdentifier, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_gen")
    private Long id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createDate = Calendar.getInstance().getTime();

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteDate;

    @Column(nullable = false)
    private boolean deleted = false;

    @Column(nullable = false)
    private boolean active = true;

    /**
     * Create a partial view of an entity to only expose its id
     */
    public static EntityIdentifier getEntityIdentifier(final EntityIdentifier entity) {
        return entity == null ? null : new EntityIdentifier() {
            @Override
            public Long getId() {
                return entity.getId();
            }
        };
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date modifiedDate) {
        this.lastModifiedDate = modifiedDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;

        if (deleted) {
            this.deleteDate = Calendar.getInstance().getTime();
            this.active = false;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @PrePersist
    protected void sanitise() {

    }

    @PreUpdate
    protected void update() {
        sanitise();
        this.lastModifiedDate = Calendar.getInstance().getTime();
    }
}
