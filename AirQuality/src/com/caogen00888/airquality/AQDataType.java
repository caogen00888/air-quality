package com.caogen00888.airquality;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Pm25.in API  文档更新时间：2014-01-03
注意
  
1、所有API的调用，必须附带"token"这个参数，即申请的AppKey；
2、API中的'city'参数支持中文、拼音和区号，例如："city=广州","city=guangzhou","city=020"；
3、因为参数使用中文需要encode，所以推荐开发者city参数尽量使用拼音；
4、重名情况：泰州的拼音为"taizhoushi"，台州的拼音为"taizhou"；
5、API返回的JSON格式数据是经过UTF-8编码的，这个文档里的"成功返回示例"为了直观，已经decode过；
6、由于有些开发者对API调用过于频繁，严重影响了服务器，现在对API调用次数作出调整：1.10和1.11每小时15次、1.12每小时5次、1.13每小时15次，其余每小时500次；
7、由于数据源方面的原因，API中的24小时均值、station_code有时返回可能为空；

数据格式说明：
PM25.in网站提供的空气质量指数实时数据来源于国家环境保护部，API返回的数据包括了以下内容：

字段  字段说明
aqi     空气质量指数(AQI)，即air quality index，是定量描述空气质量状况的无纲量指数
area    城市名称
position_name   监测点名称
station_code    监测点编码
so2     二氧化硫1小时平均
so2_24h     二氧化硫24小时滑动平均
no2     二氧化氮1小时平均
no2_24h     二氧化氮24小时滑动平均
pm10    颗粒物（粒径小于等于10μm）1小时平均
pm10_24h    颗粒物（粒径小于等于10μm）24小时滑动平均
co  一氧化碳1小时平均
co_24h  一氧化碳24小时滑动平均
o3  臭氧1小时平均
o3_24h  臭氧日最大1小时平均
o3_8h   臭氧8小时滑动平均
o3_8h_24h   臭氧日最大8小时滑动平均
pm2_5   颗粒物（粒径小于等于2.5μm）1小时平均
pm2_5_24h   颗粒物（粒径小于等于2.5μm）24小时滑动平均
primary_pollutant   首要污染物
quality     空气质量指数类别，有“优、良、轻度污染、中度污染、重度污染、严重污染”6类
time_point  数据发布的时间
*/
public class AQDataType {
    public static class AQDataTypeDic {
        public static String COLUMN_NAME_AQI = "Aqi";
        public static String COLUMN_NAME_AREA = "area";
        public static String COLUMN_NAME_POSITION_NAME = "position_name";
        public static String COLUMN_NAME_STATION_CODE = "station_code";
        public static String COLUMN_NAME_SO2 = "so2";
        public static String COLUMN_NAME_SO2_24H = "so2_24h";
        public static String COLUMN_NAME_NO2 = "no2";
        public static String COLUMN_NAME_NO2_24H = "no2_24h";
        public static String COLUMN_NAME_PM10 = "pm10";
        public static String COLUMN_NAME_PM10_24H = "pm10_24h";
        public static String COLUMN_NAME_CO = "co";
        public static String COLUMN_NAME_CO_24H = "co_24h";
        public static String COLUMN_NAME_O3 = "o3";
        public static String COLUMN_NAME_O3_24H = "o3_24h";
        public static String COLUMN_NAME_O3_8H = "o3_8h";
        public static String COLUMN_NAME_O3_8H_24H = "o3_8h_24h";
        public static String COLUMN_NAME_PM2_5 = "pm2_5";
        public static String COLUMN_NAME_PM2_5_24H = "pm2_5_24h";
        public static String COLUMN_NAME_PRIMARY_POLLUTANT = "primary_pollutant";
        public static String COLUMN_NAME_QUALITY = "quality";
        public static String COLUMN_NAME_TIME_POINT = "time_point";
        private static final String[] parseKeys = new String[] {COLUMN_NAME_AQI,COLUMN_NAME_SO2,COLUMN_NAME_SO2_24H,COLUMN_NAME_NO2,COLUMN_NAME_NO2_24H,COLUMN_NAME_PM10,COLUMN_NAME_PM10_24H,COLUMN_NAME_CO,COLUMN_NAME_CO_24H,COLUMN_NAME_O3,COLUMN_NAME_O3_24H,COLUMN_NAME_O3_8H,COLUMN_NAME_O3_8H_24H,COLUMN_NAME_PM2_5,COLUMN_NAME_PM2_5_24H,COLUMN_NAME_PRIMARY_POLLUTANT,COLUMN_NAME_QUALITY,COLUMN_NAME_TIME_POINT};
        Map<String, Class<?>> dics = new HashMap<String, Class<?>> ();
        public AQDataTypeDic() {
            dics.clear();
            dics.put("aqi",Integer.class);
            dics.put("area",String.class);
            dics.put("position_name",String.class);
            dics.put("station_code",String.class);
            dics.put("so2",Integer.class);
            dics.put("so2_24h",Integer.class);
            dics.put("no2",Integer.class);
            dics.put("no2_24h",Integer.class);
            dics.put("pm10",Integer.class);
            dics.put("pm10_24h",Integer.class);
            dics.put("co",Integer.class);
            dics.put("co_24h",Integer.class);
            dics.put("o3",Integer.class);
            dics.put("o3_24h",Integer.class);
            dics.put("o3_8h",Integer.class);
            dics.put("o3_8h_24h",Integer.class);
            dics.put("pm2_5",Integer.class);
            dics.put("pm2_5_24h",Integer.class);
            dics.put("primary_pollutant",String.class);
            dics.put("quality",String.class);
            dics.put("time_point",Long.class);
            dics.put("station_name",String.class);
            dics.put("station_code",String.class);
            dics.put("city",String.class);
            dics.put("stations", new ArrayList<AQStationType>().getClass());
            dics.put("http://www.pm25.in/api/querys/station_names.json", new ArrayList<AQCityType>().getClass());
        }
    }

