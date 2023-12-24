package com.n1ssy2.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ClassName: StuCheckinRecordVO
 * Package: com.n1ssy2.vo
 * Description:
 *
 * @Auther: N1ssy2
 * @Create: 2023/12/24 15:42
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCheckinRecordVO implements Serializable {
    private Integer checkinId;
    private String courseName;
    private Short checkinStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp checkinTime;
}
