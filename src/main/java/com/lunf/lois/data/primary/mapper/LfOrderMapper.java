package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.data.primary.PrimaryMapper;
import com.lunf.lois.data.primary.entity.LfOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@PrimaryMapper
public interface LfOrderMapper {

    @Insert("insert into lf_order (name, code, estimated_completion_date, delivery_address, booking_quota, type) " +
            "values (#{name}, #{code}, #{estimatedCompletionDate}, #{deliveryAddress}, #{bookingQuota}, #{type})")
    @Options(useGeneratedKeys=true, keyColumn = "id")
    void insert(LfOrder lfOrder);

    @Select("select * from lf_order where id = #{id}")
    LfOrder findById(@Param("id") Long id);

}
