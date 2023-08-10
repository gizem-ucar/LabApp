package com.project.labapp.config;

import com.project.labapp.entities.Report;
import com.project.labapp.responses.ReportResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReportMapper {

    public ReportResponse mapToResponse(Report report) {
        ReportResponse response = new ReportResponse(report);
        return response;
    }

    public List<ReportResponse> mapListToResponse(List<Report> reports) {
        return reports.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}