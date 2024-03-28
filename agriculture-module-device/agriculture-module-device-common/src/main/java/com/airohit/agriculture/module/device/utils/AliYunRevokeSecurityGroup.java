package com.airohit.agriculture.module.device.utils;// This file is auto-generated, don't edit it. Thanks.

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@Slf4j
public class AliYunRevokeSecurityGroup {

    @Value("${aliyun.access-key-id}")
    private static String accessKeyId;

    @Value("${aliyun.access-key-secret}")
    private static String accessKeySecret;

    @Value("${aliyun.security-group-id}")
    private static String securityGroupId;

    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.teaopenapi.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // Endpoint 请参考 https://api.aliyun.com/product/Ecs
        config.endpoint = "ecs.cn-beijing.aliyuncs.com";
        return new com.aliyun.teaopenapi.Client(config);
    }

    /**
     * API 相关
     *
     * @param
     * @return OpenApi.Params
     */
    public static com.aliyun.teaopenapi.models.Params createApiInfo() throws Exception {
        com.aliyun.teaopenapi.models.Params params = new com.aliyun.teaopenapi.models.Params()
                // 接口名称
                .setAction("RevokeSecurityGroup")
                // 接口版本
                .setVersion("2014-05-26")
                // 接口协议
                .setProtocol("HTTPS")
                // 接口 HTTP 方法
                .setMethod("POST")
                .setAuthType("AK")
                .setStyle("RPC")
                // 接口 PATH
                .setPathname("/")
                // 接口请求体内容格式
                .setReqBodyType("json")
                // 接口响应体内容格式
                .setBodyType("json");
        return params;
    }

    public static void revokeSecurityGroup(String portRange) {

        try {
            com.aliyun.teaopenapi.Client client = AliYunRevokeSecurityGroup.createClient(accessKeyId, accessKeySecret);
            com.aliyun.teaopenapi.models.Params params = AliYunRevokeSecurityGroup.createApiInfo();
            // query params
            java.util.Map<String, Object> queries = new java.util.HashMap<>();
            queries.put("RegionId", "cn-beijing");
            queries.put("SecurityGroupId", securityGroupId);
            queries.put("Policy", "accept");
            queries.put("Priority", "1");
            queries.put("IpProtocol", "tcp");
            queries.put("SourceCidrIp", "0.0.0.0/0");
            queries.put("PortRange", String.format("%s/%s", portRange, portRange));
            // runtime options
            com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
            com.aliyun.teaopenapi.models.OpenApiRequest request = new com.aliyun.teaopenapi.models.OpenApiRequest()
                    .setQuery(com.aliyun.openapiutil.Client.query(queries));
            // 复制代码运行请自行打印 API 的返回值
            // 返回值为 Map 类型，可从 Map 中获得三类数据：响应体 body、响应头 headers、HTTP 返回的状态码 statusCode。
            java.util.Map<String, Object> resp = (Map<String, Object>) client.callApi(params, request, runtime);
            com.aliyun.teaconsole.Client.log(com.aliyun.teautil.Common.toJSONString(resp));
            log.info("阿里云 删除安全组入方向规则 queries: {}   resp：{} ", queries, resp);
        } catch (Exception e) {
            log.info("阿里云 删除安全组入方向规则 失败：{}", e.getMessage());
        }
    }
}
