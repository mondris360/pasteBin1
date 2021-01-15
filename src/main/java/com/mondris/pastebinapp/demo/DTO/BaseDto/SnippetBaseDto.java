package com.mondris.pastebinapp.demo.DTO.BaseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnippetBaseDto {
    @NotBlank(message = "name is mandatory")
    private String name;
    private String url;
    @NotBlank(message = "snippet name is mandatory")
    private String  snippet;
    private String password;
}


