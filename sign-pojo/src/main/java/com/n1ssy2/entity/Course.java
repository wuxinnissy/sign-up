package com.n1ssy2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @PackageName: com.n1ssy2.entity
 * @ClassName: Course
 * @Arthor: N1ssy2
 * @Create: 2023/12/21 14:17
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course implements Serializable {
    private String courseId;
    private String courseName;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
}
