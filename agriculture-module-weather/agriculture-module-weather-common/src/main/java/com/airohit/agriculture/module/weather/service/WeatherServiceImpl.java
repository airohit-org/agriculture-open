package com.airohit.agriculture.module.weather.service;

import cn.hutool.http.HttpUtil;
import com.airohit.agriculture.framework.tenant.core.aop.FarmTenantIgnore;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.framework.tenant.core.context.FarmTenantContextHolder;
import com.airohit.agriculture.module.infra.api.file.FileApi;
import com.airohit.agriculture.module.weather.dal.dataobject.weather.FarmDO;
import com.airohit.agriculture.module.weather.dal.dataobject.weather.WeatherHour;
import com.airohit.agriculture.module.weather.dal.dataobject.weather.WeatherRadar;
import com.airohit.agriculture.module.weather.dal.mysql.weather.WeatherFarmMapper;
import com.airohit.agriculture.module.weather.dal.mysql.weather.WeatherHourMapper;
import com.airohit.agriculture.module.weather.dal.mysql.weather.WeatherRadarMapper;
import com.airohit.agriculture.module.weather.vo.day24.WeatherHourBaseVO;
import com.airohit.agriculture.module.weather.vo.day24.WeatherHourVO;
import com.airohit.agriculture.module.weather.vo.grid.WeatherGridVO;
import com.airohit.agriculture.module.weather.vo.damage.WeatherDamageVO;
import com.airohit.agriculture.module.weather.vo.radar.WeatherRadarVO;
import com.airohit.agriculture.module.weather.vo.rain.WeatherRainVO;
import com.airohit.agriculture.module.weather.vo.futureday.WeatherDaysBaseVO;
import com.airohit.agriculture.module.weather.vo.futureday.WeatherDaysVO;
import com.airohit.agriculture.module.weather.vo.rain.WeatherRainVoTemp;
import com.airohit.agriculture.module.weather.vo.today.Daily;
import com.airohit.agriculture.module.weather.vo.today.Hourly;
import com.airohit.agriculture.module.weather.vo.today.WeatherVO;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.time.LocalDateTime.from;


@Service
public class WeatherServiceImpl implements WeatherService {

    @Resource
    private WeatherHourMapper weatherHourMapper;

    @Resource
    private WeatherRadarMapper weatherRadarMapper;

    @Resource
    private FileApi fileApi;

    @Resource
    private WeatherFarmMapper weatherFarmMapper;

    @Value("${weather.key}")
    private String key;

    @Value("${weather.today.url_15}")
    private String url_15;
    @Value("${weather.today.url_240}")
    private String url_240;
    @Value("${weather.today.hour}")
    private String hour;

    @Value("${weather.damage.damage_url}")
    private String damage_url;

    @Value("${weather.day24.url_24}")
    private String url_24;
    @Value("${weather.day24.hour_24}")
    private String hour_24;

    @Value("${weather.future.url_future}")
    private String url_future;

    @Value("${weather.grid.grid_url}")
    private String grid_url;

    @Value("${weather.radar.radar_url}")
    private String radar_url;

    @Value("${weather.rain.url_rain}")
    private String url_rain;
    @Value("${weather.rain.url_hour}")
    private String url_hour;
    @Value("${weather.rain.url_rain_hour}")
    private String url_rain_hour;



    @Value("${weatherDate.todayWeather.dailyString}")
    private String todayDailyString;
    @Value("${weatherDate.todayWeather.hourlyString}")
    private String hourlyString;

    @Value("${weatherDate.Weather10.dailyString}")
    private String dailyString;

    @Value("${weatherDate.rain.result}")
    private String result;
    @Value("${weatherDate.rain.hourlyString}")
    private String rainHourlyString;
    @Value("${weatherDate.rain.series}")
    private String series;

    @Value("${weatherDate.damage.alerts}")
    private String alerts;

    @Value("${weatherDate.grid.a}")
    private String a;

    @Value("${weatherDate.weather24.dailyString}")
    private String day24DailyString;

    @Value("${weather.state}")
    private Boolean state;



