package com.filetiger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filetiger.model.FTFolder;

@Repository
public interface FTFolderRepository extends JpaRepository<FTFolder, Integer>{

}
