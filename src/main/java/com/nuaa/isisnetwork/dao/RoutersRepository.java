package com.nuaa.isisnetwork.dao;

import com.nuaa.isisnetwork.pojo.Routers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Author YZX
 * @Create 2023-06-2 20:59
 * @Java-version jdk1.8
 */
@Repository
//接口继承实现Routers的增删改查
public interface RoutersRepository extends JpaRepository<Routers,Integer>, JpaSpecificationExecutor<Routers> {

    //根据路由器名称查询
    Routers findRoutersByName(String name);
}
