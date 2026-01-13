package com.duymeke.finaltwo.repository;

import com.duymeke.finaltwo.entity.Account;
import com.duymeke.finaltwo.entity.WorkItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkItemRepository extends JpaRepository<WorkItem, Long> {
    List<WorkItem> findByAccount(Account account);
}
