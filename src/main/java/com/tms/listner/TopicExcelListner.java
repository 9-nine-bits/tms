package com.tms.listner;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.tms.dto.TopicDto;
import com.tms.dto.UserDto;
import com.tms.entity.RoleUser;
import com.tms.entity.Topic;
import com.tms.entity.User;
import com.tms.mapper.RoleUserMapper;
import com.tms.mapper.TopicMapper;
import com.tms.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;
public class TopicExcelListner extends AnalysisEventListener<TopicDto>{
    private List<TopicDto> datas = new ArrayList<>();
    private static final int BATCH_COUNT = 3000;
    private TopicMapper topicMapper;
    private Integer userid;

    public TopicExcelListner(TopicMapper topicMapper, Integer userid){
       this.topicMapper=topicMapper;
       this.userid=userid;
    }

    @Override
    public void invoke(TopicDto topic, AnalysisContext analysisContext) {
        //数据存储到datas，供批量处理，或后续自己业务逻辑处理。
        datas.add(topic);
        //达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if(datas.size() >= BATCH_COUNT){
            saveData();
            // 存储完成清理datas
            datas.clear();
        }
    }

    private void saveData() {
        for(TopicDto topic : datas){
            Topic t=new Topic();
            t.setTopicName(topic.getTopicName());
            t.setTopicDesc(topic.getTopicDesc());
            t.setTopicCapacity(topic.getTopicCapacity());
            t.setCreateUserId(userid);
            t.setTopicSelectedNum(0);
            t.setIsCheck("1");
            t.setTopicMaxNum(1);
            t.setIsTop("0");
            topicMapper.insert(t);
        }

    }

    public List<TopicDto> getDatas() {
        return datas;
    }

    public void setDatas(List<TopicDto> datas) {
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
