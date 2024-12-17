package com.project.shopapp.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Province {
    @JsonProperty("province_name")
    private String provinceName;
    @JsonProperty("province_id")
    private String provinceId;
    @JsonProperty("district_name")
    private String districtName;
    @JsonProperty("district_id")
    private String districtId;
    @JsonProperty("commune_name")
    private String communeName;
    @JsonProperty("commune_id")
    private String communeId;


}
