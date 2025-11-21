package com.sparta.payment_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparta.payment_system.entity.PointTransaction;

@Repository
public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {

}
