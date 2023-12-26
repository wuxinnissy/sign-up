package com.n1ssy2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ClassName: CheckinRecordDTO
 * Package: com.n1ssy2.dto
 * Description:
 *
 * @Auther: N1ssy2
 * @Create: 2023/12/22 20:05
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckinRecordDTO implements Serializable {
    private String studentId;
    private String checkinNode;
}
