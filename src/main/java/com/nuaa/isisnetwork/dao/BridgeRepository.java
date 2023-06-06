package com.nuaa.isisnetwork.dao;

import com.nuaa.isisnetwork.pojo.Bridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author YZX
 * @Create 2023-06-05 16:52
 * @Java-version jdk1.8
 */
public interface BridgeRepository extends JpaRepository<Bridge,Integer>, JpaSpecificationExecutor<Bridge> {

    //获得网桥桥头升序排序后的网桥构建
    List<Bridge> findAllByOrderByBridgeHeadAsc();


    //获得网桥桥尾降序排序后的网桥构建
    List<Bridge> findAllByOrderByBridgeEndAsc();

}
