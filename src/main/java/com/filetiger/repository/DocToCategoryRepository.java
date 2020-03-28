package com.filetiger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.filetiger.model.DocToCategory;
import com.filetiger.model.DocToCategoryIdentity;

@Repository
public interface DocToCategoryRepository extends JpaRepository<DocToCategory, DocToCategoryIdentity>{
	@Query("from DocToCategory WHERE docToCategoryIdentity.documentId=:documentId")
	List<DocToCategory> docTypeCategories(@Param("documentId") Integer documentId);
	@Query("delete from DocToCategory WHERE categoryId=:categoryId")
	void deleteAllCategoriesAttachedToCategory(@Param("categoryId") Integer categoryId);
}
