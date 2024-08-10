package com.example.job.systemJob.web;

import com.example.job.systemJob.entry.DirSystemEntry;
import com.example.job.systemJob.entry.SystemConfigEnerty;
import com.example.job.systemJob.entry.SystemEntry;
import com.example.job.systemJob.entry.SystemNetWork;
import com.example.job.systemJob.service.SystemJobService;
import com.sun.management.OperatingSystemMXBean;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.GlobalConfig;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * 系统信息
 * @author Enoki
 */
@RestController
public class SystemJobConteroller {

    private static final Logger log = Logger.getLogger(SystemJobConteroller.class);

    @Resource
    private SystemJobService systemJobService;

    /**
     * 自动任务每隔10分钟更新一次系统信息
     */
    @Scheduled( fixedDelay = 60 * 10 * 1000 )
//    @Scheduled( fixedDelay = 10000 )
    public void getSystemJob() {
        try {
            SystemEntry systemEntry = getSysInfo();
            systemConfig(systemEntry);
            systemEntry.setCreateDate(systemEntry.getNowDate(null));
            systemJobService.insertSystem(systemEntry);
        }catch (UnknownHostException e){
            log.error("日志截取错误："+e.getMessage());
        }
    }

    private SystemEntry getSysInfo(){
        SystemEntry systemEntry = new SystemEntry();
        try {
            SystemInfo systemInfo = new SystemInfo();
            DecimalFormat df = new DecimalFormat("#.##");
            OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

            // 总的物理内存
            String totalMemorySize = df.format(osmxb.getTotalPhysicalMemorySize() / 1024.0 / 1024 / 1024);
            // 剩余的物理内存
            String freePhysicalMemorySize = df.format(osmxb.getFreePhysicalMemorySize() / 1024.0 / 1024 / 1024);
            // 已使用的物理内存
            String usedMemory = df.format((osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / 1024.0 / 1024 / 1024);

            //cpu核数
            systemEntry.setCpuCode(Runtime.getRuntime().availableProcessors());

            GlobalConfig.set(GlobalConfig.OSHI_OS_WINDOWS_CPU_UTILITY, true);
            CentralProcessor processor = systemInfo.getHardware().getProcessor();
            long[] prevTicks = processor.getSystemCpuLoadTicks();
            // 睡眠1s
            TimeUnit.SECONDS.sleep(1);
            long[] ticks = processor.getSystemCpuLoadTicks();
            long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
            long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
            long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
            long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
            long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
            long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
            long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
            long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
            long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
            long cpuUtilization = user + nice + cSys  + iowait + irq + softirq + steal;
            //cpu系统使用率
            systemEntry.setCpuSystemUse(df.format(cSys * 100.0 / totalCpu ) + "%");
            //cpu用户使用率
            systemEntry.setCpuUserUse(df.format(user * 100.0 / totalCpu ) + "%");
            //cpu当前空闲率
            systemEntry.setCpuLeisure(df.format(idle * 100.0 / totalCpu) + "%");
            //cpu利用率
            systemEntry.setCpuUtilization(df.format(cpuUtilization * 100.0 / totalCpu) + "%");
            //总的物理内存
            systemEntry.setAllMemory(totalMemorySize + "GB");
            //剩余的物理内存
            systemEntry.setUtilizationMemory(freePhysicalMemorySize + "GB");
            //已使用的物理内存
            systemEntry.setUseMemory(usedMemory + "GB");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return systemEntry;
    }

    /**
     * 设置磁盘信息
     */
    private void setSysFiles(String systemId) {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem os = systemInfo.getOperatingSystem();
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            if (fs.getType().equals("ext4") || fs.getType().equals("NTFS")){
                long free = fs.getUsableSpace();
                long total = fs.getTotalSpace();
                long used = total - free;
                DirSystemEntry sysFile = new DirSystemEntry();
                sysFile.setSysTypeName(fs.getType());
                sysFile.setTypeName(fs.getName());
                sysFile.setTotal(convertFileSize(total));
                sysFile.setFree(convertFileSize(free));
                sysFile.setUsed(convertFileSize(used));
                sysFile.setDescription(fs.getDescription());
                sysFile.setUuid(fs.getUUID());
                sysFile.setFsType(fs.getType());
                sysFile.setSystemId(systemId);
                sysFile.setDirName(fs.getName());
                String ids = systemJobService.selectDoubleDicCode(sysFile);
                if(ids == null){
                    systemJobService.insertSystemDic(sysFile);
                }else{
                    sysFile.setId(ids);
                    systemJobService.updateSystemDic(sysFile);
                }
            }
        }
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    private String convertFileSize(long size)
    {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        }
        else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        }
        else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        }
        else {
            return String.format("%d B", size);
        }
    }

    private SystemEntry systemConfig (SystemEntry systemEntry) throws UnknownHostException {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        MemoryMXBean memoryManagerMXBean = ManagementFactory.getMemoryMXBean();
        SystemConfigEnerty enerty = new SystemConfigEnerty();
        Date date = new Date(runtimeMXBean.getStartTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        enerty.setStartTime(sdf.format(date));//
        enerty.setRuntime(runtimeMXBean.getUptime());
        enerty.setSystemNum(System.getProperty("os.name"));
        enerty.setCpuName(System.getenv("PROCESSOR_IDENTIFIER"));
        enerty.setCpuCode(System.getenv("PROCESSOR_IDENTIFIER"));
        enerty.setSystemName(System.getenv("COMPUTERNAME"));
        enerty.setIp(InetAddress.getLocalHost().getHostAddress());
        enerty.setSystemType(System.getProperty("os.name"));
        String ids = systemJobService.selectDoubleCpuCode(enerty.getCpuCode());
        if(ids == null){
            systemJobService.insertSystemConfig(enerty);
            systemEntry.setSystemId(enerty.getId());
        }else {
            enerty.setId(ids);
            systemEntry.setSystemId(ids);
            systemJobService.updateSystemConfig(enerty);
        }
        systemEntry.setMemoryUse((memoryManagerMXBean.getHeapMemoryUsage().getUsed() / 1024 / 1024)+"MB");//MB
        systemEntry.setMemoryMax((memoryManagerMXBean.getHeapMemoryUsage().getMax() / 1024 / 1024)+"MB");//MB
        systemEntry.setMemoryUtilization(((memoryManagerMXBean.getHeapMemoryUsage().getMax() - memoryManagerMXBean.getHeapMemoryUsage().getUsed()) / 1024 / 1024)+"MB");//MB
        setSysFiles(enerty.getId());
        setNetWork(enerty.getId());
        return systemEntry;
    }

    public void setNetWork(String systemId){
        SystemNetWork netWork = new SystemNetWork();
        Properties props = System.getProperties();
        String os = props.getProperty("os.name").toLowerCase();
        os = os.startsWith("win")?"windows":"linux";
        Process pro = null;
        Runtime r = Runtime.getRuntime();
        BufferedReader input = null;
        try {
            String command = "windows".equals(os)?"netstat -e":"ifconfig";
            pro = r.exec(command);
            input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            long[] result1 = readInLine(input);
            Thread.sleep(3);
            pro.destroy();
            input.close();
            pro = r.exec(command);
            input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            long[] result2 = readInLine(input);
            netWork.setRxPercent(formatNumber((result2[0]-result1[0])/(1024.0*(3.0 / 1000)))); // 下行速率(kB/s)
            netWork.setTxPercent(formatNumber((result2[1]-result1[1])/(1024.0*(3.0 / 1000)))); // 上行速率(kB/s)
            netWork.setSystemId(systemId);
            netWork.setCreateDate(netWork.getNowDate(null));
            systemJobService.insertSystemNetWork(netWork);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Optional.ofNullable(pro).ifPresent(Process::destroy);
        }
    }

    private static long[] readInLine(BufferedReader input) {
        long[] arr = new long[2];
        StringTokenizer tokenStat = null;
        try {
            input.readLine();
            input.readLine();
            input.readLine();
            input.readLine();
            tokenStat = new StringTokenizer(input.readLine());
            tokenStat.nextToken();
            arr[0] = Long.parseLong(tokenStat.nextToken());
            arr[1] = Long.parseLong(tokenStat.nextToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    private static String formatNumber(double f) {
        return new Formatter().format("%.2f", f).toString();
    }

}