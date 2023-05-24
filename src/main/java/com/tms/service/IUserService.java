package com.tms.service;

import com.tms.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuchuang
 * @since 2023-04-19
 */
public interface IUserService extends IService<User> {
    public int getTeamId(int userid);
    public void excelExport(HttpServletResponse response) throws IOException;
    public void excelImport(MultipartFile file) throws IOException;

}
