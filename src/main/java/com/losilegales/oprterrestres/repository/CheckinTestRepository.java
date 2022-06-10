package com.losilegales.oprterrestres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.losilegales.oprterrestres.entity.CheckinTest;

@Repository
public interface CheckinTestRepository extends JpaRepository<CheckinTest, Integer> {

}
