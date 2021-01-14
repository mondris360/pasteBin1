package com.mondris.pastebinapp.demo.DTO.BaseDto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnippetBaseDto {
    private String name;
    private String url;
    private String  snippet;
    private String password;
}


