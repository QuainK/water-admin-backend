package org.qk.pyq.water.controller;

import org.qk.pyq.water.entity.Record;
import org.qk.pyq.water.service.RecordService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class RecordController {
    final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    // GET 查询所有记录 /records
    // GET 查询某个记录 /records?recordId=1024
    @GetMapping("/records")
    public List<Record> getListOrOne(
        @RequestParam(name = "recordId", required = false) Integer recordId) {
        if (recordId == null) {
            System.out.format(" --- 响应 GET 查询所有记录 /records\n");
            return recordService.retrieveList();
        } else {
            System.out.format(" --- 响应 GET 查询某个记录 /records?recordId=%d\n", recordId);
            return recordService.retrieveOne(recordId);
        }
    }

    // GET 查询某个水表的所有记录 /records/233/all
    @GetMapping("/records/{waterId}/all")
    public List<Record> getOneWaterList(@PathVariable("waterId") Integer waterId) {
        System.out.format(" --- 响应 GET 查询某个水表的所有记录 /records/%d/all\n", waterId);
        return recordService.retrieveOneWaterList(waterId);
    }

    // GET 查询某个水表的所有记录 /records/233?pageIndex=0&pageSize=10
    @GetMapping("/records/{waterId}")
    public Page<Record> getOneWaterPage(
        @PathVariable("waterId") Integer waterId,
        @RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        System.out.format(" --- 响应 GET 查询某个水表的所有记录 从0索引第%d页 每页%d项"
                + " /records/%d/pages?pageIndex=%d&pageSize=%d\n",
            pageIndex, pageSize, waterId, pageIndex, pageSize);
        return recordService.retrieveOneWaterPage(waterId, pageSize, pageIndex);
    }

    // POST 新增某条记录 /records?waterId=233&recordDate=1588262400&instantUsage=98.76
    @PostMapping("/records")
    public List<Record> postOne(@RequestParam("waterId") Integer waterId,
                                @RequestParam("recordDate") Long recordDate,
                                @RequestParam("instantUsage") Double instantUsage) {
        System.out.format(" --- 响应 PUT 增加某条记录 /records?waterId=%d&recordDate=%d&instantUsage=%.6f\n",
            waterId, recordDate, instantUsage);
        return recordService.addOrModifyOne(null, waterId, recordDate, instantUsage);
    }

    // PUT 更新某条记录 /records/1024?waterId=233&recordDate=1588262400&instantUsage=98.76
    @PutMapping("/records/{recordId}")
    public List<Record> putOne(@PathVariable("recordId") Integer recordId,
                               @RequestParam("waterId") Integer waterId,
                               @RequestParam("recordDate") Long recordDate,
                               @RequestParam("instantUsage") Double instantUsage) {
        System.out.format(" --- 响应 PUT 更新某条记录 /records/%d?waterId=%d&recordDate=%d&instantUsage=%.6f\n",
            recordId, waterId, recordDate, instantUsage);
        return recordService.addOrModifyOne(recordId, waterId, recordDate, instantUsage);
    }

    // DELETE 删除记录 /records/1024
    @DeleteMapping("/records/{recordId}")
    public void deleteOne(@PathVariable("recordId") Integer recordId) {
        System.out.format(" --- 响应 DELETE 删除记录 /records/%d\n", recordId);
        recordService.removeOne(recordId);
    }
}
