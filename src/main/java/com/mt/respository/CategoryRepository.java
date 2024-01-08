package com.mt.respository;

import com.mt.model.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>, JpaSpecificationExecutor<CategoryEntity> {
    @Query("select (count(c) > 0) from Category c where c.name = ?1")
    boolean existsByName(String name);

    @Query("select (count(c) > 0) from Category c where c.name = ?1 and c.deletedAt is null")
    boolean existsByNameAndDeleteAtIsNull(String name);

    @Query("select c from Category c where upper(c.name) like upper(concat('%', ?1, '%'))")
    Page<CategoryEntity> findAllCategoriesByNameContainingIgnoreCase(Pageable pageable, String q);


    @Query("select c from Category c where c.id = ?1 and c.deletedAt is null")
    Optional<CategoryEntity> findByIdAndDeletedAtIsNull(Long id);
}
