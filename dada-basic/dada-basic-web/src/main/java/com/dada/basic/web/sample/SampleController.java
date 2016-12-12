package com.dada.basic.web.sample;

import com.dada.base.config.util.ConfigHolder;
import com.dada.base.core.api.ApiResponse;
import com.dada.base.kafka.KafkaLogger;
import com.dada.base.redis.DadaRedis;
import com.dada.base.registry.consumer.DiscoveryService;
import com.dada.basic.core.entity.dada.AreaBean;
import com.dada.basic.service.dada.AreaService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * Created by cook on 16/9/22.
 */
@Validated
@RequestMapping("/v1/sample")
@ResponseBody
public class SampleController {

    private static final Log log = LogFactory.getLog(SampleController.class);

    @Autowired
    private AreaService areaService;

    @Autowired
    private ConfigHolder config;

    @Autowired
    private DiscoveryService discoveryService;

    @Autowired
    private KafkaLogger kafkaLogger;

    @Autowired
    private DadaRedis dadaRedis;

    @RequestMapping(value = "/getAreaById", method = RequestMethod.GET)
    public ApiResponse getById(@Min(value = 1, message = "areaId必须大于0")
                            @RequestParam("areaId") Long areaId) {
        return ApiResponse.success(areaService.getById(areaId));
    }

    /**
     * 查找例子
     * @param areaName
     * @return
     */
    @RequestMapping(value = "/findAreas", method = RequestMethod.POST)
    public ApiResponse findAreas(@NotEmpty(message = "areaName不能为空")
                                    @RequestParam("areaName") String areaName) {
        return ApiResponse.success(areaService.findAreas(areaName));
    }

    @RequestMapping(value = "/addArea", method = RequestMethod.POST)
    public ApiResponse addArea(@NotNull @RequestBody AreaBean area) {
        log.info("area is " + area);
        int i = areaService.addArea(area);
        return ApiResponse.success(i);
    }

    /**
     * 获取实时配置项, 每一个项目必须分配一个唯一的项目系统名称
     * @param itemName
     * @return
     */
    @RequestMapping(value = "/getDynamicConfigItem", method = RequestMethod.GET)
    public ApiResponse getDynamicConfigItem(@RequestParam("itemName") String itemName) {
        return ApiResponse.success(config.get(itemName));
    }

    @RequestMapping(value = "/getServiceUrl", method = RequestMethod.GET)
    public ApiResponse getServiceUrl() {
        String end = discoveryService.getServiceEndpoint("sample-service", "sample");
        return ApiResponse.success(end);
    }

    /**
     * 记录业务日志, biz_type可以在sysmon.corp.imdada.cn中申请
     * @return
     */
    @RequestMapping(value = "/logBizLog", method = RequestMethod.POST)
    public ApiResponse logBizLog(@RequestParam String logText) {
        kafkaLogger.infoBizLog(100010, logText);
        return ApiResponse.success();
    }

    /**
     * 设置redis的 key - value
     * @return
     */
    @RequestMapping(value = "/setRedisValue", method = RequestMethod.POST)
    public ApiResponse setRedisValue(@RequestParam String key, @RequestParam String value) {
        dadaRedis.addOrUpdate(key, value);
        return ApiResponse.success();
    }

    /**
     * 通过key, 获取redis的value
     * @return
     */
    @RequestMapping(value = "/getRedisValue", method = RequestMethod.GET)
    public ApiResponse getRedisValue(@RequestParam String key) {
        Object v = dadaRedis.get(key);
        log.info("v is " + v);
        return ApiResponse.successMap(key, v);
    }

    public static void main(String[] args) {
        System.out.println("system is basic");
    }

}
