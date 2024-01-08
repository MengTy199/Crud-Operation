package com.mt.service;
import com.mt.exception.AlreadyException;
import com.mt.exception.BedRequestException;
import com.mt.exception.NotFoundException;
import com.mt.model.entity.CategoryEntity;
import com.mt.model.request.CategoryReqest;
import com.mt.model.request.RestoreCategoryReqest;
import com.mt.respository.CategoryRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //create
    public CategoryEntity create(CategoryReqest reqest) throws Exception {
        CategoryEntity entity = reqest.toEntity();

        if (this.categoryRepository.existsByNameAndDeleteAtIsNull(entity.getName())) {
            throw new AlreadyException("Category name alreay exist!");
        }

        return this.categoryRepository.save(entity);
    }

    //update
    public CategoryEntity update(CategoryReqest reqest, Long id) throws Exception {
        CategoryEntity foundata = this.findone(id);
        foundata.setName(reqest.getName() == null ? foundata.getName() : reqest.getName());
        foundata.setDescription(reqest.getDescription() == null ? foundata.getDescription() : reqest.getDescription());

        try {
            return this.categoryRepository.save(foundata);
        } catch (Exception ex) {
            throw new Exception(ex);
        }

    }

    //find by id
    public CategoryEntity findone(Long id) throws Exception {
        return   this.categoryRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new NotFoundException("Category Notfound!"));
    }

    //delete
    public CategoryEntity delete(Long id) throws Exception {
        CategoryEntity category = this.findone(id);
        try{
//            this.categoryRepository.deleteById(category.getId());
            category.setDeleteAt(new Date());

            this.categoryRepository.save(category);

        }catch (Exception ex){
            throw  new Exception(ex);
        }
        return category;
    }

    //findall
    public Page<CategoryEntity> findAll( int limit, int page, boolean Ispage, String sort, boolean isTrash, Map<String, String> reqParam) throws BedRequestException {


        if (page <= 0 || limit <= 0) throw new BedRequestException("Invalid pagination! ");
        List<Sort.Order> sortByList = new ArrayList<>();
        for (String item : sort.split(",")
        ) {
            String[] srt = item.split(":");
            if (srt.length != 2) {
                System.out.println("srt" + srt.length);
                continue;
            }
//                throw new BedRequestException("Invalid Sort");
            String direction = srt[1].toLowerCase();
            String field = srt[0];
            sortByList.add(new Sort.Order(direction.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, field));
        }

        Pageable pageable;
        if (Ispage) pageable = PageRequest.of(page - 1, limit, Sort.by(sortByList));
        else pageable = Pageable.unpaged();
        return this.categoryRepository.findAll((Specification<CategoryEntity>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Map.Entry<String, String> entry : reqParam.entrySet()) {
                if (entry.getKey().startsWith("q_")) {
                    String qKey = entry.getKey().split("q_",2)[1];
                    String qValue = entry.getValue() == null ? "" : entry.getValue();
                    predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(qKey).as(String.class)), "%" + qValue.toUpperCase() + "%"));
                }
            }
            if(predicates.size() ==0){
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("name").as(String.class)), "%" + "" + "%"));

            }
            return criteriaBuilder.and(isTrash ? criteriaBuilder.isNotNull(root.get("deletedAt")): criteriaBuilder.isNull(root.get("deletedAt")) , criteriaBuilder.or(predicates.toArray(Predicate[]::new)));
        }, pageable);


    }

    private  CategoryEntity findOneWithSoftDeleted (Long id) throws  NotFoundException{
        return this.categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Category not found!"));
    }

    public CategoryEntity restore(Long id, RestoreCategoryReqest reqest) throws  Exception {

        //get category data from db by id
        CategoryEntity category = this.findOneWithSoftDeleted(id);

        //check name from request exists or not in db
        if (this.categoryRepository.existsByNameAndDeleteAtIsNull(reqest.getName())) {
            throw new AlreadyException("Category name alreay exist!");
        }

        //remove deletedAt field to null value
        category.setDeleteAt(null);
        category.setName(reqest.getName());
        try{
            return this.categoryRepository.save(category);

        }catch (Exception ex){
            throw  new Exception(ex);
        }

    }
}


