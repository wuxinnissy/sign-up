package com.n1ssy2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @PackageName: com.n1ssy2.entity
 * @ClassName: Student
 * @Arthor: N1ssy2
 * @Create: 2023/12/21 14:47
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student implements Serializable {
    private String studentId;
    private String studentName;
    private String password;
}
