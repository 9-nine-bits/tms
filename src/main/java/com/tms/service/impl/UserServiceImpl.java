package com.tms.service.impl;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tms.dto.StudentGradeDto;
import com.tms.dto.StudentMessageDto;
import com.tms.dto.StudentPageDto;
import com.tms.dto.UserDto;
import com.tms.entity.*;
import com.tms.listner.ExcelListener;
import com.tms.mapper.*;
import com.tms.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuchuang
 * @since 2023-04-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    UserMapper userMapper;
    @Resource
    StageScoreMapper stageScoreMapper;

    @Resource
    GradeMapper gradeMapper;
    @Resource
    TeamMapper teamMapper;

    @Resource
    TeamUserMapper teamUserMapper;


    @Resource
    RoleUserMapper roleUserMapper;




    public int getTeamId(int userid){
        QueryWrapper<Team> wrapper=new QueryWrapper<>();
        QueryWrapper<TeamUser> wrapper1=new QueryWrapper<>();
        wrapper.eq("team_leader_id",userid);
        wrapper1.eq("user_id",userid);
        Team t=teamMapper.selectOne(wrapper);
        TeamUser tm=teamUserMapper.selectOne(wrapper1);
        if(t!=null&&teamMapper.getId(t.getTeamName())!=null){
            return teamMapper.getId(t.getTeamName());
        }else if(tm!=null&&tm.getTeamId()!=null){
            return tm.getTeamId();
        }
        return -1;
    }


    public void excelExport(HttpServletResponse response) throws IOException {
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        //获取到所有学生
        List<StudentMessageDto> stulist = userMapper.selectAllStudent();
        //获取每个学生对应的阶段成绩
        List<StudentGradeDto> gradelist=new ArrayList<>();
        for(StudentMessageDto s:stulist){
            QueryWrapper<StageScore> wrapper1=new QueryWrapper<>();
            wrapper1.eq("user_id",s.getId());
            List<StageScore> stalist=stageScoreMapper.selectList(wrapper1);
            StudentGradeDto studentGradeDto=new StudentGradeDto();
            studentGradeDto.setAccount(s.getAccount());
            studentGradeDto.setUsername(s.getUsername());
            for(StageScore aa:stalist){
                if(aa.getLessonStatusId().equals(2)){
                    studentGradeDto.setA(aa.getCurStatusScore());
                }
                else if(aa.getLessonStatusId().equals(3)){
                    studentGradeDto.setB(aa.getCurStatusScore());
                }
                else if(aa.getLessonStatusId().equals(4)){
                    studentGradeDto.setC(aa.getCurStatusScore());
                }
                else if(aa.getLessonStatusId().equals(5)){
                    studentGradeDto.setD(aa.getCurStatusScore());
                }
                else if(aa.getLessonStatusId().equals(6)){
                    studentGradeDto.setE(aa.getCurStatusScore());
                }
                else if(aa.getLessonStatusId().equals(7)){
                    studentGradeDto.setF(aa.getCurStatusScore());
                }

            }
            QueryWrapper<Grade> gwraper=new QueryWrapper<>();
            gwraper.eq("user_id",s.getId());
            Grade g=gradeMapper.selectOne(gwraper);
            if(g!=null) {
                studentGradeDto.setScore(g.getScore());

            }
            gradelist.add(studentGradeDto);


        }

        //封装字段返回
        String fileName = "学生名单";
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
        ServletOutputStream out = response.getOutputStream();
        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLS,true);
        Sheet sheet = new Sheet(1,0, StudentGradeDto.class);
        //设置自适应宽度
        sheet.setAutoWidth(Boolean.TRUE);
        sheet.setSheetName("学生名单");
        writer.write(gradelist,sheet);
        writer.finish();
        out.flush();
        response.getOutputStream().close();
        out.close();
    }
    public void excelImport(MultipartFile file) throws IOException {
        if(!file.getOriginalFilename().equals("学生名单.xls") && !file.getOriginalFilename().equals("学生名单.xlsx") ){
            return;
        }
        InputStream inputStream = new BufferedInputStream(file.getInputStream());
        //实例化实现了AnalysisEventListener接口的类
        ExcelListener excelListener = new ExcelListener(userMapper,roleUserMapper);
        ExcelReader reader = new ExcelReader(inputStream,null,excelListener);
        //读取信息
        reader.read(new Sheet(1,1,UserDto.class));
    }








}
