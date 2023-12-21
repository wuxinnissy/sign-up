package com.n1ssy2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
    private LocalDateTime createTime;
}
