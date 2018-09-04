package com.uqiauto.api.secret.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "wms_app_system")
public class AppSystem implements Serializable {

    private static final long serialVersionUID = -6629918430373064516L;

    @Id
    @javax.persistence.Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    /**
     * 系统名称
     */
    @Column(name = "app_name")
    private String appName;

    /**
     * 访问key要保证唯一性
     */
    @Column(name = "app_key")
    private String appKey;

    /**
     * 访问密钥
     */
    @Column(name = "app_secret")
    private String appSecret;

    /**
     * 每个系统的域名
     */
    @Column(name = "url")
    private String url;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 供应商编号
     */
    @Column(name = "company_id")
    private Long companyId;

}
