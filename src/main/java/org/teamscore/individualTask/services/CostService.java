package org.teamscore.individualTask.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.teamscore.individualTask.exceptions.CategoryNotFoundException;
import org.teamscore.individualTask.exceptions.TypePaymentNotFoundException;
import org.teamscore.individualTask.models.DTO.entity.CostDTO;
import org.teamscore.individualTask.models.DTO.entity.createDTO.CreateCostDTO;
import org.teamscore.individualTask.models.entity.Cost;
import org.teamscore.individualTask.repositories.CategoryRepository;
import org.teamscore.individualTask.repositories.CostRepository;
import org.teamscore.individualTask.repositories.TypePaymentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CostService {

    @Autowired
    private CostRepository costRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TypePaymentRepository typePaymentRepository;

    @Transactional
    public CostDTO createCost(CreateCostDTO costDTO) {
        Cost cost = new Cost(
                costDTO.getSellerName(),
                costDTO.getSum(),
                typePaymentRepository.findById(costDTO.getTypePayment().getId()).orElseThrow(
                        () -> new TypePaymentNotFoundException("Тип оплаты с ид " + costDTO.getTypePayment().getId() + " не найден")),
                costDTO.getCategories().stream()
                        .map(cat -> categoryRepository.findById(cat.getId()).orElseThrow(
                                () -> new CategoryNotFoundException("Категория с ид " + cat.getId() + " не найдена")))
                        .collect(Collectors.toSet())
        );
        return costRepository.save(cost).toDTO();
    }

    @Transactional
    public CostDTO updateCost(CostDTO cost) {
        var oldCost = costRepository.findById(cost.getId());
        if (oldCost.isEmpty())
            return null;
        var oldCostPres = oldCost.get();
        oldCostPres.setCategories(cost.getCategories().stream()
                .map(cat -> categoryRepository.findById(cat.getId()).orElseThrow(
                        () -> new CategoryNotFoundException("Категория с ид " + cat.getId() + " не найдена")))
                .collect(Collectors.toSet()));
        oldCostPres.setTypePayment(typePaymentRepository.findById(cost.getTypePayment().getId()).orElseThrow(
                () -> new TypePaymentNotFoundException("Тип оплаты с ид " + cost.getTypePayment().getId() + " не найден")));
        oldCostPres.setSum(cost.getSum());
        oldCostPres.setSellerName(cost.getSellerName());
        return costRepository.save(oldCostPres).toDTO();
    }

    public void deleteCost(Long id) {
        costRepository.deleteById(id);
    }

    public void deleteCost(Cost cost) {
        costRepository.delete(cost);
    }

    public List<CostDTO> getAllCost(Pageable pageable) {
        return costRepository.findAll(pageable).stream().map(Cost::toDTO).toList();
    }

    public Cost getCostById(Long id) {
        return costRepository.findById(id).orElse(null);
    }

    public List<CostDTO> getAllCostByPeriod(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return costRepository.findAllByPeriod(dateFrom, dateTo).stream().map(Cost::toDTO).toList();
    }
}
