package com.example.nachrichten.service;

import com.example.nachrichten.enity.Nachrichten;
import com.example.nachrichten.mapper.NachrichtenMapper;
import com.example.util.rsaKey;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NachrichtenService {

    @Resource
    private NachrichtenMapper nachrichtenMapper;

    /**
     * 通知查询数据（分页）
     * @param
     * @return
     */
    public List<Nachrichten> nachrichtenMineTab(Nachrichten nachrichten){
        PageHelper.offsetPage(nachrichten.getStartTab(), nachrichten.getHasTab());
        List<Nachrichten> item = nachrichtenMapper.nachrichtenMineTab(nachrichten);
        return item;
    }

    /**
     * 获取唯一值
     * @param nachrichten
     * @return
     */
    public Nachrichten get(Nachrichten nachrichten){
        return nachrichtenMapper.get(nachrichten);
    }

    /**
     * 删除轮播通知
     * @param nachrichten
     * @return
     */
    public int delNachrichten(Nachrichten nachrichten){
        return nachrichtenMapper.delNachrichten(nachrichten);
    }

    /**
     * 新增轮播通知
     * @param nachrichten
     * @return
     */
    public int insertNachrichten(Nachrichten nachrichten){
        nachrichten.setIds(rsaKey.uuid(null));
        nachrichten.getNowDate(null);
        nachrichten.setDelFlag("0");
        return nachrichtenMapper.insertNachrichten(nachrichten);
    }

    /**
     * 修改轮播通知
     * @param nachrichten
     * @return
     */
    public int updateNachrichten(Nachrichten nachrichten){
        return nachrichtenMapper.updateNachrichten(nachrichten);
    }
}
