package com.mondris.pastebinapp.demo.Service;

import com.mondris.pastebinapp.demo.DTO.BaseDto.PasteBaseDto;
import com.mondris.pastebinapp.demo.DTO.PasteReqDto;
import com.mondris.pastebinapp.demo.DTO.PasteResDto;
import com.mondris.pastebinapp.demo.Model.Paste;

public interface PasteBinService {
    public PasteResDto createPaste(PasteReqDto newPaste) throws Exception;
    public PasteResDto likePasteContent(String pasteBinName) throws Exception;
    public  PasteResDto getPasteBinByName(String pasteBinName) throws  Exception;
}
