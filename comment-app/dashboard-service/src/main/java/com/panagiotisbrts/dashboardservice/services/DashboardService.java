package com.panagiotisbrts.dashboardservice.services;

import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.clients.commentservice.model.CommentRequest;


import java.util.List;

/**
 * @author Panagiotis_Baroutas
 */
public interface DashboardService {

   void addComment(CommentRequest commentRequest);

   List<CommentDto> getLatestComments(List<String> commentUUIds);
}
