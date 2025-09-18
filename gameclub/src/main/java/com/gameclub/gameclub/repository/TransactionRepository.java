package com.gameclub.gameclub.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.gameclub.gameclub.model.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByMemberIdOrderByTimestampDesc(String memberId);
}