    public static class AQData {
        public String so2;    //二氧化硫1小时平均
        public String so2_24h;    //二氧化硫24小时滑动平均
        public String no2;    //二氧化氮1小时平均
        public String no2_24h;    //二氧化氮24小时滑动平均
        public String pm10;    //颗粒物（粒径小于等于10μm）1小时平均
        public String pm10_24h;    //颗粒物（粒径小于等于10μm）24小时滑动平均
        public String co;    //一氧化碳1小时平均
        public String co_24h;    //一氧化碳24小时滑动平均
        public String o3;    //臭氧1小时平均
        public String o3_24h;    //臭氧日最大1小时平均
        public String o3_8h;    //臭氧8小时滑动平均
        public String o3_8h_24h;    //臭氧日最大8小时滑动平均
        public String pm2_5;    //颗粒物（粒径小于等于2.5μm）1小时平均
        public String pm2_5_24h;    //颗粒物（粒径小于等于2.5μm）24小时滑动平均
        public String primary_pollutant;    //首要污染物
        public String quality;    //空气质量指数类别，有“优、良、轻度污染、中度污染、重度污染、严重污染”6类
    }
    
/*
1、空气质量数据相关接口
*/
    /*
     * station air quality info
     */
    public static class StationQualityData {
        public int aqi;
        public String area;
        public int pm2_5;
        public int pm2_5_24h;
        public String position_name;
        public String primary_pollutant;
        public String quality;
        public String station_code;
        public String time_point;
    }
    /*
     * city station info
     */
    public static class CityQualityData {
        public String city = null;
        List<StationQualityData> datas = new ArrayList<StationQualityData>();
    }
    /*
1.1、获取一个城市所有监测点的PM2.5数据

地址  http://www.pm25.in/api/querys/pm2_5.json
方法  GET
参数  * city：城市名称，必选参数 * avg：是否返回一个城市所有监测点数据均值的标识，可选参数，默认是true，不需要均值时传这个参数并设置为false * stations：是否只返回一个城市均值的标识，可选参数，默认是yes，不需要监测点信息时传这个参数并设置为no
返回  一个数组，里面的一项是一个监测点的PM2.5信息，其中每一项数据包括 * aqi * area * pm2_5 * pm2_5_24h * position_name * primary_pollutant * quality * station_code * time_point 默认情况下，最后一项是所有监测点的均值(即一个城市的值)

请求示例：
http://www.pm25.in/api/querys/pm2_5.json?city=珠海&token=xxxxxx  或者
http://www.pm25.in/api/querys/pm2_5.json?city=zhuhai&token=xxxxxx

成功返回示例：
[
    {
        "aqi": 82,
        "area": "珠海",
        "pm2_5": 31,
        "pm2_5_24h": 60,
        "position_name": "吉大",
        "primary_pollutant": "颗粒物(PM2.5)",
        "quality": "良",
        "station_code": "1367A",
        "time_point": "2013-03-07T19:00:00Z"
    },
    ...
    ...
    ...
    {
        "aqi": 108,
        "area": "珠海",
        "pm2_5": 0,
        "pm2_5_24h": 53,
        "position_name": "斗门",
        "primary_pollutant": "臭氧8小时",
        "quality": "轻度污染",
        "station_code": "1370A",
        "time_point": "2013-03-07T19:00:00Z"
    },
    {
        "aqi": 99,
        "area": "珠海",
        "pm2_5": 39,
        "pm2_5_24h": 67,
        "position_name": null,
        "primary_pollutant": null,
        "quality": "良",
        "station_code": null,
        "time_point": "2013-03-07T19:00:00Z"
    }
]
*/
    /*
    可能的错误返回信息：
    {
        "error": "参数不能为空"
    }
    {
        "error": "该城市还未有PM2.5数据"
    }
    {
        "error": "Sorry，您这个小时内的API请求次数用完了，休息一下吧！"
    }
    {
        "error": "You need to sign in or sign up before continuing."
    }
    */
        
