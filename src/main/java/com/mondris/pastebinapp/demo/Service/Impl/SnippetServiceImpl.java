package com.mondris.pastebinapp.demo.Service.Impl;

import com.mondris.pastebinapp.demo.DTO.SnippetRequestDto;
import com.mondris.pastebinapp.demo.DTO.SnippetResponseDto;
import com.mondris.pastebinapp.demo.Model.Snippet;
import com.mondris.pastebinapp.demo.Repository.SnippetRepository;
import com.mondris.pastebinapp.demo.Service.SnippetService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> createSnippet(SnippetRequestDto newPaste)  {
        Snippet snippet = new Snippet();
        SnippetResponseDto snippetResponseDto;
        ResponseEntity<Object> responseEntity;

        try {
            // create a new snippet  Object from the DTO
            snippet.setName(newPaste.getName().toLowerCase());
            snippet.setPassword(newPaste.getPassword());
            snippet.setSnippet(newPaste.getSnippet());
            snippet.setExpires_at(LocalDateTime.now().plusSeconds(newPaste.getExpires_at()));

            // since we are using the snippet name as  our unique key, this should return null if the name already exists
            Snippet createdSnippet = snippetRepository.save(snippet);
            if (createdSnippet.getCreated_at() == null) {
                String error = "A snippet with the name " + newPaste.getName() + " already exists";
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
            snippetResponseDto = convertToSnippetResponseDto(createdSnippet);
            responseEntity = new ResponseEntity<>(snippetResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            String error = e.getMessage();
            log.warn(error);
            responseEntity = new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity ;
    }


    @Override
    // method to like a snippet
    public ResponseEntity<Object>  likeSnippetContent(String snippetName) {
        ResponseEntity<Object> responseEntity;
        Snippet retrievedSnippet = null;
        Snippet snippet;
        SnippetResponseDto snippetResponseDto = null;
        int extendExpiresAtBySeconds = 30;


        try {
            retrievedSnippet = snippetRepository.getByName(snippetName.toLowerCase());
            if (retrievedSnippet == null) {
                responseEntity =  new ResponseEntity<>(HttpStatus.NOT_FOUND);
                // check if the snippet has expired
            } else if (retrievedSnippet.getExpires_at().isAfter(LocalDateTime.now())) {
                responseEntity =  new ResponseEntity<>(HttpStatus.NOT_FOUND);

            } else {
                // extend the expiring date by X seconds
                retrievedSnippet.setExpires_at(retrievedSnippet.getExpires_at().plusSeconds(extendExpiresAtBySeconds));
                retrievedSnippet.setLikes(retrievedSnippet.getLikes() + 1);
                snippet = snippetRepository.save(retrievedSnippet);
                snippetResponseDto = convertToSnippetResponseDto(snippet);
                responseEntity = new ResponseEntity<>(snippetResponseDto, HttpStatus.OK);
            }

        } catch (Exception e) {
            log.warn(e.getMessage());
            responseEntity =  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    // method to retrieve snippet by name
    @Override
    public ResponseEntity<Object>  getSnippetByName(String snippetName) {
        SnippetResponseDto snippetResponseDto = null;
        Snippet retrievedSnippet = null;
        ResponseEntity<Object> responseEntity;

        if (snippetName == null) {
            throw new IllegalArgumentException("The field name cannot be null");
        }

        try {
            retrievedSnippet = snippetRepository.getByName(snippetName.toLowerCase());
            if (retrievedSnippet == null) {
                responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                // if the snippet has expired
            } else if (retrievedSnippet.getExpires_at().isAfter(LocalDateTime.now())) {
                responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                snippetResponseDto = convertToSnippetResponseDto(retrievedSnippet);
                responseEntity =  new ResponseEntity<>(snippetResponseDto, HttpStatus.OK);
            }

        } catch (Exception e) {
            log.warn(e.getMessage());
            responseEntity =  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
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
