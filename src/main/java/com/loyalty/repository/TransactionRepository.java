package com.loyalty.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.loyalty.model.TransactionDetails;

@RepositoryRestResource
public interface TransactionRepository extends CrudRepository<TransactionDetails, Long> {

	@Query(value = "SELECT * FROM TRANSACTION WHERE EMPLOYEEID=?1", nativeQuery = true)
	public List<TransactionDetails> findTransactionsOfUser(long userId);
}
