package org.example.adapter.out.repository;

import org.example.adapter.out.entity.StockOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockOrderRepository extends JpaRepository<StockOrderEntity, Long> {}
