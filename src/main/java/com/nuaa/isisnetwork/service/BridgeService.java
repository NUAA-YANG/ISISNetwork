package com.nuaa.isisnetwork.service;

import com.nuaa.isisnetwork.dao.BridgeRepository;
import com.nuaa.isisnetwork.pojo.Bridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    //获得构建成功且升序排序后的网桥
    @Transactional
    public List<Bridge> getHeadOrderAscList(){return bridgeRepository.findAllByOrderByBridgeHeadAsc();}

    //获得构建成功且降序排序后的网桥
    @Transactional
    public List<Bridge> getEndOrderDescList(){return bridgeRepository.findAllByOrderByBridgeEndAsc();}

}
