package com.tms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tms.entity.User;
import com.tms.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TmsApplication.class)
public class updateTest {
    @Resource
    IUserService userService;

    @Test
    public void one() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account","201992449");
        User u=userService.getOne(wrapper);

        System.out.println(u.getUsername());
        u.setPassword("1234");
        userService.update(u,wrapper);

    }

}
