package com.uqiauto.api.orders.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uqiauto.api.orders.model.OrderInfo;
import com.uqiauto.api.secret.dao.AppSystemRepository;
import com.uqiauto.api.secret.model.AppSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrdersSupplierController {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    @Autowired
    private AppSystemRepository appSystemRepository;
    @Value("${ocs.order_url}")
    private String ocsOrderUrl;
    @Value("${wms.update_order_url}")
    private String wmsUpdateOrderUrl;
    @Value("${wms.pay_order_url}")
    private String wmsPayOrderUrl;
    @Value("${wms.cancel_order_url}")
    private String wmsCancelOrderUrl;

    /**
     * 查询
     *
     * @param begin_time 开始时间
     * @param end_time 结束时间
     * @return result
     */
    @GetMapping
    public ResponseEntity<?> list(String begin_time, String end_time, String order_from) {

        if (StringUtils.isEmpty(begin_time)) {
            Map<String, String> error = new HashMap<>();
            error.put("code", "-2");
            error.put("message", "开始时间为空");
            return new ResponseEntity<Map>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!StringUtils.isEmpty(begin_time)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
            try {
                formatter.parse(begin_time);
            } catch (Exception e) {
                Map<String, String> error = new HashMap<>();
                error.put("code", "-3");
                error.put("message", "开始时间格式不正确");
                return new ResponseEntity<Map>(error, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if (!StringUtils.isEmpty(end_time)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
            try {
                formatter.parse(end_time);
            } catch (Exception e) {
                Map<String, String> error = new HashMap<>();
                error.put("code", "-4");
                error.put("message", "结束时间格式不正确");
                return new ResponseEntity<Map>(error, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String app_key = authentication.getName();
        AppSystem appSystem = appSystemRepository.getAppSystemByAppKey(app_key).get();

        RestTemplate restTemplate = restTemplateBuilder.build();

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("begin_time", begin_time);
        paramMap.add("end_time", end_time);
        paramMap.add("seller_id", appSystem.getCompanyId().toString());

        String order_from_list = order_from;
        if ("汽配商".equals(order_from)) {
            order_from_list = "uplus,uplus系统商城,uplus系统调货,uplus系统询价,uplus系统需求";
        } else if ("汽修店".equals(order_from)) {
            order_from_list = "小闭环";
        }
        paramMap.add("order_from_list", order_from_list);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        ResponseEntity<String> responseEntity = restTemplate.exchange(ocsOrderUrl, HttpMethod.POST, new HttpEntity<>(paramMap, headers), String.class);

        String result = responseEntity.getBody();

        Gson gson = new Gson();

        List<Map<String,Object>> list = gson.fromJson(result, new TypeToken<List<Map<String,Object>>>(){}.getType());
        for (Map<String, Object> map : list) {
            String goods_info = (String)map.get("goods_info");
            List<Map<String,String>> goodsList = gson.fromJson(goods_info, new TypeToken<List<Map<String,String>>>(){}.getType());
            map.put("goods_info", goodsList);
        }

        List<OrderInfo> orderInfoList = gson.fromJson(gson.toJson(list),new TypeToken<List<OrderInfo>>(){}.getType());

        return new ResponseEntity<>(orderInfoList, HttpStatus.OK);

    }

}
