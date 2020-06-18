package org.qk.pyq.water.entity;

import javax.persistence.*;

@Entity//实体类（和数据表映射的类）
@Table(name = "water_record")//数据表
public class Record {
    @Id//主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自动递增
    @Column(name = "record_id")//数据表字段
    private Integer recordId;

    @Column(name = "water_id")
    private Integer waterId;

    @Column(name = "record_date")
    private Long recordDate;

    @Column(name = "instant_usage")
    private Double instantUsage;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getWaterId() {
        return waterId;
    }

    public void setWaterId(Integer waterId) {
        this.waterId = waterId;
    }

    public Long getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Long recordDate) {
        this.recordDate = recordDate;
    }

    public Double getInstantUsage() {
        return instantUsage;
    }

    public void setInstantUsage(Double instantUsage) {
        this.instantUsage = instantUsage;
    }
}