    @Override
    @TenantIgnore
    @FarmTenantIgnore
    // ok
    public WeatherVO getTodayWeather() {

        WeatherVO weatherVO = new WeatherVO();

        if (!state){
            JSONArray daily_jsonArray = JSONArray.parseArray(todayDailyString);
            Object[] daily_array = daily_jsonArray.toArray();
            for (Object o : daily_array){
                Gson gson = new Gson();
                Daily daily = gson.fromJson(o.toString(),Daily.class);
                weatherVO.setMinimumTemperature(daily.getLow());
                weatherVO.setMaximumTemperature(daily.getHigh());
            }

            JSONArray hourly_jsonArray = JSONArray.parseArray(hourlyString);
            Object[] hourly_array = hourly_jsonArray.toArray();
            for (Object o : hourly_array){
                Gson gson_hour = new Gson();
                Hourly hourly = gson_hour.fromJson(o.toString(),Hourly.class);
                weatherVO.setWeatherName(hourly.getText());
                weatherVO.setWeatherCode(hourly.getCode());
                weatherVO.setTemperature(hourly.getTemp_fc());
                weatherVO.setWindDirection(hourly.getWind_dir());
                weatherVO.setWindSpeed(hourly.getWind_dir());
            }

            return weatherVO;
        }

        Long id= FarmTenantContextHolder.getFarmTenantId();
        FarmDO farmDO = weatherFarmMapper.selectById(id);
        String latlon = farmDO.getCoordinateCenter();
        String[] farmLonlats = latlon.split(",");
        String lat = farmLonlats[0];
        String lon = farmLonlats[1];
        String lonlat = lon + "," + lat;

        String api_day= url_15 + lonlat + key;
        String api_hour= url_240 + lonlat +hour + key;

        JSONObject jsonObject_day = JSONObject.parseObject(HttpUtil.get(api_day));
        String dailyString  = ((JSONObject) jsonObject_day.get("result")).get("daily_fcsts").toString();

        JSONArray daily_jsonArray = JSONArray.parseArray(dailyString);
        Object[] daily_array = daily_jsonArray.toArray();
        for (Object o : daily_array){
            Gson gson = new Gson();
            Daily daily = gson.fromJson(o.toString(),Daily.class);
            weatherVO.setMinimumTemperature(daily.getLow());
            weatherVO.setMaximumTemperature(daily.getHigh());
        }

        JSONObject jsonObject_hour = JSONObject.parseObject(HttpUtil.get(api_hour));
        String hourlyString  = ((JSONObject) jsonObject_hour.get("result")).get("grid_hourly").toString();

        JSONArray hourly_jsonArray = JSONArray.parseArray(hourlyString);
        Object[] hourly_array = hourly_jsonArray.toArray();
        for (Object o : hourly_array){
            Gson gson_hour = new Gson();
            Hourly hourly = gson_hour.fromJson(o.toString(),Hourly.class);
            weatherVO.setWeatherName(hourly.getText());
            weatherVO.setWeatherCode(hourly.getCode());
            weatherVO.setTemperature(hourly.getTemp_fc());
            weatherVO.setWindDirection(hourly.getWind_dir());
            weatherVO.setWindSpeed(hourly.getWind_dir());
        }

        return weatherVO;
    }


