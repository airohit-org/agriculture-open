package com.airohit.agriculture.module.device.utils;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class Ssh2Util {
    private Connection conn;
    private String charset = Charset.defaultCharset().toString();

    /**
     * 连接到指定的IP
     *
     * @param user
     * @param passwd
     * @param ip
     * @param port
     * @return
     * @throws Exception
     */
    public boolean connect(String user, String passwd, String ip, int port) throws Exception {
        this.conn = new Connection(ip, port);
        this.conn.connect();
        return this.conn.authenticateWithPassword(user, passwd);
    }

    /**
     * 执行相关的命令
     * 非交互式
     *
     * @param command
     * @param user
     * @param passwd
     * @param ip
     * @param port
     * @return
     * @throws Exception
     */
    public String execCmd(String command, String user, String passwd, String ip, int port) throws Exception {
        InputStream stdOut = null;
        InputStream stdErr = null;
        StringBuilder buf = new StringBuilder();
        try {
            System.out.println("连接地址：" + ip + ":" + port);
            buf.append("\n连接地址：" + ip + ":" + port + "\n");
            if (connect(user, passwd, ip, port)) {
                Session session = this.conn.openSession();
                System.out.println("执行命令：" + command);
                buf.append("执行命令：" + command + "\n");
                session.execCommand(command);
                stdOut = new StreamGobbler(session.getStdout());
                String outStr = processStream(stdOut, this.charset);
                stdErr = new StreamGobbler(session.getStderr());
                String outErr = processStream(stdErr, this.charset);
                System.out.println("执行结果：" + outStr + outErr);
                buf.append("执行结果：" + outStr + outErr + "\n");
                int ret = session.getExitStatus();
                System.out.println("返回代码：" + ret);
                buf.append("返回代码：" + ret);
            } else {
                System.out.println("登录远程机器失败，请检查网络连接及用户密码是否正确！！！");
                buf.append("登录远程机器失败，请检查网络连接及用户密码是否正确！！！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            buf.append("执行脚本时出现异常，命令：" + command + "-" + e.getMessage());
        } finally {
            if (null != conn) {
                conn.close();
            }
            if (null != stdOut) {
                stdOut.close();
            }
            if (null != stdErr) {
                stdErr.close();
            }
        }
        return buf.toString();
    }


    /**
     * 交互式命令
     *
     * @param commands
     * @param user
     * @param passwd
     * @param ip
     * @param port
     * @throws Exception
     */
    public void execCmdOnPTY(String[] commands, String user, String passwd, String ip, int port)
            throws Exception {
        InputStream stdOut = null;
        InputStream stdErr = null;
        Session session = null;
        try {
            if (connect(user, passwd, ip, port)) {
                session = this.conn.openSession();
                session.requestPTY("bash");
                session.startShell();
                try (PrintWriter out = new PrintWriter(session.getStdin());) {
                    for (String command : commands) {
                        System.out.println("执行命令：" + command);
                        out.println(command);
                    }
                    out.println("exit");
                    stdOut = new StreamGobbler(session.getStdout());
                    String outStr = processStream(stdOut, this.charset);
                    stdErr = new StreamGobbler(session.getStderr());
                    String outErr = processStream(stdErr, this.charset);
                    System.out.println("执行结果（out）：" + outStr);
                    System.out.println("执行结果（err）：" + outErr);
                    int waitForCondition = session.waitForCondition(
                            ChannelCondition.CLOSED | ChannelCondition.EOF | ChannelCondition.EXIT_STATUS, 0);
                    System.out.println("waitForCondition:" + waitForCondition);
                    int ret = session.getExitStatus();
                    System.out.println("返回代码：" + ret);
                    if (ret != 0) {
                        throw new Exception("导出命令不能正常执行结束！！！");
                    }
                    if (null != stdOut) {
                        stdOut.close();
                    }
                    if (null != stdErr) {
                        stdErr.close();
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            } else {
                System.out.println("登录远程机器失败，请检查网络连接及用户密码是否正确！！！");
                throw new Exception("登录远程机器失败，请检查网络连接及用户密码是否正确!!!");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != session) {
                session.close();
            }
            if (null != conn) {
                conn.close();
            }
            if (null != stdOut) {
                stdOut.close();
            }
            if (null != stdErr) {
                stdErr.close();
            }
        }
    }


    /**
     * 输出日志
     */
    private String processStream(InputStream in, String charset) throws Exception {
        StringBuilder strBuf = new StringBuilder();
        byte[] buf = new byte[1024];
        int num = 0;
        while ((num = in.read(buf)) != -1) {
            strBuf.append(new String(buf, 0, num, charset).trim());
            buf = new byte[1024];
        }
        return strBuf.toString();
    }
}

