package com.n1ssy2.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;
    private Integer checkinId;
    private String checkinNode;
    private Integer validTime;
}
