package com.dalfredi.bookkeepingapi.repository;

import com.dalfredi.bookkeepingapi.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
}