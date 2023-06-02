package com.nuaa.isisnetwork.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author YZX
 * @Create 2023-06-01 16:47
 * @Java-version jdk1.8
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ISIS")
public class ISIS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String flag;//isis代号
    private String type;//isis协议等级
    private String net;//net号码
    private String lxdName;//所属容器名称
    private String ethName;//容器所属网卡的名称

}
