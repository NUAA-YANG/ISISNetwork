package com.nuaa.isisnetwork.dao;


import com.nuaa.isisnetwork.pojo.NetInterfaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author YZX
 * @Create 2023-06-2 20:59
 * @Java-version jdk1.8
 */
@Repository
//接口继承实现Net接口的增删改查
public interface NetInterfacesRepository extends JpaRepository<NetInterfaces,Integer>, JpaSpecificationExecutor<NetInterfaces> {

    //根据容器名称删除网卡接口
    void deleteNetInterfacesByLxdName(String lxdName);

    //根据容器名称获得网卡接口集合
    @Query("select n from NetInterfaces n where n.lxdName=?1")
    List<NetInterfaces> findNetInterfacesByLxdName(String lxdName);
}
