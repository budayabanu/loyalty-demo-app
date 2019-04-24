package com.loyalty.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loyalty.exception.ConflictException;
import com.loyalty.exception.ResourceNotFoundException;
import com.loyalty.model.TransactionDetails;
import com.loyalty.model.User;
import com.loyalty.repository.TransactionRepository;
import com.loyalty.repository.UserRepository;

@RestController
public class UserController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	TransactionRepository transRepository;

	// Create User API
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public User createUser(@RequestBody User user) throws ConflictException {

		if (userRepository.findOne(user.getEmployeeid()) == null) {
			user.setPoints(100);
			user.setMessage("");
			TransactionDetails trans = new TransactionDetails();
			trans.setEmployeeid(user.getEmployeeid());
			trans.setPoints(100);
			trans.setTranstype("COLLECT");
			trans.setLocation("Bonus Points for Registration");
			trans.setTransdate(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()));
			transRepository.save(trans);
			int collectedPoints=transRepository.sumOfCollect(user.getEmployeeid());
			user.setTotalcollect(collectedPoints);
			user.setTotalredeem(0);
			return userRepository.save(user);
		} else
			throw new ConflictException("User Already Exists:" + user.getEmployeeid());

	}

	// Read

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable long id) throws ResourceNotFoundException {
		if (userRepository.findOne(id) != null) {
			return userRepository.findOne(id);
		} else
			throw new ResourceNotFoundException("User not Found");
	}

	@RequestMapping(value = "/user/transaction/{id}", method = RequestMethod.GET)
	public List<TransactionDetails> getUsertransById(@PathVariable long id) {
		return transRepository.findTransactionsOfUser(id);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	@RequestMapping(value = "/users/count", method = RequestMethod.GET)
	public long getUsersCount() {
		return userRepository.usersCount();
	}

	@RequestMapping(value = "/user/topusers", method = RequestMethod.GET)
	public List<User> getUserById() {
		return userRepository.findTopUsers();
	}

	// Update
	@RequestMapping(value = "/user/collect/{id}/{location}", method = RequestMethod.PUT)
	public User collectPoints(@PathVariable long id, @PathVariable String location) {
		User user = userRepository.findOne(id);
		int points = user.getPoints() + 100;
		user.setMessage("");
		user.setPoints(points);
		TransactionDetails trans = new TransactionDetails();
		trans.setEmployeeid(user.getEmployeeid());
		trans.setPoints(100);
		trans.setTranstype("COLLECT");
		trans.setLocation(location);
		trans.setTransdate(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()));
		transRepository.save(trans);
		int redeemedPoints=transRepository.sumOfRedeem(user.getEmployeeid());
		int collectedPoints=transRepository.sumOfCollect(user.getEmployeeid());
		user.setTotalcollect(collectedPoints);
		user.setTotalredeem(redeemedPoints);
		return userRepository.save(user);
	}

	@RequestMapping(value = "/user/redeem/{id}/{location}", method = RequestMethod.PUT)
	public User redeemPoints(@PathVariable long id, @PathVariable String location) {
		User user = userRepository.findOne(id);
		
		if (user.getPoints() >= 300) {
			int points = user.getPoints() - 100;
			user.setPoints(points);
			user.setTotalcollect(transRepository.sumOfCollect(user.getEmployeeid()));
			user.setTotalcollect(transRepository.sumOfRedeem(user.getEmployeeid()));
			user.setMessage("");
			user.setPoints(points);
			TransactionDetails trans = new TransactionDetails();
			trans.setEmployeeid(user.getEmployeeid());
			trans.setPoints(100);
			trans.setLocation(location);
			trans.setTranstype("REDEEM");
			trans.setTransdate(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()));
			transRepository.save(trans);
			int redeemedPoints=transRepository.sumOfRedeem(user.getEmployeeid());
			int collectedPoints=transRepository.sumOfCollect(user.getEmployeeid());
			user.setTotalredeem(redeemedPoints);
			user.setTotalcollect(collectedPoints);
			
		} else {
			user.setMessage("Minimum 300 points required to Redeem");
			
		}
		
		return userRepository.save(user);
	}

	@RequestMapping(value = "/user/vouchers/{id}", method = RequestMethod.PUT)
	public User getUserVouchers(@PathVariable long id) throws ResourceNotFoundException {
		if (userRepository.findOne(id) != null) {
			User user = userRepository.findOne(id);
			TransactionDetails trans = new TransactionDetails();
			trans.setEmployeeid(user.getEmployeeid());
			int points = user.getPoints();
			if (points <= 500) {
				int voucherPoints = points / 2;
				trans.setPoints(points);
				user.setPoints(0);
				trans.setLocation("Points are Vouchered");
				trans.setTranstype("VOUCHERS");
				trans.setTransdate(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()));
				transRepository.save(trans);
				user.setMessage("Converting points to Vouchers of each :" + voucherPoints);
				int redeemedPoints=transRepository.sumOfRedeem(user.getEmployeeid());
				user.setTotalredeem(redeemedPoints);
				userRepository.save(user);
			} else {
				user.setMessage("You have enough points to Redeem points cannot be Vouchered");
				userRepository.save(user);
			}
			return user;
		} else
			throw new ResourceNotFoundException("User not Found");
	}

	// Delete
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public void deleteStudent(@PathVariable long id) {
		userRepository.delete(id);
	}
}
