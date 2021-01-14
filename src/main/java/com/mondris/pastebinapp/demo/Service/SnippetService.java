package com.mondris.pastebinapp.demo.Service;

import com.mondris.pastebinapp.demo.DTO.SnippetRequestDto;
import com.mondris.pastebinapp.demo.DTO.SnippetResponseDto;

public interface SnippetService {
    public SnippetResponseDto createSnippet(SnippetRequestDto newPaste) throws Exception;
    public SnippetResponseDto likeSnippetContent(String pasteBinName) throws Exception;
    public SnippetResponseDto getSnippetByName(String pasteBinName) throws  Exception;
}
