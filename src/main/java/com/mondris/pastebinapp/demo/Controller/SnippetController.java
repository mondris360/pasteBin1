package com.mondris.pastebinapp.demo.Controller;

import com.mondris.pastebinapp.demo.DTO.SnippetRequestDto;
import com.mondris.pastebinapp.demo.DTO.SnippetResponseDto;
import com.mondris.pastebinapp.demo.Service.SnippetService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class SnippetController {
    @Resource
    private SnippetService snippetService;

    // method to create a new snippet
    @PostMapping("/createSnippet")
    public SnippetResponseDto createdSnippet(@RequestBody SnippetRequestDto snippetRequestDto) throws Exception {
            return snippetService.createSnippet(snippetRequestDto);
    }

    @PostMapping("/snippets/{name}")
    public SnippetResponseDto getPasteBin(@PathVariable String name) throws Exception {
        return snippetService.getSnippetByName(name);
    }
    // route to like a paste bin content
    @PostMapping("/likeSnippet")
    public SnippetResponseDto likeSnippetContent(@RequestBody SnippetRequestDto snippetRequestDto) throws Exception {
        return snippetService.likeSnippetContent(snippetRequestDto.getName());
    }

}
