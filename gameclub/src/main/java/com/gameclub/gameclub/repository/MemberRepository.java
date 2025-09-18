package com.gameclub.gameclub.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.gameclub.gameclub.model.Member;

@Repository
public interface MemberRepository extends MongoRepository<Member, String> {
    
}
