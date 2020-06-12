package org.qk.pyq.water.controller;

import org.qk.pyq.water.entity.WaterLocation;
import org.qk.pyq.water.service.WaterLocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class WaterLocationController {
    final WaterLocationService waterLocationService;

    public WaterLocationController(WaterLocationService waterLocationService) {
        this.waterLocationService = waterLocationService;
    }

    // 查询所有水表
    @GetMapping("/location")
    public List<WaterLocation> waterLocationFindAll() {
        return waterLocationService.waterLocationFindAll();
    }

    // 查询某个水表
    @GetMapping("location/{waterId}")
    public List<WaterLocation> waterLocationFindOne(@PathVariable("waterId") Integer waterId) {
        return waterLocationService.waterLocationFindOne(waterId);
    }

    // 添加水表
    @PostMapping("/location")
    public List<WaterLocation> waterLocationAdd(@RequestParam("name") String name,
                                                @RequestParam("longitude") Double longitude,
                                                @RequestParam("latitude") Double latitude) {
        return waterLocationService.waterLocationAddOrUpdateOne(null, name, longitude, latitude);
    }

    // 更新水表
    @PutMapping("location/{waterId}")
    public List<WaterLocation> waterLocationUpdate(@PathVariable("waterId") Integer waterId,
                                                   @RequestParam("name") String name,
                                                   @RequestParam("longitude") Double longitude,
                                                   @RequestParam("latitude") Double latitude) {
        return waterLocationService.waterLocationAddOrUpdateOne(waterId, name, longitude, latitude);
    }

    // 删除水表
    @DeleteMapping("location/{waterId}")
    public void waterLocationDelete(@PathVariable("waterId") Integer waterId) {
        waterLocationService.waterLocationDeleteOne(waterId);
    }
}
