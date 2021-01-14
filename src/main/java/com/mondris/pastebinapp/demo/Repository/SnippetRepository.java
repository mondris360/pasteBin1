package com.mondris.pastebinapp.demo.Repository;

import com.mondris.pastebinapp.demo.Model.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnippetRepository extends JpaRepository<Snippet, Long> {
    Snippet getByName(String pasteBinName);
}
