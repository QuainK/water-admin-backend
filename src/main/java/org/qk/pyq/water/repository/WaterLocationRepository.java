package org.qk.pyq.water.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.qk.pyq.water.entity.WaterLocation;
import org.springframework.data.jpa.repository.JpaRepository;

@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public interface WaterLocationRepository extends JpaRepository<WaterLocation, Integer> {
}
