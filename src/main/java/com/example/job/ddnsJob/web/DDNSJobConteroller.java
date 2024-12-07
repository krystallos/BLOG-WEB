package com.example.job.ddnsJob.web;

import com.aliyun.alidns20150109.Client;
import com.aliyun.alidns20150109.models.*;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.example.job.ddnsJob.entry.DDNSToken;
import com.example.job.ddnsJob.service.TokenService;
import com.example.util.config.RedisUtils;
import com.example.util.dic.ConfigDicEnum;
import com.example.util.dic.DDNSEnum;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;


/**
 * 系统信息
 * @author Enoki
 */
@Component
@Order(2)
public class DDNSJobConteroller {

    private static final Logger log = Logger.getLogger(DDNSJobConteroller.class);

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private TokenService tokenService;
    private Client client;

    //历史记录IPV6
    private String localHis = "";

    /**
     * 全局初始化
     * @return
     * @throws Exception
     */
    private void createClient() {
        try {
            if (client == null) {
                Config config = new Config()
                        // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID。
                        .setAccessKeyId(redisUtils.getConfig(ConfigDicEnum.aliyunAccessKeyId.message))
                        // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
                        .setAccessKeySecret(redisUtils.getConfig(ConfigDicEnum.aliyunAccessKeySecret.message));
                config.endpoint = redisUtils.getConfig(ConfigDicEnum.aliyunDNS.message);
                client = new Client(config);
            }
        }catch (Exception error) {
            log.info("请求失败，失败结果诊断：" + error.getMessage());
        }
    }

    /**
     * 获取本机IPV6地址（首个）
     * 这里要注意，如果本机出现多个IPV6地址只会取首个
     * @return
     */
    public String getIpv6() {
        String ipv6 = null;
        try {
            Enumeration<NetworkInterface> network = NetworkInterface.getNetworkInterfaces();
            while (network.hasMoreElements()){
                NetworkInterface infa  = network.nextElement();
                if(infa.isLoopback() || !infa.isUp()){
                    continue;
                }
                Enumeration<InetAddress> inteadd = infa.getInetAddresses();
                while (inteadd.hasMoreElements()){
                    InetAddress ipGet = inteadd.nextElement();
                    if(ipGet instanceof Inet6Address){
                        if(!ipGet.getHostAddress().contains("fe80")){
                            ipv6 = ipGet.getHostAddress();
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return ipv6;
    }

    @Scheduled( fixedDelay = 60 * 30 * 1000 )
    public void getDomain() {
        String envir = redisUtils.getConfig(ConfigDicEnum.systemConfigEnvir.message);
        log.info("当前环境为[ " + envir + " ]");
        if(envir.equals("PROD")){
            String ipv6 = getIpv6();
            if(ipv6 == null){
                log.info("请求失败，无法获取IPV6信息");
                return;
            }
            if(ipv6.equals(localHis)){
                log.info("前一次请求IP地址与本次相同，无需更新");
                return;
            }else{
                localHis = ipv6;
            }
            createClient();
            Client client = this.client;
            List<DDNSToken> recordId = selectDomain(client, ipv6);
            editDomain(client, recordId);
            log.info("本次阿里云DDNS请求结束");
        }
    }

    public List<DDNSToken> selectDomain(Client client, String ipv6) {
        List<DDNSToken> arr = new ArrayList<>();
        //查询域名解析列表
        DescribeDomainRecordsRequest describeDomainRecordsRequest = new DescribeDomainRecordsRequest()
                .setDomainName(redisUtils.getConfig(ConfigDicEnum.aliyunDomain.message));
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            DescribeDomainRecordsResponse describeDomainRecordsResponse = client.describeDomainRecordsWithOptions(describeDomainRecordsRequest, runtime);
            if (describeDomainRecordsResponse.getStatusCode() == 200) {
                DescribeDomainRecordsResponseBody bodys = describeDomainRecordsResponse.getBody();
                if (bodys.getTotalCount() == 0) {
                    log.info("本次请求结束，结束原因：修改域数量小于1");
                    return new ArrayList<>();
                } else {
                    List<DescribeDomainRecordsResponseBody.DescribeDomainRecordsResponseBodyDomainRecordsRecord> body = bodys.getDomainRecords().getRecord();
                    for (DescribeDomainRecordsResponseBody.DescribeDomainRecordsResponseBodyDomainRecordsRecord vo : body) {
                        DDNSToken token = new DDNSToken();
                        BeanUtils.copyProperties(vo, token);
                        token.setRequestId(bodys.getRequestId());
                        token.setRoleType(DDNSEnum.SELECTDOMAIN.message);
                        token.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                        tokenService.insertTokenSession(token);
                        if (!token.getType().equals("CNAME")) {
                            token.setValue(ipv6);
                            arr.add(token);
                        }
                    }
                }
                return arr;
            } else {
                log.info("请求失败，失败错误码：" + describeDomainRecordsResponse.getStatusCode());
                return new ArrayList<>();
            }
        }catch (TeaException error) {
            log.info("请求失败，失败结果诊断：" + error.getData().get("Recommend"));
            return new ArrayList<>();
        }catch (Exception e) {
            log.info("请求失败，失败结果诊断：" + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void editDomain(Client client, List<DDNSToken> arr) {
        for (DDNSToken editVo : arr) {
            //更新域名解析列表
            UpdateDomainRecordRequest updateDomainRecordRequest = new UpdateDomainRecordRequest()
                    .setRecordId(editVo.getRecordId())
                    .setRR(editVo.getRR())
                    .setType(editVo.getType())
                    .setValue(editVo.getValue());
            RuntimeOptions runtime = new RuntimeOptions();
            try {
                UpdateDomainRecordResponse vo = client.updateDomainRecordWithOptions(updateDomainRecordRequest, runtime);
                if (vo.getStatusCode() == 200) {
                    UpdateDomainRecordResponseBody update = vo.getBody();
                    DDNSToken token = new DDNSToken();
                    BeanUtils.copyProperties(editVo, token);
                    token.setRequestId(update.getRequestId());
                    token.setRoleType(DDNSEnum.EDITDOMAIN.message);
                    token.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                    tokenService.insertTokenSession(token);
                } else {
                    log.info("请求失败，失败错误码：" + vo.getStatusCode());
                }
            }catch (TeaException error) {
                log.info("请求失败，失败结果诊断：" + error.getData().get("Recommend"));
            } catch (Exception e) {
                log.info("请求失败，失败原因：" + e.getMessage());
            }
        }
    }

}