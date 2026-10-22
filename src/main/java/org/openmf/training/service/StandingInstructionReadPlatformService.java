package org.openmf.training.service;

import org.openmf.training.data.StandingInstructionData;
import org.openmf.training.domain.StandingInstruction;
import org.openmf.training.repository.SavingsAccountRepository;
import org.openmf.training.repository.StandingInstructionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StandingInstructionReadPlatformService {

    private final SavingsAccountRepository accountRepository;
    private final StandingInstructionRepository standingInstructionRepository;

    public StandingInstructionReadPlatformService(
            SavingsAccountRepository accountRepository,
            StandingInstructionRepository standingInstructionRepository
    ) {
        this.accountRepository = accountRepository;
        this.standingInstructionRepository = standingInstructionRepository;
    }

    @Transactional(readOnly = true)
    public List<StandingInstructionData> retrieveByFromAccount(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new IllegalArgumentException("Savings account not found: " + accountId);
        }

        return standingInstructionRepository
                .findByFromAccountIdOrderByIdAsc(accountId)
                .stream()
                .map(this::toData)
                .toList();
    }

    private StandingInstructionData toData(StandingInstruction instruction) {
        return new StandingInstructionData(
                instruction.getId(),
                instruction.getFromAccount().getId(),
                instruction.getToAccount().getId(),
                instruction.getFromAccount().getAccountNo(),
                instruction.getToAccount().getAccountNo(),
                instruction.getAmount(),
                instruction.getFrequency(),
                instruction.getNextDue(),
                instruction.getStatus(),
                instruction.getValidFrom(),
                instruction.getValidTo()
        );
    }
}
