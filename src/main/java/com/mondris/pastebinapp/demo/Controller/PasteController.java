package com.mondris.pastebinapp.demo.Controller;

import com.mondris.pastebinapp.demo.DTO.SnippetRequestDto;
import com.mondris.pastebinapp.demo.DTO.PasteResDto;
import com.mondris.pastebinapp.demo.Service.PasteBinService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class PasteController {
    @Resource
    private PasteBinService pasteBinService;

    // method to create a new snippet
    @PostMapping("/createSnippet")
    public PasteResDto createdSnippet(@RequestBody SnippetRequestDto snippetRequestDto) throws Exception {
            return pasteBinService.createSnippet(snippetRequestDto);
    }

    @PostMapping("/snippets/{name}")
    public  PasteResDto getPasteBin(@PathVariable String name) throws Exception {
        return pasteBinService.getSnippetByName(name);
    }
    // route to like a paste bin content
    @PostMapping("/likeSnippet")
    public  PasteResDto likeSnippetContent(@RequestBody SnippetRequestDto snippetRequestDto) throws Exception {
        return pasteBinService.likeSnippetContent(snippetRequestDto.getName());
    }

}
