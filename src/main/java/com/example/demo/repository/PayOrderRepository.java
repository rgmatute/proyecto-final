package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.PayOrder;

@Repository
public interface PayOrderRepository extends MongoRepository<PayOrder, String> {

}
