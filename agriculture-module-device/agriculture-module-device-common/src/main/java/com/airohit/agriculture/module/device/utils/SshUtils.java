package com.airohit.agriculture.module.device.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class SshUtils {

    /**
     * 登录ssh
     *
     * @param ip
     * @param username
     * @param password
     * @return
     */
    public static Connection login(String ip, String username, String password) {
        boolean flag = false;
        Connection connection = null;
        try {
            connection = new Connection(ip, 8799);
            connection.connect();// 连接
            flag = connection.authenticateWithPassword(username, password);// 认证
            if (flag) {
                System.out.println("================" + ip + " 登录成功==================");
                return connection;
            }
        } catch (Exception e) {
            //log.info("=========登录失败=========" + e);
            if (connection != null)
                connection.close();
            connection = null;
        }
        return connection;
    }

    /**
     * 远程执行shll脚本或者命令
     *
     * @param cmd 即将执行的命令
     * @return 命令执行完后返回的结果值
     */
    public static String execmd(Connection connection, String cmd) {
        String result = "";
        try {
            if (connection != null) {
                Session session = connection.openSession();// 打开一个会话
                session.execCommand(cmd);// 执行命令
                result = processStdout(session.getStdout());
                System.out.println(result);
                // 如果为得到标准输出为空，说明脚本执行出错了
                /*if (!StringUtils.isEmpty(result)) {
                System.out.println("得到标准输出为空,链接conn:" + connection + ",执行的命令：" + cmd);
                result = processStdout(session.getStderr(), DEFAULTCHART);
                } else {
                    System.out.println("执行命令成功,链接conn:" + connection + ",执行的命令：" + cmd);
                }*/
                /*connection.close();
                session.close();*/
            }
        } catch (Exception e) {
            //log.info("执行命令失败,链接conn:" + connection + ",执行的命令：" + cmd + "  " + e);
            //     e.printStackTrace();
        }
        return result;
    }

    /**
     * 解析脚本执行返回的结果集
     *
     * @param in 输入流对象
     * @return 以纯文本的格式返回
     */
    private static String processStdout(InputStream in) {
        InputStream stdout = new StreamGobbler(in);
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line).append("\n");
                //   System.out.println(line);
            }
            br.close();
        } catch (IOException e) {
            //log.info("解析脚本出错：" + e.getMessage());
            //   e.printStackTrace();
        }
        return buffer.toString();
    }
}
