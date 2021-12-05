package com.example.demo.society.method;

import com.example.demo.entity.Summary;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Summaryinterface extends JpaRepository<Summary, Integer> {

}
