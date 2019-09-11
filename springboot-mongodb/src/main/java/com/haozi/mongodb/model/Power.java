package com.haozi.mongodb.model;

import lombok.Data;

/**
 * @author hao.yang
 * @date 2019/8/6
 */
@Data
public class Power {

    private Integer id;

    private Double dropStopPower; // 可降可停站点平均功率

    private Double dropNoStopPower; // 可降不可停站点平均功率

    private Double noDropNoStopPower; // 不可降不可停站点平均功率

    private Double sumPower; // 总功率
}
