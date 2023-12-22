package com.n1ssy2.mapper;

import com.n1ssy2.entity.CheckinRecord;
import com.n1ssy2.entity.Student;
import com.n1ssy2.entity.StudentCourse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @PackageName: com.n1ssy2.mapper
 * @ClassName: StudentMapper
 * @Arthor: N1ssy2
 * @Create: 2023/12/21 14:52
 * @Version: 1.0
 **/
@Mapper
public interface StudentMapper {
    /**
     * 根据id查询信息
     * @param studentId
     * @return
     */
    @Select("select * from student where student_id = #{studentId}")
    Student getById(String studentId);

    /**
     * 通过签到码获取签到id
     * @param checkinNode
     * @return
     */
    @Select("select checkin_id from checkin_case where checkin_node = #{checkinNode}")
    Integer getCheckinIdByCheckinNode(String checkinNode);

    /**
     * 添加签到记录
     * @param checkinRecord
     */
    @Update("update checkin_record " +
            "set checkin_time = #{checkinTime}, checkin_status = #{checkinStatus} " +
            "where checkin_id = #{checkinId}")
    void addCheckinRecord(CheckinRecord checkinRecord);
}
