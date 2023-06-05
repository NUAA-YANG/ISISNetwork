package com.nuaa.isisnetwork.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author YZX
 * @Create 2023-06-05 16:32
 * @Java-version jdk1.8
 */
//网桥
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Bridge")
public class Bridge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String bridgeHead;
    private String bridgeEnd;
}
