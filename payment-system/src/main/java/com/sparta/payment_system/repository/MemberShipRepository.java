package com.sparta.payment_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparta.payment_system.entity.MemberShip;
import com.sparta.payment_system.entity.User;

@Repository
public interface MemberShipRepository extends JpaRepository<MemberShip, Long> {
	MemberShip findByUser(User user);
}
