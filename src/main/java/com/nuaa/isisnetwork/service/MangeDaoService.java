package com.nuaa.isisnetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author YZX
 * @Create 2023-06-04 10:51
 * @Java-version jdk1.8
 */
//数据库统一管理
@Service
@Component
public class MangeDaoService {
    @Autowired
    ISISService isisService;
    @Autowired
    NetInterfacesService netInterfacesService;
    @Autowired
    RoutersService routersService;

    //统一根据容器名称删除数据库内容
    public void delete(String lxdName){
        isisService.deleteByLxdName(lxdName);
        netInterfacesService.deleteByLxdName(lxdName);
        routersService.deleteByName(lxdName);
    }
}
