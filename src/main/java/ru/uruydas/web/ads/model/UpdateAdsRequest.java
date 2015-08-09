package ru.uruydas.web.ads.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import urujdas.model.ads.AdsType;

public class UpdateAdsRequest extends CreateAdsRequest {
    public UpdateAdsRequest(@JsonProperty("title") String title,
                            @JsonProperty("description") String description,
                            @JsonProperty("type") AdsType adsType,
                            @JsonProperty("phone") String phone,
                            @JsonProperty("email") String email,
                            @JsonProperty("city") String city,
                            @JsonProperty("categoryId") Long categoryId) {
        super(title, description, adsType, phone, email, city, categoryId);
    }
}
