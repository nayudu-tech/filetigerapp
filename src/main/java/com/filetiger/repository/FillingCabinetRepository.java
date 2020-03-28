package com.filetiger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filetiger.model.FillingCabinet;

@Repository
public interface FillingCabinetRepository extends JpaRepository<FillingCabinet, Integer>{

}
