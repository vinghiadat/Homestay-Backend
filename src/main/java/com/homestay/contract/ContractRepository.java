package com.homestay.contract;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.homestay.user.User;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    Optional<List<Contract>> findByCustomer_Id(Integer id);
    @Modifying
    @Query("UPDATE Contract c SET c.status = :status, c.note = :note, c.admin = :admin WHERE c.id = :contractId")
    void updateContractStatusAndNoteAndAdmin(Integer contractId, Integer status, String note, User admin);
    List<Contract> findByNumberRoomAndRoomType(Integer numberRoom, String roomType);
}
