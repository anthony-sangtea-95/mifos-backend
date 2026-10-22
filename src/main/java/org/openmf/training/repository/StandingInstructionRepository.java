package org.openmf.training.repository;

import org.openmf.training.domain.StandingInstruction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StandingInstructionRepository
        extends JpaRepository<StandingInstruction, Long> {

    List<StandingInstruction> findByFromAccountIdOrderByIdAsc(Long fromAccountId);
}
