package com.lunf.delilah.data.mapper;

import com.lunf.delilah.data.entity.LfJob;
import com.lunf.delilah.data.entity.LfUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Array;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@MybatisTest
public class LfJobMapperTest {

    @Autowired
    private LfJobMapper lfJobMapper;

    @Autowired
    private LfUserMapper lfUserMapper;


    @Before
    public void setup() {

    }

    @Test
    public void insertJobTest() {

        String title = "This is message title";
        LfUser lfUser = lfUserMapper.findByUsername("john.doe@domain.com");

        LfJob job = new LfJob();

        job.setCreatedAt(ZonedDateTime.now());
        job.setDevices("{\"g2ZnDeZfGZ6CFmmGiYTzxTjfQHyTD4VcmzPZuYoAmpbgR4MTxL0KILfkk0yKtay8\", \"ms3tUJHDc8TX2kkTOZB180tZlnBjJf66D8sOaqeopdbJK5nKaHy9hXIicXb1EqFS\"}");
        job.setMessageBody("This is message body");
        job.setMessageMetadata("{\"key_1\":\"value_1\", \"key_2\":\"value_2\"}");
        job.setMessageType(1);
        job.setMessageTitle(title);
        job.setSendToType(4);
        job.setSender(lfUser);

        lfJobMapper.insert(job);

        LfJob foundJob = lfJobMapper.findById(0L);

        assertThat(foundJob.getMessageTitle()).isEqualTo(title);
        assertThat(foundJob.getMessageType()).isEqualTo(1);
        assertThat(foundJob.getSender().getUsername()).isEqualTo(lfUser.getUsername());
    }
}
