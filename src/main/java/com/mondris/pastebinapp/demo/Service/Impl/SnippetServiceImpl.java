package com.mondris.pastebinapp.demo.Service.Impl;

import com.mondris.pastebinapp.demo.DTO.SnippetRequestDto;
import com.mondris.pastebinapp.demo.DTO.SnippetResponseDto;
import com.mondris.pastebinapp.demo.Model.Snippet;
import com.mondris.pastebinapp.demo.Repository.SnippetRepository;
import com.mondris.pastebinapp.demo.Service.SnippetService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
@Slf4j
public class SnippetServiceImpl implements SnippetService {
    private String baseUrl = "https://example.com/snippets/";
    @Resource   // better than using @ Autowired
    private SnippetRepository snippetRepository;
    LocalDateTime localDateTime;

    @Override
    public SnippetResponseDto createSnippet(SnippetRequestDto newPaste) throws Exception {
        Snippet snippet = new Snippet();
        SnippetResponseDto pasteResDto;
        // create a new snippet  Object from the DTO
        snippet.setName(newPaste.getName());
        snippet.setPassword(newPaste.getPassword());
        snippet.setSnippet(newPaste.getSnippet());
        snippet.setExpires_at(LocalDateTime.now().plusSeconds(newPaste.getExpires_at()));

        try {
            // since we are using the paste bin name as a unique, this should return null if the name already exists
            Snippet createdSnippet = snippetRepository.save(snippet);
            if (createdSnippet.getCreated_at() == null) {
                throw new IllegalArgumentException("A paste with the name " + newPaste.getName() + "Already exists");
            }
            pasteResDto = convertToSnippetResponseDto(createdSnippet);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new Exception(e.getMessage());
        }

        return pasteResDto;
    }


    @Override
    // method to like a snippet
    public SnippetResponseDto likeSnippetContent(String snippetName) throws Exception {
        Snippet retrievedSnippet = null;
        Snippet snippet;
        SnippetResponseDto apiResponse = null;
        int extendExpiresAtBySeconds = 30;


        try {
            retrievedSnippet = snippetRepository.getByName(snippetName);
            // check if a valid paste bin name was provided by the user
            if (retrievedSnippet == null) {
                throw new IllegalArgumentException("Invalid Snippet Name");
                // check if the snippet has expired
            } else if (retrievedSnippet.getExpires_at().isAfter(LocalDateTime.now())) {
                throw new IllegalArgumentException("Snippet has Expired");
            } else {
                // extend the expiring date by X seconds
                retrievedSnippet.setExpires_at(retrievedSnippet.getExpires_at().plusSeconds(extendExpiresAtBySeconds));
                retrievedSnippet.setLikes(retrievedSnippet.getLikes() + 1);
                snippet = snippetRepository.save(retrievedSnippet);
                apiResponse = convertToSnippetResponseDto(snippet);
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return apiResponse;
    }


    // method to retrieve snippet by name
    @Override
    public SnippetResponseDto getSnippetByName(String pasteBinName) throws Exception {
        SnippetResponseDto pasteResDto = null;
        Snippet retrievedSnippet = null;

        if (pasteBinName == null) {
            throw new IllegalArgumentException("The field name cannot be null");
        }

        try {
            retrievedSnippet = snippetRepository.getByName(pasteBinName);
            if (retrievedSnippet == null) {
                throw new NotFoundException("404 Not Found");
                // if the snippet has expired
            } else if (retrievedSnippet.getExpires_at().isAfter(LocalDateTime.now())) {
                throw new IllegalAccessException("404 Not Found");
            } else {
                pasteResDto = convertToSnippetResponseDto(retrievedSnippet);
            }

        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return pasteResDto;
    }


    // method to convert a Paste Model to a PasteDto
    private SnippetResponseDto convertToSnippetResponseDto(Snippet snippet) {
        SnippetResponseDto pasteDto = new SnippetResponseDto();
        pasteDto.setName(snippet.getName());
        pasteDto.setExpires_at(snippet.getExpires_at());
        pasteDto.setSnippet(snippet.getSnippet());
        pasteDto.setUrl(baseUrl + snippet.getName());
        return pasteDto;
    }


}
