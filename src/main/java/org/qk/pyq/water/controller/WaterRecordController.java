package org.qk.pyq.water.controller;

import org.qk.pyq.water.entity.WaterRecord;
import org.qk.pyq.water.service.WaterRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class WaterRecordController {
    final WaterRecordService waterRecordService;

    public WaterRecordController(WaterRecordService waterRecordService) {
        this.waterRecordService = waterRecordService;
    }

    // 查询所有记录 /record
    // 查询某条记录 /record?recordId=1024
    @GetMapping("/record")
    public List<WaterRecord> waterRecordFind(@RequestParam(name = "recordId", required = false) Integer recordId) {
        if (recordId == null) return waterRecordService.waterRecordFindAll();
        return waterRecordService.waterRecordFindOne(recordId);
    }

    // 查询某个水表的所有记录
    @GetMapping("/record/{waterId}")
    public List<WaterRecord> waterRecordFindAllByWaterId(@PathVariable("waterId") Integer waterId) {
        return waterRecordService.waterRecordFindAllByWaterId(waterId);
    }

    // 添加记录
    @PostMapping("/record")
    public List<WaterRecord> waterRecordAdd(@RequestParam("waterId") Integer waterId,
                                            @RequestParam("recordDate") String recordDate,
                                            @RequestParam("instantUsage") Double instantUsage) {
        return waterRecordService.waterRecordAddOrUpdateOne(null, waterId, recordDate, instantUsage);
    }

    // 更新记录
    @PutMapping("record/{recordId}")
    public List<WaterRecord> waterRecordUpdate(@PathVariable("recordId") Integer recordId,
                                               @RequestParam("waterId") Integer waterId,
                                               @RequestParam("recordDate") String recordDate,
                                               @RequestParam("instantUsage") Double instantUsage) {
        return waterRecordService.waterRecordAddOrUpdateOne(recordId, waterId, recordDate, instantUsage);
    }

    // 删除记录
    @DeleteMapping("/record/{recordId}")
    public void deleteWaterRecord(@PathVariable("recordId") Integer recordId) {
        waterRecordService.waterRecordDeleteOne(recordId);
    }
}
