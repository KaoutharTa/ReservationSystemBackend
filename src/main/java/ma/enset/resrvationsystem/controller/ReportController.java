package ma.enset.resrvationsystem.controller;
import ma.enset.resrvationsystem.entity.Report;
import ma.enset.resrvationsystem.entity.User;
import ma.enset.resrvationsystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/presence")
    public ResponseEntity<List<Report>> generatePresenceReports() {
        List<Report> reports = reportService.generatePresenceReports();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @GetMapping("/high-reservation-rate")
    public ResponseEntity<List<User>> getUsersWithHighReservationRate() {
        List<User> users = reportService.getUsersWithHighReservationRate();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}

