package com.panagiotisbrts.commentservice.repositories;

import com.panagiotisbrts.commentservice.domain.Comment;

import org.springframework.data.jpa.repository.JpaRepository;



/**
 * @author Panagiotis_Baroutas
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {



}
