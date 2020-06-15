package org.qk.pyq.water.service;

import org.qk.pyq.water.entity.WaterLocation;
import org.qk.pyq.water.repository.WaterLocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WaterLocationService {
    final WaterLocationRepository waterLocationRepository;

    public WaterLocationService(WaterLocationRepository waterLocationRepository) {
        this.waterLocationRepository = waterLocationRepository;
    }

    // 查询所有水表
    public List<WaterLocation> waterLocationFindAll() {
        return waterLocationRepository.findAll();
    }

    // 查询所有水表并分页
    public Page<WaterLocation> waterLocationFindAllPageable(Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return waterLocationRepository.findAll(pageable);
    }

    // 查询某个水表
    public List<WaterLocation> waterLocationFindOne(Integer waterId) {
        List<WaterLocation> waterLocationList = new ArrayList<>();
        waterLocationList.add(waterLocationRepository.findWaterLocationByWaterId(waterId));
        return waterLocationList;
    }

    // 添加一个水表或更新某个水表
    public List<WaterLocation> waterLocationAddOrUpdateOne(Integer waterId,
                                                           String name,
                                                           Double longitude,
                                                           Double latitude) {
        List<WaterLocation> waterLocationList = new ArrayList<>();
        WaterLocation waterLocation = new WaterLocation();
        // 如果提供waterId，说明是修改，找到对应waterId的数据并覆盖；
        // 如果没提供waterId，说明是新增，让数据库自动递增生成新waterId，并且累计用量是0.0。
        if (waterId != null) {
            waterLocation = waterLocationRepository.findWaterLocationByWaterId(waterId);
        } else {
            waterLocation.setTotalUsage(0.0);
        }
        waterLocation.setName(name);
        waterLocation.setLongitude(longitude);
        waterLocation.setLatitude(latitude);
        waterLocationList.add(waterLocationRepository.saveAndFlush(waterLocation));
        return waterLocationList;
    }

    // 删除某个水表
    public void waterLocationDeleteOne(Integer waterId) {
        waterLocationRepository.deleteById(waterId);
    }
}
