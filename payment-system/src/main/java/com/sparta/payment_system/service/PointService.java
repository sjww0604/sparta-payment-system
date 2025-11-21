package com.sparta.payment_system.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.sparta.payment_system.entity.Grade;
import com.sparta.payment_system.entity.MemberShip;
import com.sparta.payment_system.entity.Order;
import com.sparta.payment_system.entity.PointTransaction;
import com.sparta.payment_system.entity.User;
import com.sparta.payment_system.repository.MemberShipRepository;
import com.sparta.payment_system.repository.PointTransactionRepository;

@Service
public class PointService {
	private final PointTransactionRepository pointTransactionRepository;
	private final MemberShipRepository memberShipRepository;

	public PointService(PointTransactionRepository pointTransactionRepository,
		MemberShipRepository memberShipRepository) {
		this.pointTransactionRepository = pointTransactionRepository;
		this.memberShipRepository = memberShipRepository;
	}

	/* 결제 완료 후 포인트 적립
	 * - 현재 정책 : 등급별 결제 금액의 일정 비율을 포인트로 적립 (소수점 이하 제거)
	 * - 추후 정책 변경 시 이 메서드 수정 필요
	 */
	public void earnPointsAfterPayment(Order order, BigDecimal paidAmount) {
		if (order == null || paidAmount == null) {
			return;
		}

		if (paidAmount.compareTo(BigDecimal.ZERO) <= 0) {
			return;
		}

		User user = order.getUser();
		if (user == null) {
			return;
		}

		// 등급별 결제 금액의 일정 비율을 적립
		MemberShip memberShip = memberShipRepository.findByUser(user);
		if (memberShip == null) {
			return;
		}

		Grade grade = memberShip.getGrade();
		// 1, 5, 10 비율을 소수로 바꾸는 과정
		BigDecimal rate = BigDecimal.valueOf(grade.getRate())
			.divide(BigDecimal.valueOf(100));

		// 결제 금액 * 등급별 적립률
		int points = paidAmount
			.multiply(rate)
			.setScale(0, RoundingMode.DOWN)
			.intValue();

		if (points <= 0) {
			return;
		}

		LocalDateTime expiresAt = LocalDateTime.now().plusDays(30);

		PointTransaction pointTransaction = new PointTransaction(
			user,
			order,
			points,
			expiresAt
		);

		pointTransactionRepository.save(pointTransaction);
	}
}
