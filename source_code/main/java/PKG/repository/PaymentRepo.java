package PKG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PKG.entity.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
	
}
