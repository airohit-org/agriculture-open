package com.airohit.agriculture.module.peasant.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class Excel {

    @ExcelProperty(value = "手机号")
    private String phone;

    @ExcelProperty(value = "姓名")
    private String name;

    @ExcelProperty(value = "省")
    private String province;

    @ExcelProperty(value = "城市")
    private String city;

    @ExcelProperty(value = "区县")
    private String area;

    //@ExcelProperty(value = "乡镇")
    //private String street;

    //@ExcelProperty(value = "村委会")
    //private String village;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

}
