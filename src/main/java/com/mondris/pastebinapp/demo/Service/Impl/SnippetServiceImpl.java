package com.mondris.pastebinapp.demo.Service.Impl;

import com.mondris.pastebinapp.demo.DTO.SnippetRequestDto;
import com.mondris.pastebinapp.demo.DTO.PasteResDto;
import com.mondris.pastebinapp.demo.Model.Paste;
import com.mondris.pastebinapp.demo.Repository.SnippetRepository;
import com.mondris.pastebinapp.demo.Service.PasteBinService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class SnippetServiceImpl implements PasteBinService {
    private String baseUrl = "https://example.com/snippets/";
    @Resource   // better than using @ Autowired
    private SnippetRepository snippetRepository;
    LocalDateTime localDateTime;

    @Override
    public PasteResDto createSnippet(SnippetRequestDto newPaste) throws Exception {
        Paste paste = new Paste();
        PasteResDto pasteResDto;
        // create a new paste Object from the DTO
        paste.setName(newPaste.getName());
        paste.setPassword(newPaste.getPassword());
        paste.setSnippet(newPaste.getSnippet());
        paste.setExpires_at(LocalDateTime.now().plusSeconds(newPaste.getExpires_at()));

        try {
            // since we are using the paste bin name as a unique, this should return null if the name already exists
            Paste createdPaste =  snippetRepository.save(paste);
            if(createdPaste.getCreated_at() == null){
                throw  new IllegalArgumentException("A paste with the name " + newPaste.getName() +"Already exists");
            }
            pasteResDto = convertToPasteResDTO(createdPaste);
        }catch (Exception exception){
            throw  new Exception(exception.getMessage());
        }

        return pasteResDto;
    }



    @Override
    // method to like a paste content
    public PasteResDto likeSnippetContent(String pasteBinName) throws Exception{
        Paste getPaste = null;
        Paste paste ;
        PasteResDto apiResponse = null;
        int extendExpiresAtBySeconds = 30;

        try {
            getPaste =  snippetRepository.getByName(pasteBinName);
            // check if a valid paste bin name was provided by the user
            if (getPaste == null){
                throw new IllegalArgumentException("Invalid paste Name");
                // if the paste has expired.
            } else if (getPaste.getExpires_at().isAfter(LocalDateTime.now()) ){
                throw new IllegalArgumentException("Paste Bin has Expired");
                //Todo: Delete it from the db
            } else {
                // extend the expiring date by x seconds
                getPaste.setExpires_at(getPaste.getExpires_at().plusSeconds(extendExpiresAtBySeconds));
                getPaste.setTotalLikes(getPaste.getTotalLikes() + 1);
                paste = snippetRepository.save(getPaste);
                apiResponse = convertToPasteResDTO(paste);
            }
        }catch ( Exception e){
                throw new Exception(e.getMessage());
        }

        return apiResponse;
    }



    // method to retrieve paste bin content by Name
    @Override
    public PasteResDto getSnippetByName(String pasteBinName) throws Exception {
        PasteResDto pasteResDto = null;
        Paste retrievedPaste =  null;

        if (pasteBinName == null){
            throw new IllegalArgumentException("The field name cannot be null");
        }

        try {
            retrievedPaste =  snippetRepository.getByName(pasteBinName);

            if(retrievedPaste == null){
                throw  new NotFoundException("404 Not Found");
                // if the snippet has expired
            } else if(retrievedPaste.getExpires_at().isAfter(LocalDateTime.now())){
                throw new IllegalAccessException("404 Not Found");
            } else {
                pasteResDto = convertToPasteResDTO(retrievedPaste);
            }
        } catch (Exception e){
            throw  new Exception( e.getMessage());
        }
        return pasteResDto;
    }

    // method to convert a Paste Model to a PasteDto
    private PasteResDto convertToPasteResDTO(Paste paste){
        PasteResDto pasteDto = new PasteResDto();
        pasteDto.setName(paste.getName());
        pasteDto.setExpires_at(paste.getExpires_at());
        pasteDto.setSnippet(paste.getSnippet());
        pasteDto.setUrl(baseUrl + paste.getName());
        return  pasteDto;
    }



}
