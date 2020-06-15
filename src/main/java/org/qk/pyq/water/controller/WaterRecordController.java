package org.qk.pyq.water.controller;

import org.qk.pyq.water.entity.WaterRecord;
import org.qk.pyq.water.service.WaterRecordService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class WaterRecordController {
    final WaterRecordService waterRecordService;

    public WaterRecordController(WaterRecordService waterRecordService) {
        this.waterRecordService = waterRecordService;
    }

    // GET 查询所有记录 /records
    // GET 查询某个记录 /records?recordId=1024
    @GetMapping("/records")
    public List<WaterRecord> waterRecordGetAllOrOne(
        @RequestParam(name = "recordId", required = false) Integer recordId) {
        if (recordId == null) {
            System.out.format(" --- 响应 GET 查询所有记录 /records\n");
            return waterRecordService.waterRecordFindAll();
        } else {
            System.out.format(" --- 响应 GET 查询某个记录 /records?recordId=%d\n", recordId);
            return waterRecordService.waterRecordFindOne(recordId);
        }
    }

    // GET 查询某个水表的所有记录 /records/233/all
    @GetMapping("/records/{waterId}/all")
    public List<WaterRecord> waterRecordGetAllByWaterId(@PathVariable("waterId") Integer waterId) {
        System.out.format(" --- 响应 GET 查询某个水表的所有记录 /records/%d/all\n", waterId);
        return waterRecordService.waterRecordFindAllByWaterId(waterId);
    }

    // GET 查询某个水表的所有记录 /records/233?pageIndex=0&pageSize=10
    @GetMapping("/records/{waterId}")
    public Page<WaterRecord> waterRecordGetAllByWaterIdPageable(
        @PathVariable("waterId") Integer waterId,
        @RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        System.out.format(" --- 响应 GET 查询某个水表的所有记录 从0索引第%d页 每页%d项"
                + " /records/%d/pages?pageIndex=%d&pageSize=%d\n",
            pageIndex, pageSize, waterId, pageIndex, pageSize);
        return waterRecordService.waterRecordFindAllByWaterIdPageable(waterId, pageSize, pageIndex);
    }

    // POST 新增某条记录 /records?waterId=233&recordDate=1588262400&instantUsage=98.76
    @PostMapping("/records")
    public List<WaterRecord> waterRecordPostOne(@RequestParam("waterId") Integer waterId,
                                               @RequestParam("recordDate") Long recordDate,
                                               @RequestParam("instantUsage") Double instantUsage) {
        System.out.format(" --- 响应 PUT 增加某条记录 /records?waterId=%d&recordDate=%d&instantUsage=%.6f\n",
            waterId, recordDate, instantUsage);
        return waterRecordService.waterRecordAddOrUpdateOne(null, waterId, recordDate, instantUsage);
    }

    // PUT 更新某条记录 /records/1024?waterId=233&recordDate=1588262400&instantUsage=98.76
    @PutMapping("/records/{recordId}")
    public List<WaterRecord> waterRecordPutOne(@PathVariable("recordId") Integer recordId,
                                                  @RequestParam("waterId") Integer waterId,
                                                  @RequestParam("recordDate") Long recordDate,
                                                  @RequestParam("instantUsage") Double instantUsage) {
        System.out.format(" --- 响应 PUT 更新某条记录 /records/%d?waterId=%d&recordDate=%d&instantUsage=%.6f\n",
            recordId, waterId, recordDate, instantUsage);
        return waterRecordService.waterRecordAddOrUpdateOne(recordId, waterId, recordDate, instantUsage);
    }

    // DELETE 删除记录 /records/1024
    @DeleteMapping("/records/{recordId}")
    public void waterRecordDeleteOne(@PathVariable("recordId") Integer recordId) {
        System.out.format(" --- 响应 DELETE 删除记录 /records/%d\n", recordId);
        waterRecordService.waterRecordDeleteOne(recordId);
    }
}
