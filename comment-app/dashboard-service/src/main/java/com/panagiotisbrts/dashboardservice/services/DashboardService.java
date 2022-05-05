package com.panagiotisbrts.dashboardservice.services;

import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.clients.commentservice.model.CommentRequest;
import com.panagiotisbrts.clients.commentservice.model.CommentResponse;


import java.util.List;

/**
 * @author Panagiotis_Baroutas
 */
public interface DashboardService {

   List<CommentResponse>  getComments();

   void addComment(CommentRequest commentRequest);

   List<CommentDto> getLatestComments();
}
