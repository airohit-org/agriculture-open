package com.airohit.agriculture.module.device.vo.soil;

import lombok.*;

import java.math.BigDecimal;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/25 17:14
 */
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoilTempVo {
    private BigDecimal min;
    private BigDecimal max;
}