    // 实时 获得10天气信息
    @Override
    @TenantIgnore
    @FarmTenantIgnore
    // ok
    public List<WeatherDaysVO> getWeather10() {

        List<WeatherDaysVO> list = new ArrayList<>();

        if (!state) {
            JSONArray daily_jsonArray = JSONArray.parseArray(dailyString);
            Object[] daily_array = daily_jsonArray.toArray();
            for (Object o : daily_array) {
                Gson gson = new Gson();
                WeatherDaysBaseVO daily = gson.fromJson(o.toString(), WeatherDaysBaseVO.class);
                WeatherDaysVO weatherDaysVO = new WeatherDaysVO();
                weatherDaysVO.setMinimumTemperature(daily.getLow());
                weatherDaysVO.setMaximumTemperature(daily.getHigh());
                weatherDaysVO.setWeatherDate(daily.getDate().substring(5));
                list.add(weatherDaysVO);
            }
            return list;
        }

        Long id= FarmTenantContextHolder.getFarmTenantId();
        //System.out.println(id);
        FarmDO farmDO = weatherFarmMapper.selectById(id);
        //System.out.println(farmDO);
        String latlon = farmDO.getCoordinateCenter();
        String[] farmLonlats = latlon.split(",");
        String lat = farmLonlats[0];
        String lon = farmLonlats[1];
        String lonlat = lon + "," + lat;

        String api_day= url_future + lonlat + key;
        //System.out.println(api_day);

        // 15天
        JSONObject jsonObject_day = JSONObject.parseObject(HttpUtil.get(api_day));
        String dailyString  = ((JSONObject) jsonObject_day.get("result")).get("daily_fcsts").toString();

        JSONArray daily_jsonArray = JSONArray.parseArray(dailyString);
        Object[] daily_array = daily_jsonArray.toArray();
        for (Object o : daily_array){
            Gson gson = new Gson();
            WeatherDaysBaseVO daily = gson.fromJson(o.toString(), WeatherDaysBaseVO.class);
            WeatherDaysVO weatherDaysVO = new WeatherDaysVO();
            weatherDaysVO.setMinimumTemperature(daily.getLow());
            weatherDaysVO.setMaximumTemperature(daily.getHigh());
            weatherDaysVO.setWeatherDate(daily.getDate().substring(5));
            list.add(weatherDaysVO);
        }

        return list;
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    //ok
    public List<WeatherRainVO> getWeatherRain() {

        if (!state){
            JSONArray hourly_jsonArray = JSONArray.parseArray(rainHourlyString);

            Object[] hourly_array = hourly_jsonArray.toArray();
            WeatherRainVoTemp temp = new WeatherRainVoTemp();
            for (Object o : hourly_array){
                Gson gson_hour = new Gson();
                temp = gson_hour.fromJson(o.toString(),WeatherRainVoTemp.class);
            }

            List<WeatherRainVO> list = new ArrayList<>();
            LocalDateTime baseTime = LocalDateTime.now();
            Gson gson_result = new Gson();
            JSONArray jsonArray = JSONArray.parseArray(series);
            Object[] array = jsonArray.toArray();
            int i = 0;
            for (Object o : array){
                WeatherRainVO weatherRainVO = gson_result.fromJson(result, WeatherRainVO.class);
                weatherRainVO.setTempFc(temp.getTemp_fc());

                weatherRainVO.setDataTime(String.valueOf(baseTime.plusMinutes(i)));
                weatherRainVO.setRain(String.valueOf(o));
                i = i+1;
                list.add(weatherRainVO);
            }
            return list;
        }

        Long id= FarmTenantContextHolder.getFarmTenantId();
        FarmDO farmDO = weatherFarmMapper.selectById(id);
        String latlon = farmDO.getCoordinateCenter();
        String[] farmLonlats = latlon.split(",");
        String lat = farmLonlats[0];
        String lon = farmLonlats[1];
        String lonlat = lon + "," + lat;

        String api_hour = url_hour + lonlat +  url_rain_hour + key;
        String api = url_rain +  lonlat + key;

//        System.out.println(api_hour);
//        System.out.println(api);

        JSONObject jsonObject_hour = JSONObject.parseObject(HttpUtil.get(api_hour));
        String hourlyString  = ((JSONObject) jsonObject_hour.get("result")).get("grid_hourly").toString();

        JSONArray hourly_jsonArray = JSONArray.parseArray(hourlyString);

        Object[] hourly_array = hourly_jsonArray.toArray();
        WeatherRainVoTemp temp = new WeatherRainVoTemp();
        for (Object o : hourly_array){
            Gson gson_hour = new Gson();
            temp = gson_hour.fromJson(o.toString(),WeatherRainVoTemp.class);
        }

       List<WeatherRainVO> list = new ArrayList<>();
       LocalDateTime baseTime = LocalDateTime.now();
       JSONObject jsonObject = JSONObject.parseObject(HttpUtil.get(api));
       Object result = jsonObject.get("result");
       String series  = ((JSONObject) jsonObject.get("result")).get("series").toString();

        Gson gson_result = new Gson();

        // 没雨
        if (Objects.equals(series, "[]")){
            for (int i =0; i<120; i++){
                WeatherRainVO weatherRainVO = gson_result.fromJson(result.toString(), WeatherRainVO.class);
                weatherRainVO.setTempFc(temp.getTemp_fc());
                weatherRainVO.setDataTime(String.valueOf(baseTime.plusMinutes(i)));
                weatherRainVO.setRain("0");
                list.add(weatherRainVO);
            }
        }

        // 有雨
        JSONArray jsonArray = JSONArray.parseArray(series);
        Object[] array = jsonArray.toArray();
        int i = 0;
        for (Object o : array){
            WeatherRainVO weatherRainVO = gson_result.fromJson(result.toString(), WeatherRainVO.class);
            weatherRainVO.setTempFc(temp.getTemp_fc());

            weatherRainVO.setDataTime(String.valueOf(baseTime.plusMinutes(i)));
            weatherRainVO.setRain(String.valueOf(o));
            i = i+1;
            list.add(weatherRainVO);
        }
        return list;
    }



    @Override
    @TenantIgnore
    @FarmTenantIgnore
    // ok
    public List<WeatherDamageVO> getWeatherDamage() {

        List<WeatherDamageVO> list = new ArrayList<>();
        List<String> damageList = Arrays.asList(
                "0",
                "台风", "暴雨", "暴雪", "寒潮",
                "大风", "沙尘暴", "高温", "干旱",
                "雷电", "冰雹", "霜冻", "大雾");

        if (!state){
            JSONArray jsonArray = JSONArray.parseArray(alerts);
            Object[] array = jsonArray.toArray();
            for (Object o : array) {
                Gson gson = new Gson();
                WeatherDamageVO weatherDamageVO = gson.fromJson(o.toString(),WeatherDamageVO.class);
                String type = weatherDamageVO.getType();

                for (int i =0 ;i<damageList.size() ; i++) {
                    if (damageList.get(i) == type) {
                        weatherDamageVO.setTypeId(String.valueOf(i));
                        list.add(weatherDamageVO);
                    }
                }
                weatherDamageVO.setCreateDate(String.valueOf(LocalDateTime.now()));
            }
            return list;

        }

        Long id= FarmTenantContextHolder.getFarmTenantId();
        FarmDO farmDO = weatherFarmMapper.selectById(id);
        String latlon = farmDO.getCoordinateCenter();
        String[] farmLonlats = latlon.split(",");
        String lat = farmLonlats[0];
        String lon = farmLonlats[1];
        String lonlat = lon + "," + lat;

        String api = damage_url + lonlat + key;

        //System.out.println(api);

        JSONObject jsonObject = JSONObject.parseObject(HttpUtil.get(api));
        String alerts  = ((JSONObject) jsonObject.get("result")).get("alerts").toString();

        if (Objects.equals(alerts, "[]")){
            return list;
        }
        JSONArray jsonArray = JSONArray.parseArray(alerts);
        Object[] array = jsonArray.toArray();
        for (Object o : array) {
            Gson gson = new Gson();
            WeatherDamageVO weatherDamageVO = gson.fromJson(o.toString(),WeatherDamageVO.class);
            String type = weatherDamageVO.getType();

            for (int i =0 ;i<damageList.size() ; i++) {
                if (damageList.get(i) == type) {
                    weatherDamageVO.setTypeId(String.valueOf(i));
                    list.add(weatherDamageVO);
                }
            }
            weatherDamageVO.setCreateDate(String.valueOf(LocalDateTime.now()));
        }

        return list;
    }


    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public WeatherGridVO getWeatherGrid(String lonlat) {

        if (!state){
            Gson gson = new Gson();
            WeatherGridVO weatherGridVO = gson.fromJson(a,WeatherGridVO.class);
            weatherGridVO.setCreateDate(String.valueOf(LocalDateTime.now()));

            return weatherGridVO;
        }

        String api = grid_url + lonlat + key;

        JSONObject jsonObject = JSONObject.parseObject(HttpUtil.get(api));
        Object a  = ((JSONObject) jsonObject.get("result")).get("grid_now");

        Gson gson = new Gson();
        WeatherGridVO weatherGridVO = gson.fromJson(a.toString(),WeatherGridVO.class);
        weatherGridVO.setCreateDate(String.valueOf(LocalDateTime.now()));

        return weatherGridVO;
    }



    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public List<WeatherHour> getWeatherDay24() throws ParseException {

        if (!state){
            List<WeatherHour> list = new ArrayList<>();

            JSONArray daily_jsonArray = JSONArray.parseArray(day24DailyString);
            Object[] daily_array = daily_jsonArray.toArray();

            for (Object o : daily_array){
                Gson gson = new Gson();
                WeatherHourBaseVO weatherHourBaseVO = gson.fromJson(o.toString(),WeatherHourBaseVO.class);
                weatherHourBaseVO.setDate(weatherHourBaseVO.getData_time().substring(0,10));
                weatherHourBaseVO.setTime(weatherHourBaseVO.getData_time().substring(11));

                WeatherHour weatherHour = new WeatherHour();
                weatherHour.setCode(weatherHourBaseVO.getCode());
                weatherHour.setText(weatherHourBaseVO.getText());
                weatherHour.setTempFc(weatherHourBaseVO.getTemp_fc());
                weatherHour.setRh(weatherHourBaseVO.getRh());
                weatherHour.setWindClass(weatherHourBaseVO.getWind_class());
                weatherHour.setWindSpeed(weatherHourBaseVO.getWind_speed());
                weatherHour.setWindDir(weatherHourBaseVO.getWind_dir());

                SimpleDateFormat dateDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateDate = new SimpleDateFormat("yyyy-MM-dd");
                //SimpleDateFormat dateTime = new SimpleDateFormat("HH:mm:ss");

                weatherHour.setDataTime(dateDateTime.parse(weatherHourBaseVO.getData_time()));
                weatherHour.setDate(dateDate.parse(weatherHourBaseVO.getDate()));

                DateTimeFormatter dateFormat_3 = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalTime time = LocalTime.parse(weatherHourBaseVO.getTime(), dateFormat_3);
                weatherHour.setTime(Time.valueOf(time));


                //System.out.println(weatherHourBaseVO);
                list.add(weatherHour);
            }

            return list;
        }


        Long id= FarmTenantContextHolder.getFarmTenantId();

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = currentDate.format(formatter);
        return weatherHourMapper.selectDay24(id,date);
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public List<WeatherRadar> getWeatherRadar() {

        if (!state){
            List<WeatherRadar> list = new ArrayList<>();
            return list;
        }

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = currentDate.format(formatter);

        LocalDateTime currentTime = from(LocalDateTime.now());
        LocalDateTime a = currentTime.plus(2, ChronoUnit.HOURS);
        LocalDateTime b = currentTime.plus(-3, ChronoUnit.HOURS);

        Date first = Date.from(a.atZone(ZoneId.systemDefault()).toInstant());
        Date last = Date.from(b.atZone(ZoneId.systemDefault()).toInstant());

        return weatherRadarMapper.selectRadar(first,last);
    }



    //@Scheduled(cron = "0 30 00 * * ?")
    @TenantIgnore
    @FarmTenantIgnore
    public void createWeatherHours() throws ParseException {

       List<FarmDO> FarmList = weatherFarmMapper.selectLonlat();
       for (FarmDO farm : FarmList){
           String latlon = farm.getCoordinateCenter();
           int id = farm.getId();


           String[] farmLonlats = latlon.split(",");
           String lat = farmLonlats[0];
           String lon = farmLonlats[1];
           String lonlat = lon + "," + lat;

           List<WeatherHourBaseVO> list = getDataDay24(lonlat);

           for (WeatherHourBaseVO o : list){
               WeatherHour weatherHour = new WeatherHour();
               weatherHour.setCode(o.getCode());
               weatherHour.setText(o.getText());
               weatherHour.setTempFc(o.getTemp_fc());
               weatherHour.setRh(o.getRh());
               weatherHour.setWindSpeed(o.getWind_speed());
               weatherHour.setWindDir(o.getWind_dir());
               weatherHour.setWindClass(o.getWind_class());
               SimpleDateFormat dateFormat_1 = new SimpleDateFormat("yyyy-MM-dd");
               Date date = dateFormat_1.parse(o.getDate());
               weatherHour.setDate(date);
               SimpleDateFormat dateFormat_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               Date dateTime = dateFormat_2.parse(o.getData_time());
               weatherHour.setDataTime(dateTime);
               DateTimeFormatter dateFormat_3 = DateTimeFormatter.ofPattern("HH:mm:ss");
               LocalTime time = LocalTime.parse(o.getTime(), dateFormat_3);
               weatherHour.setTime(Time.valueOf(time));

               weatherHour.setFarmTenantId((long) id);
               weatherHourMapper.insert(weatherHour);
           }

       }
    }

    @TenantIgnore
    @FarmTenantIgnore
    private List<WeatherHourBaseVO> getDataDay24(String lonlat) {

        String api = url_24 + lonlat + hour_24 + key;

        JSONObject jsonObject_day = JSONObject.parseObject(HttpUtil.get(api));
        String dailyString = ((JSONObject) jsonObject_day.get("result")).get("grid_hourly").toString();
        JSONArray daily_jsonArray = JSONArray.parseArray(dailyString);
        Object[] daily_array = daily_jsonArray.toArray();

        List<WeatherHourBaseVO> list = new ArrayList<>();

        for (Object o : daily_array) {
            Gson gson = new Gson();
            WeatherHourBaseVO weatherHourBaseVO = gson.fromJson(o.toString(), WeatherHourBaseVO.class);
            weatherHourBaseVO.setDate(weatherHourBaseVO.getData_time().substring(0, 10));
            weatherHourBaseVO.setTime(weatherHourBaseVO.getData_time().substring(11));
            list.add(weatherHourBaseVO);
        }

        return list;
    }

    //@Scheduled(cron = "0 */10 * * * ?")
    @TenantIgnore
    @FarmTenantIgnore
    public void createWeatherRadar(){

        List<WeatherRadarVO> list = getRadarList();

        for (WeatherRadarVO o : list){
            Gson gson = new Gson();
            WeatherRadar weatherRadar = gson.fromJson(o.toString(), WeatherRadar.class);

            List<WeatherRadar> listDetermine =weatherRadarMapper.determine(weatherRadar);

            if (listDetermine.isEmpty()){
                weatherRadarMapper.insert(weatherRadar);
            }
            else {
                for (WeatherRadar weatherRadar1 : listDetermine){
                    long id = weatherRadar1.getId();
                    weatherRadarMapper.deleteById(id);
                    weatherRadarMapper.insert(weatherRadar);
                }
            }
        }
    }

    @TenantIgnore
    @FarmTenantIgnore
    public List<WeatherRadarVO> getRadarList()  {

        JSONObject jsonObject_day = JSONObject.parseObject(HttpUtil.get(radar_url + key));
        String host  = ((JSONObject) jsonObject_day.get("result")).get("host").toString();
        String imgs  = ((JSONObject)((JSONObject) jsonObject_day.get("result")).get("fcImgSeries")).get("imgs").toString();
        String times  = ((JSONObject)((JSONObject) jsonObject_day.get("result")).get("fcImgSeries")).get("times").toString();

        JSONArray imgs_jsonArray = JSONArray.parseArray(imgs);
        Object[] imgs_array = imgs_jsonArray.toArray();
        JSONArray times_jsonArray = JSONArray.parseArray(times);

        List<WeatherRadarVO> list = new ArrayList<>();
        int i =0;

        for (Object img : imgs_array){

            String img_url = host +img;

            // oss
            String oss_url = fileApi.createFile(img_url.getBytes());
            WeatherRadarVO weatherRadarVO = new WeatherRadarVO();
            weatherRadarVO.setImg(oss_url);
            weatherRadarVO.setDateTime(times_jsonArray.get(i).toString());
            weatherRadarVO.setDate(times_jsonArray.get(i).toString().substring(0,8));
            weatherRadarVO.setTime(times_jsonArray.get(i).toString().substring(8));

            i = i+1;

            list.add(weatherRadarVO);
        }

        return list;
    }





}
