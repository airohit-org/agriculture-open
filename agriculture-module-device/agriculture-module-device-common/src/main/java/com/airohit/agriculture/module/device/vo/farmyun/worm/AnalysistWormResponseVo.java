package com.airohit.agriculture.module.device.vo.farmyun.worm;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/6 13:53
 */
public class AnalysistWormResponseVo {

    /**
     * recordId : 69010
     * imagesUrl : http://cq.sub.farm.0531yun.cn:7077/static/wormImages/20000009A_20220807215015.jpg
     * analyseCoordUrl : http://cq.sub.farm.0531yun.cn:7077/static/wormImages/pictureProcessing/pictureProcessing20000009A_20220807215015.jpg
     */

    private String recordId;
    private String imagesUrl;
    private String analyseCoordUrl;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public String getAnalyseCoordUrl() {
        return analyseCoordUrl;
    }

    public void setAnalyseCoordUrl(String analyseCoordUrl) {
        this.analyseCoordUrl = analyseCoordUrl;
    }
}
