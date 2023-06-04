package com.nuaa.isisnetwork.service;

import com.nuaa.isisnetwork.dao.NetInterfacesRepository;
import com.nuaa.isisnetwork.pojo.NetInterfaces;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author YZX
 * @Create 2023-04-28 10:04
 * @Java-version jdk1.8
 */
//实现网口的所有服务
@Service
@Component
public class NetInterfacesService {

    @Autowired
    NetInterfacesRepository netInterfacesRepository;

    //保存网口
    @Transactional
    public NetInterfaces save(NetInterfaces netInterfaces){
        return netInterfacesRepository.save(netInterfaces);}

    //批量保存网口
    @Transactional
    public List<NetInterfaces> saveList(List<NetInterfaces> netInterList){
        return netInterfacesRepository.saveAll(netInterList);}


    //根据id找到网口
    @Transactional
    public NetInterfaces getById(Integer id){return netInterfacesRepository.findById(id).get();}


    //获取全部的网卡接口信息
    @Transactional
    public List<NetInterfaces> getList(){
        return netInterfacesRepository.findAll();
    }

    //根据容器名称删除网卡
    @Transactional
    public void deleteByLxdName(String lxdName){
        netInterfacesRepository.deleteNetInterfacesByLxdName(lxdName);
    }


}
