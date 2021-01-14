package com.mondris.pastebinapp.demo.Service.Impl;

import com.mondris.pastebinapp.demo.DTO.SnippetRequestDto;
import com.mondris.pastebinapp.demo.DTO.SnippetResponseDto;
import com.mondris.pastebinapp.demo.Model.Snippet;
import com.mondris.pastebinapp.demo.Repository.SnippetRepository;
import com.mondris.pastebinapp.demo.Service.SnippetService;
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
            // current time + 30 seconds
            snippet.setExpires_at(LocalDateTime.now().plusSeconds(newPaste.getExpires_in()));
            System.out.println(snippet);
            // since we are using the snippet name as  our unique key, this should return null if the name already exists
            Snippet createdSnippet = snippetRepository.save(snippet);
            if (createdSnippet.getCreated_at() == null) {
                String error = "A snippet with the name " + newPaste.getName() + " already exists";
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
            snippetResponseDto = convertToSnippetResponseDto(createdSnippet);
            responseEntity = new ResponseEntity<>(snippetResponseDto, HttpStatus.CREATED);

        } catch (Exception e) {
            String error = e.getMessage();
            log.warn(error);
            responseEntity = new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity ;
    }


    @Override
    // method to like a snippet and increment the expiring time by 30secs
    public ResponseEntity<Object>  likeSnippetContent(String snippetName) {
        ResponseEntity<Object> responseEntity;
        Snippet retrievedSnippet = null;
        Snippet snippet;
        SnippetResponseDto snippetResponseDto = null;
        int extendExpiresAtBySeconds = 30;

        if (snippetName == null) {
            return new ResponseEntity<>("Snippet name cannot be null", HttpStatus.BAD_REQUEST);
        }

        try {
            retrievedSnippet = snippetRepository.getByName(snippetName.toLowerCase());
            if (retrievedSnippet == null) {
                responseEntity =  new ResponseEntity<>(HttpStatus.NOT_FOUND);
                // check if the snippet has expired
            } else if ((LocalDateTime.now().isAfter(retrievedSnippet.getExpires_at()))) {
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
        int extendExpiresAtBySeconds = 30;
        SnippetResponseDto snippetResponseDto = null;
        Snippet retrievedSnippet = null;
        ResponseEntity<Object> responseEntity;

        if (snippetName == null) {
            return new ResponseEntity<>("Snippet name cannot be null", HttpStatus.BAD_REQUEST);
        }

        try {
            retrievedSnippet = snippetRepository.getByName(snippetName.toLowerCase());
            if (retrievedSnippet == null) {
                responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                // if the snippet has expired
            } else if ((LocalDateTime.now().isAfter(retrievedSnippet.getExpires_at()))) {
                responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                // extend the expiring date by X seconds and update the snippet in the db
                retrievedSnippet.setExpires_at(retrievedSnippet.getExpires_at().plusSeconds(extendExpiresAtBySeconds));
                retrievedSnippet = snippetRepository.save(retrievedSnippet);
                snippetResponseDto = convertToSnippetResponseDto(retrievedSnippet);

                responseEntity =  new ResponseEntity<>(snippetResponseDto, HttpStatus.OK);
            }

        } catch (Exception e) {
            log.warn(e.getMessage());
            responseEntity =  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }


    // method to convert a Snippet Model to  snippetResponseDto
    private SnippetResponseDto convertToSnippetResponseDto(Snippet snippet) {
        SnippetResponseDto snippetResponseDto = new SnippetResponseDto();
        snippetResponseDto.setName(snippet.getName());
        snippetResponseDto.setExpires_at(snippet.getExpires_at());
        snippetResponseDto.setSnippet(snippet.getSnippet());
        snippetResponseDto.setUrl(baseUrl + snippet.getName());
        snippetResponseDto.setPassword(snippet.getPassword());
        snippetResponseDto.setLikes(snippet.getLikes());

        return snippetResponseDto;
    }

}
