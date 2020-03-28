package com.filetiger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filetiger.model.Categories;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer>{

}
