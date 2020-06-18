package org.qk.pyq.water.service;

import org.qk.pyq.water.dao.LocationRepository;
import org.qk.pyq.water.dao.RecordRepository;
import org.qk.pyq.water.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {
    final LocationRepository locationRepository;
    final RecordRepository recordRepository;

    public LocationService(LocationRepository locationRepository,
                           RecordRepository recordRepository) {
        this.locationRepository = locationRepository;
        this.recordRepository = recordRepository;
    }

    // 查询所有水表
    public List<Location> retrieveList() {
        return locationRepository.findAll();
    }

    // 查询所有水表并分页
    public Page<Location> retrievePage(Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return locationRepository.findAll(pageable);
    }

    // 查询某个水表
    public List<Location> retrieveOne(Integer waterId) {
        List<Location> locationList = new ArrayList<>();
        locationList.add(locationRepository.findWaterLocationByWaterId(waterId));
        return locationList;
    }

    // 添加一个水表或更新某个水表
    public List<Location> addOrModifyOne(Integer waterId,
                                         String name,
                                         Double longitude,
                                         Double latitude) {
        List<Location> locationList = new ArrayList<>();
        Location location = new Location();
        // 如果提供waterId，说明是修改，找到对应waterId的数据并覆盖；
        // 如果没提供waterId，说明是新增，让数据库自动递增生成新waterId，并且累计用量是0.0。
        if (waterId != null) {
            location = locationRepository.findWaterLocationByWaterId(waterId);
        } else {
            location.setTotalUsage(0.0);
        }
        location.setName(name);
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        locationList.add(locationRepository.saveAndFlush(location));
        return locationList;
    }

    // 删除某个水表，同时也删除其所有记录
    public void removeOne(Integer waterId) {
        locationRepository.deleteById(waterId);
        recordRepository.deleteRecordsByWaterId(waterId);
    }
}
