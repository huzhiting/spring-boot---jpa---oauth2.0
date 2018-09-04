/*
 * FileName: OrderInf.java
 *
 * Company: 北京新汽联科技有限公司
 * Copyright 2017-2018 (C) UQI Software CO., LTD. All Rights Reserved.
 */
package com.uqiauto.api.orders.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author Admin
 * @version 1.0

 * <p>
 * History:
 *
 * Date                     							Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2017年2月20日 下午2:18:12          xiaopang        1.0         To create
 * </p>
 *
 * @since
 * @see
 */
@Data
public class OrderInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2035671803577958157L;

    private String id;

    private String order_from;//订单来源，京东 淘宝 天猫

    private String order_id; // 管易的平台单号也是京东或者淘宝的真是单号

    private String seller_nick;//卖家名称

    private String seller_id;//卖家id

    private String order_total_price;//订单总金额

    private String order_seller_price;//订单货款金额（订单总金额-商家优惠金额）

    private String order_payment;//订单实付金额

    private String seller_discount;//商家优惠金额

    private String freight_price;//运费

    private Integer delivery_state;//发货状态，0未发货，1其他已发货

    private String create_time; //下单时间,格式:yyyy-MM-dd HH:mm:ss

    private String pay_time;//付款时间,未付款为空

    private String invoice_info;//发票信息，包括发票类型和发票抬头

    private String buyer_remark;//买家备注

    private String seller_remark;//卖家备注

    private String delivery_time; //发货时间

    /*******************买家信息******************************/

    private String receiver_name;//收货人姓名

    private String receiver_phone;//收货人电话

    private String receiver_mobile;//收货人手机

    private String receiver_address;//收货人详细地址

    private String receiver_province;//收货人省

    private String receiver_city;//收货人市

    private String receiver_county;//收货人县

    /**
     * 商品信息
     * json格式 [{},{}]
     * sku_id , outer_sku_id, sku_name, price, num, total_fee
     */
    private List<Map<String, String>> goods_info;//商品信息

    //********************闪电侠新增****************************************
    private String is_refund;//是否有退款或者退货   1有  0没有

    //********************蓝精灵新增****************************************
    private String order_status; //订单状态(汉字)
    private String buyer_id; // 买家账号(member_mobile) 手机号 蓝精灵用来检索用户的订单的
    private String buyer_outer_code;//买家外部编码
    private String pay_type; // 付款方式
    private Long plateform_id; //平台id

    //********************畅途新增****************************************
    private String car_no; //车牌号

    private String vin_code; //vin码

    private String brand; //车辆品牌

    private String supplier; //供应商名称

    private String series; //车系

    private String year; //年款

    //********************订单接口新增****************************************
    private String update_time;//更新时间,格式:yyyy-MM-dd HH:mm:ss
    private String pay_sn;//支付订单编号
    private String view_account;//首次查看人
    private String view_date;//查看时间
    private Integer check_status = 0; //对账状态，0-未对账，1-已对账
    private String taxmoney;//税款
    private String iftaxes;//是否开具发票  1：开具 2：不开具发票
    private String transport_way; //运输方式
    private String transport_company; //运输公司
    private Integer cancel; //0：默认，1：买家取消订单，2:卖家审核不通过
    private String cancel_remark; //买家申请取消订单的备注
    private String cancel_back; //卖家作废备注
    private Long customer_address_id; //收货地址ID
    private String inquiry_num;//询价单单号
    private String business_audit_back; //业务审核备注
    private String finance_audit_back; //财务审核备注
    private String hope_arrive_date; //期望到货日期

    private String store_title; //汽修店门店名称，买家全称
    private String erp_order_id;//erp单号
    private String purchase_id;//买方单号
    private String cancel_date;//订单取消时间

    private String store_short_title;//买家简称

}
