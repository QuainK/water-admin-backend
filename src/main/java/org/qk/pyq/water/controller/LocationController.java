package org.qk.pyq.water.controller;

import org.qk.pyq.water.entity.Location;
import org.qk.pyq.water.service.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class LocationController {
    final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    // GET 查询所有位置并分页 /locations
    @GetMapping("/locations")
    public Page<Location> getPage(@RequestParam(defaultValue = "0") Integer pageIndex,
                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        System.out.format(" --- 响应 GET 查询所有位置并分页 从0索引第%d页 每页%d项 /locations?pageIndex=%d&pageSize=%d\n",
            pageIndex, pageSize, pageIndex, pageSize);
        return locationService.retrievePage(pageIndex, pageSize);
    }

    // GET 查询所有位置 /locations/-1
    // GET 查询某个位置 /locations/233
    @GetMapping("/locations/{waterId}")
    public List<Location> getListOrOne(@PathVariable("waterId") Integer waterId) {
        if (waterId == -1) {
            System.out.format(" --- 响应 GET 查询所有位置 /locations/-1\n");
            return locationService.retrieveList();
        }
        System.out.format(" --- 响应 GET 查询某个位置 /locations/%d\n", waterId);
        return locationService.retrieveOne(waterId);
    }

    // POST 新增一个位置 /locations?name=HelloWater&longitude=120.123456&latitude=30.123456
    @PostMapping("/locations")
    public List<Location> postOne(@RequestParam("name") String name,
                                  @RequestParam("longitude") Double longitude,
                                  @RequestParam("latitude") Double latitude) {
        System.out.format(" --- 响应 POST 新增一个位置 /locations?name=%s&longitude=%.6f&latitude=%.6f\n",
            name, longitude, latitude);
        return locationService.addOrModifyOne(null, name, longitude, latitude);
    }

    // PUT 更新某个位置 /locations/233?name=HiWater&longitude=-120.123456&latitude=-30.123456
    @PutMapping("locations/{waterId}")
    public List<Location> putOne(@PathVariable("waterId") Integer waterId,
                                 @RequestParam("name") String name,
                                 @RequestParam("longitude") Double longitude,
                                 @RequestParam("latitude") Double latitude) {
        System.out.format(" --- 响应 PUT 更新某个位置 /locations/%d?name=%s&longitude=%.6f&latitude=%.6f\n",
            waterId, name, longitude, latitude);
        return locationService.addOrModifyOne(waterId, name, longitude, latitude);
    }

    // DELETE 删除某个位置 /locations/233
    @DeleteMapping("locations/{waterId}")
    public void deleteOne(@PathVariable("waterId") Integer waterId) {
        System.out.format(" --- 响应 DELETE 删除某个位置 /locations/%d\n", waterId);
        locationService.removeOne(waterId);
    }
}
