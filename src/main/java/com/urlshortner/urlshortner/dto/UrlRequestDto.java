package com.urlshortner.urlshortner.dto;

import lombok.Data;

@Data
public class UrlRequestDto {
    private String destinationUrl;
    private String shortUrl;
    private int daysToAdd;
}
