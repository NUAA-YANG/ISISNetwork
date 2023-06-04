package com.nuaa.isisnetwork.nfv;

import com.nuaa.isisnetwork.pojo.Routers;
import com.nuaa.isisnetwork.service.RoutersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author YZX
 * @Create 2023-06-02 17:20
 * @Java-version jdk1.8
 */
//获取容器信息
@Service
@Component
public class CreateLxd {
    @Autowired
    RoutersService routersService;

    public List<Routers> getRouters(String xmlPath){
        List<Routers> list = new ArrayList<>();
        return list;
    }
}
