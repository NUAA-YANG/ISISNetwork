package com.nuaa.isisnetwork.service;

import com.nuaa.isisnetwork.dao.RoutersRepository;
import com.nuaa.isisnetwork.pojo.Routers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author YZX
 * @Create 2023-04-28 10:24
 * @Java-version jdk1.8
 */
//实现路由器的全部服务
@Service
@Component
public class RoutersService {
    @Autowired
    RoutersRepository routersRepository;

    //保存路由器
    @Transactional
    public Routers save(Routers routers){return routersRepository.save(routers);}

    //存储路由器列表
    @Transactional
    public List<Routers> saveList(List<Routers> routersList){return routersRepository.saveAll(routersList);}

    //根据id获得路由器
    @Transactional
    public Routers getById(Integer id){return routersRepository.findById(id).get();}

    //根据名称获得路由器
    @Transactional
    public Routers getByName(String name){return routersRepository.findRoutersByName(name);}

    //查询所有
    @Transactional
    public List<Routers> getList(){return routersRepository.findAll();}

    //根据名字删除
    @Transactional
    public void deleteByName(String name){
        routersRepository.deleteRoutersByName(name);
    }
}
