package com.mondris.pastebinapp.demo.DTO;

import com.mondris.pastebinapp.demo.DTO.BaseDto.SnippetBaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnippetResponseDto extends SnippetBaseDto {
    private LocalDateTime expires_at;
}
