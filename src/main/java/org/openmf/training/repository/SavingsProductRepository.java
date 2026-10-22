package org.openmf.training.repository;

import org.openmf.training.domain.SavingsProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsProductRepository
        extends JpaRepository<SavingsProduct, Long> {
}