package com.example.catalogservice.controller;

import com.example.catalogservice.dto.ResponseCatalog;
import com.example.catalogservice.entity.CatalogEntity;
import com.example.catalogservice.service.CatalogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * << 개정이력 >>
 * - 2024-11-16 [ecbank-lyj] 최초 생성
 * </pre>
 *
 * @author ecbank-lyj
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/catalog-service")
@Slf4j
@RequiredArgsConstructor
public class CatalogController {

    private final Environment environment;
    private final CatalogService catalogService;

    @GetMapping("/health_check")
    public String status(HttpServletRequest request) {
        return String.format("It's Working in Catalog Service on Port %s", environment.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogs() {
        Iterable<CatalogEntity> allCatalogs = catalogService.getAllCatalogs();

        List<ResponseCatalog> catalogs = new ArrayList<>();
        allCatalogs.forEach(catalogEntity -> {
            catalogs.add(new ModelMapper().map(catalogEntity, ResponseCatalog.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(catalogs);
    }
}
