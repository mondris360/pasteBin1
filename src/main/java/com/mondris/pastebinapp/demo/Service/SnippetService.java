package com.mondris.pastebinapp.demo.Service;

import com.mondris.pastebinapp.demo.DTO.SnippetRequestDto;
import org.springframework.http.ResponseEntity;

public interface SnippetService {
    public ResponseEntity<Object> createSnippet(SnippetRequestDto snippet);
    public ResponseEntity<Object>  likeSnippetContent(String snippetName) ;
    public ResponseEntity<Object>  getSnippetByName(String snippetName);
}
