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

}
