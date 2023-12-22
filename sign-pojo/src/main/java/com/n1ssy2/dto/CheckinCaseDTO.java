package com.n1ssy2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * ClassName: CheckinCaseDTO
 * Package: com.n1ssy2.dto
 * Description:
 *
 * @Auther: N1ssy2
 * @Create: 2023/12/21 23:27
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckinCaseDTO implements Serializable {
    private String teacherId;
    private String courseId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;
}
