package com.loyalty.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.loyalty.model.User;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long> {

	@Query(value = "SELECT * FROM loyalty ORDER BY POINTS DESC", nativeQuery = true)
	public 	List<User> findTopUsers();
	
	@Query(value = "SELECT count(*) FROM loyalty", nativeQuery = true)
	public 	long usersCount();
	
}
