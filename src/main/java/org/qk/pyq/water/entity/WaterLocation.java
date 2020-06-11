package org.qk.pyq.water.entity;

import javax.persistence.*;

@Entity//实体类（和数据表映射的类）
@Table(name = "water_location")//数据表
public class WaterLocation {
    @Id//主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自动递增
    @Column(name = "water_id")//数据表字段
    private Integer waterId;

    @Column(name = "name")
    private String name;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    public Integer getWaterId() {
        return waterId;
    }

    public void setWaterId(Integer waterId) {
        this.waterId = waterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
