package com.nuaa.isisnetwork.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author YZX
 * @Create 2023-06-02 16:38
 * @Java-version jdk1.8
 */
//路由器类
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Routers")
public class Routers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//路由器id
    private String name;//路由器名称
    private String interfacesId;//包含的接口id集合,用分号拆分不同的地址
    private Integer isisId;//isis协议id
}
