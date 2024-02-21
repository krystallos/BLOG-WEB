package com.example.job.systemJob.web;

import com.example.job.systemJob.entry.SystemEntry;
import com.example.job.systemJob.service.SystemJobService;
import com.sun.management.OperatingSystemMXBean;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.util.GlobalConfig;

import javax.annotation.Resource;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.Date;
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
     * 自动任务每隔20分钟更新一次系统信息
     */
    @Scheduled( fixedDelay = 60 * 10 * 1000 )
//    @Scheduled( fixedDelay = 10000 )
    public void getSystemJob() {
        SystemEntry systemEntry = getSysInfo();
        if(null == systemEntry.getTimeMillis()){
            log.error("系统日志记录失败，失败节点：" + new Date().getTime());
        }
        systemEntry.setCreateDate(systemEntry.getNowDate(null));
        systemJobService.insertSystemJob(systemEntry);
    }

    private SystemEntry getSysInfo(){
        SystemEntry systemEntry = new SystemEntry();
        systemEntry.setTimeMillis(new Date().getTime());
        try {
            SystemInfo systemInfo = new SystemInfo();
            DecimalFormat df = new DecimalFormat("#.##");
            OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

            // 操作系统
            String osName = System.getProperty("os.name");
            // 总的物理内存
            String totalMemorySize = df.format(osmxb.getTotalPhysicalMemorySize() / 1024.0 / 1024 / 1024);
            // 剩余的物理内存
            String freePhysicalMemorySize = df.format(osmxb.getFreePhysicalMemorySize() / 1024.0 / 1024 / 1024);
            // 已使用的物理内存
            String usedMemory = df.format((osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / 1024.0 / 1024 / 1024);

            //操作系统
            systemEntry.setSystemNum(osName);
            //cpu核数
            systemEntry.setCpuCode(Runtime.getRuntime().availableProcessors() + "");

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
            systemEntry.setCpuSystemUse(df.format(cSys * 100.0 / totalCpu ));
            //cpu用户使用率
            systemEntry.setCpuUserUse(df.format(user * 100.0 / totalCpu ));
            //cpu当前空闲率
            systemEntry.setCpuLeisure(df.format(idle * 100.0 / totalCpu));
            //cpu利用率
            systemEntry.setCpuUtilization(df.format(cpuUtilization * 100.0 / totalCpu));
            //总的物理内存
            systemEntry.setAllMemory(totalMemorySize);
            //剩余的物理内存
            systemEntry.setUtilizationMemory(freePhysicalMemorySize);
            //已使用的物理内存
            systemEntry.setUseMemory(usedMemory);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return systemEntry;
    }

}