package com.gossip_point.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gossip_point.app.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("Select m From Message m join m.chat c where c.id=:chatId")
	public List<Message> findByChatId(@Param("chatId") Integer chatId);
}
