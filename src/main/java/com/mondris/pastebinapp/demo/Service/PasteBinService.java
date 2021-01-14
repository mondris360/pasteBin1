package com.mondris.pastebinapp.demo.Service;

import com.mondris.pastebinapp.demo.DTO.SnippetRequestDto;
import com.mondris.pastebinapp.demo.DTO.PasteResDto;

public interface PasteBinService {
    public PasteResDto createSnippet(SnippetRequestDto newPaste) throws Exception;
    public PasteResDto likeSnippetContent(String pasteBinName) throws Exception;
    public  PasteResDto getSnippetByName(String pasteBinName) throws  Exception;
}
