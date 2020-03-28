package com.filetiger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filetiger.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer>{

}
