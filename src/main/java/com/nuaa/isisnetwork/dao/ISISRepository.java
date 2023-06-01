package com.nuaa.isisnetwork.dao;

import com.nuaa.isisnetwork.pojo.ISIS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author YZX
 * @Create 2023-06-01 16:57
 * @Java-version jdk1.8
 */
public interface ISISRepository extends JpaRepository<ISIS,Integer>, JpaSpecificationExecutor<ISIS> {
}
