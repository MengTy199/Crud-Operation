package com.mt.model.entity;

import com.mt.infrastructure.model.entity.BaseSoftDeleteEnitity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity(name = "Category")
@Table(name = "Categories")
public class CategoryEntity extends BaseSoftDeleteEnitity<Long> {
    @Column(nullable = false, length = 30)
    private String name;

    @Column(length = 100)
    private String description;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CategoryEntity category = (CategoryEntity) o;
        return getId() != null && Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

