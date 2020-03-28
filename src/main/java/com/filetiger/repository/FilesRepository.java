package com.filetiger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.filetiger.model.Files;

@Repository
public interface FilesRepository extends JpaRepository<Files, Integer>{
	@Query("from Files WHERE documentId=:documentId")
	List<Files> fetchAllFiles(@Param("documentId") Integer documentId);
}
