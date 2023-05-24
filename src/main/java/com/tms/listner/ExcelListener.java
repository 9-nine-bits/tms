package com.tms.listner;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.tms.dto.UserDto;
import com.tms.entity.RoleUser;
import com.tms.entity.User;
import com.tms.mapper.RoleUserMapper;
import com.tms.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

public class ExcelListener extends AnalysisEventListener<UserDto> {
    private List<UserDto> datas = new ArrayList<>();
    private static final int BATCH_COUNT = 3000;
    private UserMapper userMapper;
    private RoleUserMapper roleUserMapper;

    public ExcelListener(UserMapper userDao, RoleUserMapper roleUserMapper){
        this.userMapper = userDao;
        this.roleUserMapper=roleUserMapper;
    }

    @Override
    public void invoke(UserDto user, AnalysisContext analysisContext) {
        //数据存储到datas，供批量处理，或后续自己业务逻辑处理。
        datas.add(user);
        //达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if(datas.size() >= BATCH_COUNT){
            saveData();
            // 存储完成清理datas
            datas.clear();
        }
    }

    private void saveData() {
        for(UserDto user : datas){
            User user1=new User();
            user1.setAccount(user.getAccount());
            user1.setUsername(user.getUsername());
            user1.setGender("male");
            user1.setPassword("1234");
            user1.setAnswer1("1");
            user1.setAnswer2("1");
            user1.setQuestion2("1");
            user1.setQuestion2("1");
            userMapper.insert(user1);
            Integer userid=userMapper.getId(user.getAccount());
            RoleUser roleUser=new RoleUser();
            roleUser.setUserId(userid);
            roleUser.setRoleId(1);
            roleUserMapper.insert(roleUser);
        }

    }

    public List<UserDto> getDatas() {
        return datas;
    }

    public void setDatas(List<UserDto> datas) {
        this.datas = datas;
    }

    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();//确保所有数据都能入库
    }
}

