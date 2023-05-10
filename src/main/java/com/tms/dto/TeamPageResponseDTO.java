package com.tms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamPageResponseDTO {
    String teamName;
    String teamLeader;
    String teamCapacity;
    String teamCurCapacity;
    String topic;
    List<StudentPageDto> follower;
    public TeamPageResponseDTO(TeamPageDto t, List<StudentPageDto> f) throws IOException, ClassNotFoundException {
        this.teamName=t.getTeamName();
        this.teamCapacity=t.getTeamCapacity();
        this.teamCurCapacity=t.getTeamCurCapacity();
        this.topic=t.getTopic();
        this.teamLeader=t.getTeamLeader();

// 将List对象进行序列化
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(f);
        byte[] data = baos.toByteArray();

// 将序列化后的字节数组反序列化成新的List对象
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        this.follower = (List<StudentPageDto>) ois.readObject();
    }

}
