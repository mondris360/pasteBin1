package com.mondris.pastebinapp.demo.DTO;

import com.mondris.pastebinapp.demo.DTO.BaseDto.PasteBaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasteResDto extends PasteBaseDto {
    private LocalDateTime expires_at;
}
