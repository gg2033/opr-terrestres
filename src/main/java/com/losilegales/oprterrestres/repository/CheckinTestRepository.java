package com.losilegales.oprterrestres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.losilegales.oprterrestres.entity.CheckinTest;

@Repository
public interface CheckinTestRepository extends JpaRepository<CheckinTest, Integer> {

	@Query(value = "SELECT * FROM checkin_test WHERE codigo = :codigo LIMIT 1", nativeQuery = true)
	CheckinTest getCheckinTestConCodigo(@Param(value = "codigo") String codigo);

//	@Query(value = "SELECT * FROM checkin_test WHERE codigo = :codigo LIMIT 1", nativeQuery = true)
//	CheckinTest existeCheckin(@Param(value = "codigo") String codigo);

}
