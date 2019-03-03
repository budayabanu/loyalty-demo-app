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
import com.loyalty.exception.ResourceNotFoundException;
import com.loyalty.exception.ConflictException;
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

	// Create
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public User createUser(@RequestBody User user) throws ConflictException {

		if (userRepository.findOne(user.getEmployeeid()) == null) {
			user.setPoints(100);
			TransactionDetails trans = new TransactionDetails();
			trans.setEmployeeid(user.getEmployeeid());
			trans.setPoints(100);
			trans.setTranstype("COLLECT");
			trans.setTransdate(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()));
			transRepository.save(trans);
			return userRepository.save(user);
		} else
			throw new ConflictException("User Already Exists:" + user.getEmployeeid());

	}

	// Read
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable long id) throws ResourceNotFoundException {
	if (userRepository.findOne(id) == null) {
		return userRepository.findOne(id);
	}
	 else
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

	@RequestMapping(value = "/user/collect/{id}", method = RequestMethod.PUT)
	public User collectPoints(@PathVariable long id) {
		User user=userRepository.findOne(id);
		int points=user.getPoints()+100;
		user.setPoints(points);
		TransactionDetails trans = new TransactionDetails();
		trans.setEmployeeid(user.getEmployeeid());
		trans.setPoints(100);
		trans.setTranstype("COLLECT");
		trans.setTransdate(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()));
		transRepository.save(trans);
		return userRepository.save(user);
	}

	@RequestMapping(value = "/user/redeem/{id}", method = RequestMethod.PUT)
	public User redeemPoints(@PathVariable long id) {
		User user=userRepository.findOne(id);
		int points=user.getPoints()-100;
		user.setPoints(points);
		user.setPoints(points);
		TransactionDetails trans = new TransactionDetails();
		trans.setEmployeeid(user.getEmployeeid());
		trans.setPoints(100);
		trans.setTranstype("REDEEM");
		trans.setTransdate(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()));
		transRepository.save(trans);
		return userRepository.save(user);
	}

	// Delete
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public void deleteStudent(@PathVariable long id) {
		userRepository.delete(id);
	}
}
