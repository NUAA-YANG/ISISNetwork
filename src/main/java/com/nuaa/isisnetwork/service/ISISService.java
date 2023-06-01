package com.nuaa.isisnetwork.service;

import com.nuaa.isisnetwork.dao.ISISRepository;
import com.nuaa.isisnetwork.pojo.ISIS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author YZX
 * @Create 2023-06-01 17:03
 * @Java-version jdk1.8
 */
//实现ISIS的所有服务
@Service
@Component
public class ISISService {
    @Autowired
    ISISRepository isisRepository;

    //保存ISIS协议
    @Transactional
    public ISIS save(ISIS isis){
        return isisRepository.save(isis);
    }

    //根据本身的id找到ISIS协议
    @Transactional
    public ISIS getById(Integer id){
        return isisRepository.findById(id).get();
    }

    //查询所有的ISIS列表
    @Transactional
    public List<ISIS> getList(){
        return isisRepository.findAll();
    }


}
