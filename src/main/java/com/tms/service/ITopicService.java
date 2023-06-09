package com.tms.service;

import com.tms.entity.Topic;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuchuang
 * @since 2023-04-19
 */
public interface ITopicService extends IService<Topic> {
    public void excelImport(MultipartFile file) throws IOException;

}
