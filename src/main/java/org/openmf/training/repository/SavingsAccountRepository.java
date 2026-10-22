package org.openmf.training.repository;

import org.openmf.training.domain.SavingsAccount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SavingsAccountRepository
        extends JpaRepository<SavingsAccount, Long> {
    Optional<SavingsAccount> findByAccountNo(String accountNo);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select account from SavingsAccount account where account.id = :id")
    Optional<SavingsAccount> findByIdForUpdate(@Param("id") Long id);

    boolean existsByAccountNo(String accountNo);
}