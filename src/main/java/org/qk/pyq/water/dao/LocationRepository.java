package org.qk.pyq.water.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.qk.pyq.water.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public interface LocationRepository extends JpaRepository<Location, Integer> {
    // 查询某个水表位置
    Location findWaterLocationByWaterId(Integer waterId);
}
