package org.qk.pyq.water.util;

import org.qk.pyq.water.entity.WaterLocation;
import org.qk.pyq.water.service.WaterLocationService;
import org.qk.pyq.water.service.WaterRecordService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
 * 定时给每个水表生成一条随机记录
 */
@Component
@EnableScheduling
public class WaterRecordTimedGenerator {
    final WaterLocationService waterLocationService;
    final WaterRecordService waterRecordService;

    public WaterRecordTimedGenerator(WaterLocationService waterLocationService,
                                     WaterRecordService waterRecordService) {
        this.waterLocationService = waterLocationService;
        this.waterRecordService = waterRecordService;
    }

    // 每分钟的第0秒执行执行一次
    @Scheduled(cron = "0 * * * * ?")
    public void generateNewRandomRecord() {
        // 读取全部水表
        List<WaterLocation> waterLocationList = waterLocationService.waterLocationFindAll();
        // 遍历全部水表的位置信息并添加一条记录，瞬时用量随机生成
        for (WaterLocation waterLocation : waterLocationList) {
            Integer waterId;
            String name;
            double instantUsage;
            StringBuilder stringInstantUsage = new StringBuilder();

            waterId = waterLocation.getWaterId(); // 获取水表编号
            name = waterLocation.getName(); //　获取水表名称

            // 随机生成一个瞬时用量，保留2位小数，四舍五入
            stringInstantUsage.append(String.format("%.2f", Math.random() * 10));
            instantUsage = Double.parseDouble(stringInstantUsage.toString());

            // 日期时间
            Date dateTime = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String stringRecordDate = sdf.format(dateTime);

            // 添加记录
            waterRecordService.waterRecordAddOrUpdateOne(null, waterId, stringRecordDate, instantUsage);

            // 终端输出
            System.out.println(" --- " + stringRecordDate
                + " --- \t已生成随机记录。\t水表编号: " + waterId
                + "\t , 瞬时用量: " + instantUsage
                + "\t , 水表名称: " + name);
        }
    }
}
