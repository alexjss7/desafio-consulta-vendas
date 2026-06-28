package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    public SaleMinDTO findById(Long id) {
        Optional<Sale> result = repository.findById(id);
        Sale entity = result.get();
        return new SaleMinDTO(entity);
    }

    public Page<SaleReportDTO> report(String startDateStr, String endDateStr, String name, Pageable pageable) {

        LocalDate endDate = (endDateStr == null || endDateStr.isEmpty())
                ? LocalDate.now()
                : LocalDate.parse(endDateStr);

        LocalDate startDate = (startDateStr == null || startDateStr.isEmpty())
                ? endDate.minusYears(1)
                : LocalDate.parse(startDateStr);

        String sellerName = (name == null || name.isEmpty())
                ? null
                : name;
        return repository.report(startDate, endDate, sellerName, pageable);
    }

    public List<SaleSummaryDTO> summary(String startDateStr, String endDateStr) {

        LocalDate endDate = (endDateStr == null || endDateStr.isEmpty())
                ? LocalDate.now()
                : LocalDate.parse(endDateStr);

        LocalDate startDate = (startDateStr == null || startDateStr.isEmpty())
                ? endDate.minusYears(1)
                : LocalDate.parse(startDateStr);

        return repository.summary(startDate, endDate);
    }
}
