package com.homestay.registerservice;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterServiceRepository extends JpaRepository<RegisterService, Integer> {
     @Query("SELECT rs FROM RegisterService rs " +
           "WHERE rs.fromDate >= :startDate AND rs.toDate <= :endDate")
    List<RegisterService> findRegisterServicesInDateRange(
        @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
        List<RegisterService> findByCustomerId(Integer customerId);
    List<RegisterService> findByContractId(Integer contractId);
}
