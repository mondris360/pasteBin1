package com.mondris.pastebinapp.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    private long Id;
    private String firstName;
    private String lastName;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
