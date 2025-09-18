package com.gameclub.gameclub.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gameclub.gameclub.model.Member;
import com.gameclub.gameclub.repository.MemberRepository;
import com.gameclub.gameclub.repository.TransactionRepository;
import com.gameclub.gameclub.model.Transaction;

@RestController
@RequestMapping(path="/members")
public class MemberController {
    
    @Autowired
    private MemberRepository repo;

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping
    public Member create(@RequestBody Member member) {
        member.setId(null);
        Member savedMember = repo.save(member);
        return savedMember;
    }

    @GetMapping
    public List<Member> findAll() {
        return repo.findAll();
    }

    @GetMapping(path="/{id}")
    public Member findById(@PathVariable String id) {
        return repo.findById(id).get();
    }

    @PutMapping(path="/{id}")
    public Member update(@PathVariable String id, @RequestBody Member member) {
        Member oldMember = repo.findById(id).get();
        oldMember.setName(member.getName());
        oldMember.setPh_no(member.getPh_no());
        oldMember.setBalance(member.getBalance());
        return repo.save(oldMember);
    }

    @DeleteMapping(path="/{id}")
    public void delete(@PathVariable String id) {
        repo.deleteById(id);
    }

    @PostMapping(path="/{id}/recharge")
    public Member recharge(@PathVariable String id, @RequestParam float amount) {
        Member member = repo.findById(id).get();
        member.setBalance(member.getBalance() + amount);
        Member updated = repo.save(member);

        Transaction t = new Transaction();
        t.setId(null);
        t.setMemberId(id);
        t.setType("RECHARGE");
        t.setAmount(amount);
        t.setTimestamp(System.currentTimeMillis());
        transactionRepository.save(t);

        return updated;
    }
}
