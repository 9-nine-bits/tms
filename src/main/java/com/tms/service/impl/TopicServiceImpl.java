package com.tms.service.impl;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.tms.dto.UserDto;
import com.tms.entity.Topic;
import com.tms.entity.User;
import com.tms.listner.ExcelListener;
import com.tms.listner.TopicExcelListner;
import com.tms.mapper.TopicMapper;
import com.tms.mapper.UserMapper;
import com.tms.service.ITopicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tms.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuchuang
 * @since 2023-04-19
 */
@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements ITopicService {

    @Resource
    UserMapper userMapper;
    @Resource
    TopicMapper topicMapper;
    public void excelImport(MultipartFile file) throws IOException {
        if(!file.getOriginalFilename().equals("选题名单.xls") && !file.getOriginalFilename().equals("选题名单.xlsx") ){
            return;
        }
        User u= ThreadLocalUtil.getCurrentUser();
        int userid=userMapper.getId(u.getAccount());
        InputStream inputStream = new BufferedInputStream(file.getInputStream());
        //实例化实现了AnalysisEventListener接口的类
        TopicExcelListner excelListener = new TopicExcelListner(topicMapper,userid);
        ExcelReader reader = new ExcelReader(inputStream,null,excelListener);
        //读取信息
        reader.read(new Sheet(1,1, UserDto.class));
    }
}
