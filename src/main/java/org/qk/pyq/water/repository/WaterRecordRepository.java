package org.qk.pyq.water.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.qk.pyq.water.entity.WaterRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public interface WaterRecordRepository extends JpaRepository<WaterRecord, Integer> {
    //查询某个水表的所有记录。按日期时间倒序排序
    List<WaterRecord> findAllByWaterIdOrderByRecordDateDesc(Integer waterId);

    // 查询某个记录
    WaterRecord findWaterRecordByRecordId(Integer recordId);
}
