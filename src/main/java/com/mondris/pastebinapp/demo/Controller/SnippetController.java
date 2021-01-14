package com.mondris.pastebinapp.demo.Controller;

import com.mondris.pastebinapp.demo.DTO.SnippetRequestDto;
import com.mondris.pastebinapp.demo.DTO.SnippetResponseDto;
import com.mondris.pastebinapp.demo.Service.SnippetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class SnippetController {
    @Resource
    private SnippetService snippetService;

    // method to create a new snippet
    @PostMapping("/snippets")
    public ResponseEntity<Object> createSnippet(@RequestBody SnippetRequestDto snippetRequestDto){
            return snippetService.createSnippet(snippetRequestDto);
    }

    @PostMapping("/snippets/{name}")
    public ResponseEntity<Object>  getSnippet(@PathVariable String name) {
        return snippetService.getSnippetByName(name);
    }
    // route to like a snippet
    @PostMapping("/snippets/{name}/like")
    public ResponseEntity<Object>  likeSnippetContent(@RequestBody SnippetRequestDto snippetRequestDto) {
        return snippetService.likeSnippetContent(snippetRequestDto.getName());
    }

}
