package com.airohit.agriculture.module.weather;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.weather.dal.convert.WeatherDay24Convert;
import com.airohit.agriculture.module.weather.dal.convert.WeatherRadarConvert;
import com.airohit.agriculture.module.weather.dal.dataobject.weather.WeatherHour;
import com.airohit.agriculture.module.weather.dal.dataobject.weather.WeatherRadar;
import com.airohit.agriculture.module.weather.service.WeatherService;
import com.airohit.agriculture.module.weather.vo.damage.WeatherDamageVO;
import com.airohit.agriculture.module.weather.vo.day24.WeatherHourVO;
import com.airohit.agriculture.module.weather.vo.futureday.WeatherDaysVO;
import com.airohit.agriculture.module.weather.vo.grid.WeatherGridVO;
import com.airohit.agriculture.module.weather.vo.radar.WeatherRadarVO;
import com.airohit.agriculture.module.weather.vo.rain.WeatherRainVO;
import com.airohit.agriculture.module.weather.vo.today.WeatherVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.util.Date;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;


@Api(tags = "app - 天气信息")
@RestController
@RequestMapping("/app/weather")
@Validated
@Slf4j
// 象辑天气APi
public class WeatherController {


    @Resource
    private WeatherService weatherService;

    @GetMapping("/getTodayWeather")
    @ApiOperation("获得天气信息")
    @PermitAll
    public CommonResult<WeatherVO> getTest() {
        WeatherVO weather = weatherService.getTodayWeather();
        return success(weather);
    }

    @GetMapping("/getWeather10")
    @ApiOperation("获得10天气信息")
    @PermitAll
    public CommonResult<List<WeatherDaysVO>> getWeather10() {
        List<WeatherDaysVO> list  = weatherService.getWeather10();
        return success(list);
    }

    @GetMapping("/getWeatherRain")
    @ApiOperation("分钟级降水预报")
    @PermitAll
    public CommonResult<List<WeatherRainVO>> getWeatherRain() {
        List<WeatherRainVO> list  = weatherService.getWeatherRain();
        return success(list);
    }

    @GetMapping("/getWeatherDamage")
    @ApiOperation("天气预警")
    @PermitAll
    public CommonResult<List<WeatherDamageVO>> getWeatherDamage() {
        List<WeatherDamageVO> list  = weatherService.getWeatherDamage();
        return success(list);
    }

    @GetMapping("/getWeatherGrid")
    @ApiOperation("天气1公里网格")
    @PermitAll
    public CommonResult<WeatherGridVO> getWeatherGrid(String lonlat) {
        WeatherGridVO weatherGridVO = weatherService.getWeatherGrid(lonlat);
        return success(weatherGridVO);
    }



    @GetMapping("/getWeather24Hour")
    @ApiOperation("获得24小时预报")
    @PermitAll
    public CommonResult<List<WeatherHourVO>> getWeather24Hour() {
        List<WeatherHour> list = weatherService.getWeatherDay24();
        return success(WeatherDay24Convert.INSTANCE.convertList(list));
    }

    @GetMapping("/getWeatherRadar")
    @ApiOperation("天气雷达")
    @PermitAll
    public CommonResult<List<WeatherRadarVO>> getWeatherRadar(){
        List<WeatherRadar> list = weatherService.getWeatherRadar();
        return success(WeatherRadarConvert.INSTANCE.convertList(list));
    }



}
