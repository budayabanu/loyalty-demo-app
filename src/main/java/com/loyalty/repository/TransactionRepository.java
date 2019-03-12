package com.loyalty.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.loyalty.model.TransactionDetails;

@RepositoryRestResource
public interface TransactionRepository extends CrudRepository<TransactionDetails, Long> {

	@Query(value = "SELECT * FROM transaction WHERE EMPLOYEEID=?1", nativeQuery = true)
	public List<TransactionDetails> findTransactionsOfUser(long userId);
	
	@Query(value = "SELECT SUM(POINTS) FROM transaction WHERE EMPLOYEEID=?1 and TRANSTYPE='COLLECT'", nativeQuery = true)
	public int sumOfCollect(long userId);
	
	@Query(value = "SELECT CASE WHEN SUM(POINTS) IS NULL THEN 0 ELSE SUM(POINTS) END as TOTALREDEEM FROM transaction WHERE EMPLOYEEID=?1 and TRANSTYPE='REDEEM'", nativeQuery = true)
	public int sumOfRedeem(long userId);
}
