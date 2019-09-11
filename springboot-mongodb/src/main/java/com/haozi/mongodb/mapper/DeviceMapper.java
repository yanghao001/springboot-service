package com.haozi.mongodb.mapper;

import com.haozi.mongodb.model.DeviceInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hao.yang
 * @date 2019/8/2
 */
@Mapper
public interface DeviceMapper {

    /**
     *
     * 查询桩是否在充电中
     *
     * */
    Integer findAvailableByDeviceNumber(String deviceNumber);

    /**
     *
     * 查询桩的设备信息
     *
     * */
    DeviceInfo findDeviceInfoByDeviceNumber(String deviceNumber);

}
