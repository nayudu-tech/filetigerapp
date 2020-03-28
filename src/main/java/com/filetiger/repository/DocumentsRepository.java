package com.filetiger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filetiger.model.Documents;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Integer>{

}
