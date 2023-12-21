package com.n1ssy2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ClassName: CheckinCase
 * Package: com.n1ssy2.entity
 * Description:
 *
 * @Auther: N1ssy2
 * @Create: 2023/12/21 23:32
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckinCase implements Serializable {
    private String teacherId;
    private String courseId;
    private LocalDateTime createTime;
    private String checkinId;
    private String checkinNode;
}
