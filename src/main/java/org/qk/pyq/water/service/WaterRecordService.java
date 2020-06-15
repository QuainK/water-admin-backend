package org.qk.pyq.water.service;

import org.qk.pyq.water.entity.WaterLocation;
import org.qk.pyq.water.entity.WaterRecord;
import org.qk.pyq.water.repository.WaterLocationRepository;
import org.qk.pyq.water.repository.WaterRecordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    // 查询某个水表的所有记录，并按日期时间倒序排序，分页
    public Page<WaterRecord> waterRecordFindAllByWaterIdPageable(Integer waterId,
                                                                 Integer pageSize,
                                                                 Integer pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return waterRecordRepository.findAllByWaterIdOrderByRecordDateDesc(waterId, pageable);
    }

    // 添加一条记录或更新某条记录
    public List<WaterRecord> waterRecordAddOrUpdateOne(Integer recordId,
                                                       Integer waterId,
                                                       Long recordDate,
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
        waterRecord.setRecordDate(recordDate);
        // 保存并刷新
        waterRecordList.add(waterRecordRepository.saveAndFlush(waterRecord));

        /* 计算累计用量 */
        List<WaterRecord> waterRecordTmpList = waterRecordFindAllByWaterId(waterId);
        Double totalUsage = 0.0;
        // 如果没有记录，累计 = 瞬时；否则遍历累加
        if (waterRecordTmpList.isEmpty())
            totalUsage = instantUsage;
        else {
            for (WaterRecord record : waterRecordTmpList) {
                totalUsage = totalUsage + record.getInstantUsage();
            }
        }
        // 读对应waterId的水表
        WaterLocation waterLocationToUpdate = waterLocationRepository.findWaterLocationByWaterId(waterId);
        // 修改累计用量
        waterLocationToUpdate.setTotalUsage(totalUsage);
        // 保存并刷新
        waterLocationRepository.save(waterLocationToUpdate);
        return waterRecordList;
    }

    // 删除某个记录
    public void waterRecordDeleteOne(Integer recordId) {
        waterRecordRepository.deleteById(recordId);
    }
}
