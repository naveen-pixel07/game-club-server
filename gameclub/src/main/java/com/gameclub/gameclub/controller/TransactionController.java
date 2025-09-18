package com.gameclub.gameclub.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gameclub.gameclub.model.Transaction;
import com.gameclub.gameclub.repository.TransactionRepository;

@RestController
@RequestMapping(path="/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository repo;

    @PostMapping
    public Transaction create(@RequestBody Transaction transaction) {
        transaction.setId(null);
        if (transaction.getTimestamp() == 0) {
            transaction.setTimestamp(System.currentTimeMillis());
        }
        return repo.save(transaction);
    }

    @GetMapping
    public List<Transaction> findAll() {
        return repo.findAll();
    }

    @GetMapping(path="/{id}")
    public Transaction findById(@PathVariable String id) {
        return repo.findById(id).get();
    }

    @PutMapping(path="/{id}")
    public Transaction update(@PathVariable String id, @RequestBody Transaction transaction) {
        Transaction old = repo.findById(id).get();
        old.setMemberId(transaction.getMemberId());
        old.setType(transaction.getType());
        old.setAmount(transaction.getAmount());
        old.setTimestamp(transaction.getTimestamp() == 0 ? old.getTimestamp() : transaction.getTimestamp());
        return repo.save(old);
    }

    @DeleteMapping(path="/{id}")
    public void delete(@PathVariable String id) {
        repo.deleteById(id);
    }

    @GetMapping(path="/member/{memberId}")
    public List<Transaction> findByMember(@PathVariable String memberId) {
        return repo.findByMemberIdOrderByTimestampDesc(memberId);
    }
}


