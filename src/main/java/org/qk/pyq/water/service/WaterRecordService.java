package org.qk.pyq.water.service;

import org.qk.pyq.water.entity.WaterRecord;
import org.qk.pyq.water.repository.WaterLocationRepository;
import org.qk.pyq.water.repository.WaterRecordRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class WaterRecordService {
    final WaterRecordRepository waterRecordRepository;
    final WaterLocationRepository waterLocationRepository;

    public WaterRecordService(WaterRecordRepository waterRecordRepository,
                              WaterLocationRepository waterLocationRepository) {
        this.waterRecordRepository = waterRecordRepository;
        this.waterLocationRepository = waterLocationRepository;
    }

    // 查询所有记录
    public List<WaterRecord> waterRecordFindAll() {
        return waterRecordRepository.findAll();
    }

    // 查询某个记录
    public List<WaterRecord> waterRecordFindOne(Integer recordId) {
        List<WaterRecord> waterRecordList = new ArrayList<>();
        waterRecordList.add(waterRecordRepository.findWaterRecordByRecordId(recordId));
        return waterRecordList;
    }

    // 查询某个水表的所有记录，并按日期时间倒序排序
    public List<WaterRecord> waterRecordFindAllByWaterId(Integer waterId) {
        return waterRecordRepository.findAllByWaterIdOrderByRecordDateDesc(waterId);
    }

    // 添加一条记录或更新某条记录
    public List<WaterRecord> waterRecordAddOrUpdateOne(Integer recordId,
                                                       Integer waterId,
                                                       String recordDate,
                                                       Double instantUsage) {
        List<WaterRecord> waterRecordList = new ArrayList<>();
        WaterRecord waterRecord = new WaterRecord();
        // 如果提供recordId，说明是修改，找到对应recordId的数据并覆盖；
        // 如果没提供recordId，说明是新增，让数据库自动递增生成新recordId。
        if (recordId != null) {
            waterRecord = waterRecordRepository.findWaterRecordByRecordId(recordId);
        }
        waterRecord.setWaterId(waterId);
        waterRecord.setInstantUsage(instantUsage);
        // 日期时间
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            waterRecord.setRecordDate(sdf.parse(recordDate));
        } catch (ParseException e) {
            System.err.println("日期转换时出错");
            e.printStackTrace();
        }
        // 保存并刷新
        waterRecordList.add(waterRecordRepository.saveAndFlush(waterRecord));
        return waterRecordList;
    }

    // 删除某条记录
    public void waterRecordDeleteOne(Integer recordId) {
        waterRecordRepository.deleteById(recordId);
    }
}
