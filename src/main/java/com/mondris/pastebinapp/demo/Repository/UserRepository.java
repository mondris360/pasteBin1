package com.mondris.pastebinapp.demo.Repository;

import com.mondris.pastebinapp.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
