package org.qk.pyq.water.service;

import org.qk.pyq.water.dao.LocationRepository;
import org.qk.pyq.water.dao.RecordRepository;
import org.qk.pyq.water.entity.Location;
import org.qk.pyq.water.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecordService {
    final LocationRepository locationRepository;
    final RecordRepository recordRepository;

    public RecordService(LocationRepository locationRepository,
                         RecordRepository recordRepository) {
        this.locationRepository = locationRepository;
        this.recordRepository = recordRepository;
    }

    // 查询所有记录
    public List<Record> retrieveList() {
        return recordRepository.findAll();
    }

    // 查询某个记录
    public List<Record> retrieveOne(Integer recordId) {
        List<Record> recordList = new ArrayList<>();
        recordList.add(recordRepository.findWaterRecordByRecordId(recordId));
        return recordList;
    }

    // 查询某个水表的所有记录，并按日期时间倒序排序
    public List<Record> retrieveOneWaterList(Integer waterId) {
        return recordRepository.findAllByWaterIdOrderByRecordDateDesc(waterId);
    }

    // 查询某个水表的所有记录，并按日期时间倒序排序，分页
    public Page<Record> retrieveOneWaterPage(Integer waterId,
                                             Integer pageSize,
                                             Integer pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return recordRepository.findAllByWaterIdOrderByRecordDateDesc(waterId, pageable);
    }

    // 添加一条记录或更新某条记录
    public List<Record> addOrModifyOne(Integer recordId,
                                       Integer waterId,
                                       Long recordDate,
                                       Double instantUsage) {
        List<Record> recordList = new ArrayList<>();
        Record waterRecord = new Record();
        // 如果提供recordId，说明是修改，找到对应recordId的数据并覆盖；
        // 如果没提供recordId，说明是新增，让数据库自动递增生成新recordId。
        if (recordId != null) {
            waterRecord = recordRepository.findWaterRecordByRecordId(recordId);
        }
        waterRecord.setWaterId(waterId);
        waterRecord.setInstantUsage(instantUsage);
        waterRecord.setRecordDate(recordDate);
        // 保存并刷新
        recordList.add(recordRepository.saveAndFlush(waterRecord));

        /* 计算累计用量 */
        List<Record> recordTmpList = retrieveOneWaterList(waterId);
        Double totalUsage = 0.0;
        // 如果没有记录，累计 = 瞬时；否则遍历累加
        if (recordTmpList.isEmpty())
            totalUsage = instantUsage;
        else {
            for (Record record : recordTmpList) {
                totalUsage = totalUsage + record.getInstantUsage();
            }
        }
        // 读对应waterId的水表
        Location locationToUpdate = locationRepository.findWaterLocationByWaterId(waterId);
        // 修改累计用量
        locationToUpdate.setTotalUsage(totalUsage);
        // 保存并刷新
        locationRepository.save(locationToUpdate);
        return recordList;
    }

    // 删除某个记录
    public void removeOne(Integer recordId) {
        recordRepository.deleteById(recordId);
    }
}
