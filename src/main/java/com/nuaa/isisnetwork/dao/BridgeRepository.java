package com.nuaa.isisnetwork.dao;

import com.nuaa.isisnetwork.pojo.Bridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author YZX
 * @Create 2023-06-05 16:52
 * @Java-version jdk1.8
 */
public interface BridgeRepository extends JpaRepository<Bridge,Integer>, JpaSpecificationExecutor<Bridge> {
}
