package com.panagiotisbrts.dashboardservice.services;

import com.panagiotisbrts.dashboardservice.web.model.CommentDto;
import com.panagiotisbrts.dashboardservice.web.model.CommentResponse;

import java.util.List;

/**
 * @author Panagiotis_Baroutas
 */
public interface DashboardService {

    public List<CommentResponse>  getComments();

}
