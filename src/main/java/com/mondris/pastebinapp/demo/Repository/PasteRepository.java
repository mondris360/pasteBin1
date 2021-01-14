package com.mondris.pastebinapp.demo.Repository;

import com.mondris.pastebinapp.demo.Model.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasteRepository extends JpaRepository<Paste, Long> {
    Paste getByName(String pasteBinName);
}
