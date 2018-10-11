package com.lunf.lois.data.secondary;

import com.lunf.lois.data.secondary.entity.LfJobOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@SecondaryMapper
public interface LfJobOrderMapper {
    @Select("select * from joborder where code = #{code}")
    LfJobOrder findByOrderCode(@Param("code") String code);

}
