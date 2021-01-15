package com.mondris.pastebinapp.demo.DTO;

import com.mondris.pastebinapp.demo.DTO.BaseDto.SnippetBaseDto;
import lombok.*;
import org.aspectj.bridge.IMessage;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

public class SnippetRequestDto extends SnippetBaseDto {
    private  long expires_in;
}
