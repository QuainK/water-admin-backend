package org.qk.pyq.water.controller;

import org.qk.pyq.water.entity.WaterLocation;
import org.qk.pyq.water.repository.WaterLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class WaterLocationController {
    @Autowired
    WaterLocationRepository waterLocationRepository;

    // 查询所有水表
    @GetMapping("/location")
    public List<WaterLocation> waterLocationFindAll() {
        return waterLocationRepository.findAll();
    }

    // 添加水表
    @PostMapping("/location")
    public WaterLocation waterLocationAdd(@RequestParam("name") String name,
                                          @RequestParam("longitude") Double longitude,
                                          @RequestParam("latitude") Double latitude) {
        WaterLocation myWaterLocation = new WaterLocation();
        waterLocationChange(myWaterLocation, name, longitude, latitude);
        return waterLocationRepository.save(myWaterLocation);
    }

    // 更新水表
    @PutMapping("location/{waterId}")
    public WaterLocation waterLocationUpdate(@PathVariable("waterId") Integer waterId,
                                             @RequestParam("name") String name,
                                             @RequestParam("longitude") Double longitude,
                                             @RequestParam("latitude") Double latitude) {
        WaterLocation myWaterLocation = new WaterLocation();
        waterLocationChange(myWaterLocation, name, longitude, latitude);
        myWaterLocation.setWaterId(waterId);
        return waterLocationRepository.save(myWaterLocation);
    }

    // 删除水表
    @DeleteMapping("location/{waterId}")
    public void waterLocationDelete(@PathVariable("waterId") Integer waterId) {
        waterLocationRepository.deleteById(waterId);

    }

    // 修改水表（包括添加和更新）
    private void waterLocationChange(WaterLocation myWaterLocation,
                                     String name,
                                     Double longitude,
                                     Double latitude) {
        myWaterLocation.setName(name);
        myWaterLocation.setLongitude(longitude);
        myWaterLocation.setLatitude(latitude);
    }
}
