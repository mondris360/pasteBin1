package com.mondris.pastebinapp.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Snippet {
    @Id
    @Column(name = "name", nullable = false)
    @NotBlank(message = "name is mandatory")
    private String name;
    private  String password;
    @NotBlank(message = "snippet is mandatory")
    private String snippet;
    private LocalDateTime expires_at;
    private int likes;
    @CreationTimestamp
    private Timestamp created_at;
    @UpdateTimestamp
    private Timestamp updated_at;
}
