package com.filetiger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.filetiger.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	User findByUsername(String username);
	@Query("from User WHERE username=:username and password=:password")
	User findUser(@Param("username") String username, @Param("password") String password);
}
