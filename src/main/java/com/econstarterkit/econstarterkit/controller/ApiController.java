package com.econstarterkit.econstarterkit.controller;

import com.econstarterkit.econstarterkit.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@RestController
@RequestMapping("/update-all-data")
@RequiredArgsConstructor
public class ApiController {
    private final ApiService apiService;

    @GetMapping("/first-setting")
    public ResponseEntity<?> apiUpdate() throws IOException, ParserConfigurationException, SAXException {
        apiService.getApi();
        return ResponseEntity.ok().build();
    }
}
