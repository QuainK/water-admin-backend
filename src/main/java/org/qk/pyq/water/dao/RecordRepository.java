package org.qk.pyq.water.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.qk.pyq.water.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public interface RecordRepository extends JpaRepository<Record, Integer> {
    // 查询某个水表的所有记录并按日期倒序排序
    List<Record> findAllByWaterIdOrderByRecordDateDesc(Integer waterId);

    // 查询某个水表的所有记录并按日期倒序排序，分页
    Page<Record> findAllByWaterIdOrderByRecordDateDesc(Integer waterId, Pageable pageable);

    // 查询某个记录
    Record findWaterRecordByRecordId(Integer recordId);

    // 删除某个水表的所有记录
    @Transactional
    void deleteRecordsByWaterId(Integer waterId);
}