    /*
    ***stations = no时的情况***
    请求示例：
    http://www.pm25.in/api/querys/pm2_5.json?city=guangzhou&token=xxxxxx&stations=no
    成功返回示例：
    [
        {
            "aqi": 151,
            "area": "广州",
            "pm2_5": 106,
            "pm2_5_24h": 115,
            "quality": "中度污染",
            "time_point": "2013-04-16T11:00:00Z"
        }
    ]
*/
    public static class CityPM2_5Data {
        public int aqi;
        public String area;
        public int pm2_5;
        public int pm2_5_24h;
        public String position_name;
        public String primary_pollutant;
        public String quality;
        public String station_code;
        public String time_point;
    }
    public static class CityPM2_5Datas {
        List<CityPM2_5Data> mCityPM2_5Data;
    }
    
/*
1.2、获取一个城市所有监测点的PM10数据

地址  http://www.pm25.in/api/querys/pm10.json
方法  GET
参数  * city：必选 * avg：可选 * stations：可选
返回  一个数组，其中每一项数据包括 * aqi * area * pm10 * pm10_24h * position_name * primary_pollutant * quality * station_code * time_point
*/

    public static class CityPM10Data {
        public int aqi;
        public String area;
        public int pm10;
        public int pm10_24h;
        public String position_name;
        public String primary_pollutant;
        public String quality;
        public String station_code;
        public String time_point;
    }
    public static class CityPM10Datas {
        List<CityPM2_5Data> mCityPM2_5Data;
    }
/*
1.3、获取一个城市所有监测点的CO数据

地址  http://www.pm25.in/api/querys/co.json
方法  GET
参数  * city：必选 * avg：可选 * stations：可选
返回  一个数组，其中每一项数据包括 * aqi * area * co * co_24h * position_name * primary_pollutant * quality * station_code * time_point
*/
    
/*
1.4、获取一个城市所有监测点的NO2数据

地址  http://www.pm25.in/api/querys/no2.json
方法  GET
参数  * city：必选 * avg：可选 * stations：可选
返回  一个数组，其中每一项数据包括 * aqi * area * no2 * no2_24h * position_name * primary_pollutant * quality * station_code * time_point
*/
    
/*
1.5、获取一个城市所有监测点的SO2数据

地址  http://www.pm25.in/api/querys/so2.json
方法  GET
参数  * city：必选 * avg：可选 * stations：可选
返回  一个数组，其中每一项数据包括 * aqi * area * so2 * so2_24h * position_name * primary_pollutant * quality * station_code * time_point
*/
    
/*
1.6、获取一个城市所有监测点的O3数据

地址  http://www.pm25.in/api/querys/o3.json
方法  GET
参数  * city：必选 * avg：可选 * stations：可选
返回  一个数组，其中每一项数据包括 * aqi * area * o3 * o3_24h * o3_8h * o3_8h_24h * position_name * primary_pollutant * quality * station_code * time_point
*/
    
/*
1.7、获取一个城市所有监测点的AQI数据（含详情）

地址  http://www.pm25.in/api/querys/aqi_details.json
方法  GET
参数  * city：必选 * avg：可选 * stations：可选
返回  一个数组，其中每一项数据包括 * aqi * area * co * co_24h * no2 * no2_24h * o3 * o3_24h * o3_8h * o3_8h_24h * pm10 * pm10_24h * pm2_5 * pm2_5_24h * position_name * primary_pollutant * quality * so2 * so2_24h * station_code * time_point
*/
    
/*
1.8、获取一个城市所有监测点的AQI数据（不含详情，仅AQI）

地址  http://www.pm25.in/api/querys/only_aqi.json
方法  GET
参数  * city：必选 * avg：可选 * stations：可选
返回  一个数组，其中每一项数据包括 * aqi * area * position_name * primary_pollutant * quality * station_code * time_point
*/
    
/*
1.9、获取一个监测点的AQI数据（含详情）

地址  http://www.pm25.in/api/querys/aqis_by_station.json
方法  GET
参数  * station_code：必选
返回  一个数组，其中的一项数据包括 * aqi * area * co * co_24h * no2 * no2_24h * o3 * o3_24h * o3_8h * o3_8h_24h * pm10 * pm10_24h * pm2_5 * pm2_5_24h * position_name * primary_pollutant * quality * so2 * so2_24h * station_code * time_point
*/
    
/*
1.10、获取一个城市的监测点列表

地址  http://www.pm25.in/api/querys/station_names.json
方法  GET
参数  * city：必选
返回  * city * stations：值是一个数组，里面的一个数组又包含了station_name和station_code

成功返回示例：
{
    "city": "珠海",
    "stations": [
        {
            "station_name": "吉大",
            "station_code": "1367A"
        },
        {
            "station_name": "前山",
            "station_code": "1368A"
        },
        {
            "station_name": "唐家",
            "station_code": "1369A"
        },
        {
            "station_name": "斗门",
            "station_code": "1370A"
        }
    ]
}
特别提示：如果不传city这个参数，将返回全国的监测点，不鼓励频繁调用；
*/

