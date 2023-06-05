package com.nuaa.isisnetwork.service;

import com.nuaa.isisnetwork.dao.BridgeRepository;
import com.nuaa.isisnetwork.pojo.Bridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author YZX
 * @Create 2023-06-05 16:53
 * @Java-version jdk1.8
 */
@Service
@Component
public class BridgeService {
    @Autowired
    BridgeRepository bridgeRepository;

    //保存网桥
    @Transactional
    public Bridge save(Bridge bridge){return bridgeRepository.save(bridge);}

}
