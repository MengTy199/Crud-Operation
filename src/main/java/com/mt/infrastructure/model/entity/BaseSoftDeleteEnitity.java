package com.mt.infrastructure.model.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract  class BaseSoftDeleteEnitity<ID extends Serializable> extends BaseAudiEnitity<ID>{
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    public void setDeleteAt(Date deleteAt){
        this.deletedAt = deleteAt;
    }

    public Date getDeleteAt(){
        return this.deletedAt;
    }
}
