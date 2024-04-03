package com.airohit.agriculture.module.weather.controller;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.weather.dal.convert.WeatherDay24Convert;
import com.airohit.agriculture.module.weather.dal.convert.WeatherRadarConvert;
import com.airohit.agriculture.module.weather.dal.dataobject.weather.WeatherHour;
import com.airohit.agriculture.module.weather.dal.dataobject.weather.WeatherRadar;
import com.airohit.agriculture.module.weather.service.WeatherService;
import com.airohit.agriculture.module.weather.vo.damage.WeatherDamageVO;
import com.airohit.agriculture.module.weather.vo.day24.WeatherHourBaseVO;
import com.airohit.agriculture.module.weather.vo.day24.WeatherHourVO;
import com.airohit.agriculture.module.weather.vo.futureday.WeatherDaysVO;
import com.airohit.agriculture.module.weather.vo.grid.WeatherGridVO;
import com.airohit.agriculture.module.weather.vo.radar.WeatherRadarVO;
import com.airohit.agriculture.module.weather.vo.rain.WeatherRainVO;
import com.airohit.agriculture.module.weather.vo.today.WeatherVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.text.ParseException;
import java.util.*;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Api(tags = "管理后台 - 天气信息")
@RestController
@RequestMapping("/weather")
@Validated
@Slf4j
public class WeatherController {

    @Resource
    private WeatherService weatherService;

    // 实时 OK
    @GetMapping("/getTodayWeather")
    @ApiOperation("获得天气信息")
    @PermitAll
    public CommonResult<WeatherVO> getTest() {
        WeatherVO weather = weatherService.getTodayWeather();
        return success(weather);
    }

    // 实时 OK
    @GetMapping("/getWeather10")
    @ApiOperation("获得10天气信息")
    @PermitAll
    public CommonResult<List<WeatherDaysVO>> getWeather10() {
        List<WeatherDaysVO> list  = weatherService.getWeather10();
        return success(list);
    }

    // 实时 OK
    @GetMapping("/getWeatherRain")
    @ApiOperation("分钟级降水预报")
    @PermitAll
    public CommonResult<List<WeatherRainVO>> getWeatherRain() {
        List<WeatherRainVO> list  = weatherService.getWeatherRain();
        return success(list);
    }

    // 实时 OK
    @GetMapping("/getWeatherDamage")
    @ApiOperation("天气预警")
    @PermitAll
    public CommonResult<List<WeatherDamageVO>> getWeatherDamage() {
        List<WeatherDamageVO> list  = weatherService.getWeatherDamage();
        return success(list);
    }

    // 实时 OK
    @GetMapping("/getWeatherGrid")
    @ApiOperation("天气1公里网格")
    @PermitAll
    public CommonResult<WeatherGridVO> getWeatherGrid(String lonlat) {
        WeatherGridVO weatherGridVO = weatherService.getWeatherGrid(lonlat);
        return success(weatherGridVO);
    }



    // 历史 定时任务
    @GetMapping("/getWeather24Hour")
    @ApiOperation("获得24小时预报")
    @PermitAll
    public CommonResult<List<WeatherHourVO>> getWeather24Hour() throws ParseException {
        List<WeatherHour> list = weatherService.getWeatherDay24();
        return success(WeatherDay24Convert.INSTANCE.convertList(list));
    }


    // 历史 定时任务
    @GetMapping("/getWeatherRadar")
    @ApiOperation("天气雷达")
    @PermitAll
    public CommonResult<List<WeatherRadarVO>> getWeatherRadar() {
        List<WeatherRadar> list = weatherService.getWeatherRadar();
        if (list == null){
            return success(Collections.emptyList());
        }
        return success(WeatherRadarConvert.INSTANCE.convertList(list));
    }





}
