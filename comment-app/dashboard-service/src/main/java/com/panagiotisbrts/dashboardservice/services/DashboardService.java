package com.panagiotisbrts.dashboardservice.services;

import com.panagiotisbrts.clients.commentservice.CommentResponse;
import com.panagiotisbrts.dashboardservice.web.model.CommentDto;
import com.panagiotisbrts.dashboardservice.web.model.CommentRequest;

import java.util.List;

/**
 * @author Panagiotis_Baroutas
 */
public interface DashboardService {

   List<CommentResponse>  getComments();

   void addComment(CommentRequest commentRequest);

   List<CommentDto> getLatestComments();
}
