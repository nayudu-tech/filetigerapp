package com.filetiger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filetiger.model.DocToFile;
import com.filetiger.model.DocToFileIdentity;

@Repository
public interface DocToFileRepository extends JpaRepository<DocToFile, DocToFileIdentity>{

}
