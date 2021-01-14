package com.mondris.pastebinapp.demo.DTO;

import com.mondris.pastebinapp.demo.DTO.BaseDto.SnippetBaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnippetRequestDto extends SnippetBaseDto {
    private  long expires_at;
}
