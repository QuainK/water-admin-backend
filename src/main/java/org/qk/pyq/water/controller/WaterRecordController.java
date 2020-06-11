package org.qk.pyq.water.controller;

import org.qk.pyq.water.entity.WaterRecord;
import org.qk.pyq.water.repository.WaterRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin("*")
@RestController
public class WaterRecordController {
    @Autowired
    WaterRecordRepository waterRecordRepository;

    // 查询所有记录
    @GetMapping("/record")
    public List<WaterRecord> waterRecordFindAll() {
        return waterRecordRepository.findAll();
    }

    // 查询某个水表的所有记录
    @GetMapping("/record/{waterId}")
    public List<WaterRecord> waterRecordFindAllByWaterId(@PathVariable("waterId") Integer waterId) {
        return waterRecordRepository.findAllByWaterIdOrderByRecordDateDesc(waterId);
    }

    // 添加记录
    @PostMapping("/record")
    public WaterRecord waterRecordAdd(@RequestParam("waterId") Integer waterId,
                                      @RequestParam("recordDate") String recordDate,
                                      @RequestParam("instantUsage") Double instantUsage) {
        WaterRecord myWaterRecord = new WaterRecord();
        waterRecordChange(myWaterRecord, waterId, recordDate, instantUsage);
        return waterRecordRepository.save(myWaterRecord);
    }

    // 更新记录
    @PutMapping("record/{recordId}")
    public WaterRecord waterRecordUpdate(@PathVariable("recordId") Integer recordId,
                                         @RequestParam("waterId") Integer waterId,
                                         @RequestParam("recordDate") String recordDate,
                                         @RequestParam("instantUsage") Double instantUsage) {
        WaterRecord myWaterRecord = new WaterRecord();
        waterRecordChange(myWaterRecord, waterId, recordDate, instantUsage);
        myWaterRecord.setRecordId(recordId);
        return waterRecordRepository.save(myWaterRecord);
    }

    // 删除记录
    @DeleteMapping("/record/{recordId}")
    public void deleteWaterRecord(@PathVariable("recordId") Integer recordId) {
        waterRecordRepository.deleteById(recordId);
    }

    // 修改记录（包括添加和更新）
    private void waterRecordChange(WaterRecord myWaterRecord,
                                   Integer waterId,
                                   String recordDate,
                                   Double instantUsage) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            myWaterRecord.setRecordDate(sdf.parse(recordDate));
        } catch (ParseException e) {
            System.err.println("日期转换时出错");
            e.printStackTrace();
        }
        myWaterRecord.setWaterId(waterId);
        myWaterRecord.setInstantUsage(instantUsage);
        myWaterRecord.setTotalUsage(instantUsage);
    }
}
