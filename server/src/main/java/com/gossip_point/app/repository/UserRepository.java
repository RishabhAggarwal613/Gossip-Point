package com.gossip_point.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gossip_point.app.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByEmail(String email);

	@Query(value="SELECT u FROM User u WHERE u.fullname LIKE %:query% OR u.email LIKE %:query%")
	public List<User> searchUser(@Param("query") String query);
	
}