    public static class AQStationType {
        public String station_name;
        public String station_code;
        @Override
        public String toString() {
            return "{" + station_name + "," + station_code + "}";
        }
    }
    public static class AQCityType {
        public String city;
        public List<AQStationType> stations;
        @Override
        public String toString() {
            return "{" + city + "," + stations + "}\n";
        }
    }
/*
1.11、获取实施了新《环境空气质量标准》的城市列表，即有PM2.5数据的城市列表

地址  http://www.pm25.in/api/querys.json
方法  GET
参数  * 无
返回  * cities：值是一个数组

成功返回示例：
{
    "cities": [
        "上海",
        "东莞",
        "中山",
         ...
         ...
         ...
        "长春",
        "长沙",
        "青岛"
    ]
}
*/
    
/*
1.12、获取所有城市的空气质量详细数据

地址  http://www.pm25.in/api/querys/all_cities.json
方法  GET
参数  * 无
返回  一个数组，里面包含目前支持的190个城市所有监测点的详细信息，一共946项，其中每项的信息有 * aqi * area * co * co_24h * no2 * no2_24h * o3 * o3_24h * o3_8h * o3_8h_24h * pm10 * pm10_24h * pm2_5 * pm2_5_24h * position_name * primary_pollutant * quality * so2 * so2_24h * station_code * time_point
*/
    
/*
1.13、获取全部城市的空气质量指数(AQI)排行榜

地址  http://www.pm25.in/api/querys/aqi_ranking.json
方法  GET
参数  * 无
返回  一个数组，里面包含目前支持的190个城市AQI排行榜信息，其中每项的信息有 * aqi * area * co * co_24h * no2 * no2_24h * o3 * o3_24h * o3_8h * o3_8h_24h * pm10 * pm10_24h * pm2_5 * pm2_5_24h * quality * level * so2 * so2_24h * primary_pollutant * time_point 说明primary_pollutant的值可能为[二氧化硫","二氧化氮","颗粒物(PM10)","颗粒物(PM2.5)","一氧化碳","臭氧1小时","臭氧8小时"]中的一项或多项组合，出现多项时，各项之间用逗号","分隔;AQI低于50时，没有值，为"".

成功返回示例：
[
    {
        "aqi": 24,
        "area": "昆明",
        "co": 1.173,
        "co_24h": 1.362,
        "no2": 27,
        "no2_24h": 32,
        "o3": 16,
        "o3_24h": 22,
        "o3_8h": 7,
        "o3_8h_24h": 18,
        "pm10": 9,
        "pm10_24h": 24,
        "pm2_5": 11,
        "pm2_5_24h": 15,
        "quality": "优",
        "level": "一级",
        "so2": 6,
        "so2_24h": 8,
        "primary_pollutant": "",
        "time_point": "2013-10-21T14:00:00Z"
    },
    ......
    {
        "aqi": 51,
        "area": "福州",
        "co": 0.562,
        "co_24h": 0.544,
        "no2": 20,
        "no2_24h": 26,
        "o3": 92,
        "o3_24h": 93,
        "o3_8h": 64,
        "o3_8h_24h": 67,
        "pm10": 59,
        "pm10_24h": 50,
        "pm2_5": 37,
        "pm2_5_24h": 30,
        "quality": "良",
        "level": "二级",
        "so2": 12,
        "so2_24h": 10,
        "primary_pollutant": "颗粒物(PM10)",
        "time_point": "2013-10-21T14:00:00Z"
    },
    ......
    {
        "aqi": 58,
        "area": "泉州",
        "co": 0.57,
        "co_24h": 0.47,
        "no2": 16,
        "no2_24h": 17,
        "o3": 149,
        "o3_24h": 149,
        "o3_8h": 108,
        "o3_8h_24h": 108,
        "pm10": 92,
        "pm10_24h": 64,
        "pm2_5": 39,
        "pm2_5_24h": 29,
        "quality": "良",
        "level": "二级",
        "so2": 12,
        "so2_24h": 8,
        "primary_pollutant": "臭氧8小时,颗粒物(PM10)",
        "time_point": "2013-10-21T14:00:00Z"
    },
    ......
    {
        "aqi": 500,
        "area": "哈尔滨",
        "co": 4.053,
        "co_24h": 3.942,
        "no2": 166,
        "no2_24h": 165,
        "o3": 13,
        "o3_24h": 44,
        "o3_8h": 9,
        "o3_8h_24h": 35,
        "pm10": 910,
        "pm10_24h": 860,
        "pm2_5": 866,
        "pm2_5_24h": 719,
        "quality": "严重污染",
        "level": "六级",
        "so2": 61,
        "so2_24h": 47,
        "primary_pollutant": "颗粒物(PM2.5),颗粒物(PM10)",
        "time_point": "2013-10-21T14:00:00Z"
    }
]
 */
}
