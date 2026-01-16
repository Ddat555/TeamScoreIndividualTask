package org.teamscore.individualTask.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.teamscore.individualTask.exceptions.CannotDeleteEntityException;
import org.teamscore.individualTask.models.DTO.entity.CostDTO;
import org.teamscore.individualTask.models.DTO.entity.createDTO.CreateCostDTO;
import org.teamscore.individualTask.models.entity.Category;
import org.teamscore.individualTask.models.entity.Cost;
import org.teamscore.individualTask.models.entity.TypePayment;
import org.teamscore.individualTask.repositories.CostRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CostService {

    @Autowired
    private CostRepository costRepository;
    @Autowired
    private ConverterDTOService converterDTOService;

    public CostDTO createCost(CreateCostDTO costDTO){
        Cost cost = new Cost(
                costDTO.getSellerName(),
                costDTO.getSum(),
                converterDTOService.typePaymentConvert(costDTO.getTypePayment()),
                costDTO.getCategories().stream()
                        .map(cat -> converterDTOService.categoryConvert(cat))
                        .collect(Collectors.toList())
        );
        return converterDTOService.costConvert(costRepository.save(cost));
    }

    public CostDTO updateCost(CostDTO cost){
        var oldCost = costRepository.findById(cost.getId());
        if(oldCost.isEmpty())
            return null;
        var oldCostPres = oldCost.get();
        oldCostPres.setCategories(cost.getCategories().stream()
                .map(cat -> converterDTOService.categoryConvert(cat))
                .collect(Collectors.toList()));
        oldCostPres.setTypePayment(converterDTOService.typePaymentConvert(cost.getTypePayment()));
        oldCostPres.setSum(cost.getSum());
        oldCostPres.setSellerName(cost.getSellerName());
        return converterDTOService.costConvert(costRepository.save(oldCostPres));
    }

    public void deleteCost(Long id){
        costRepository.deleteById(id);
    }

    public void deleteCost(Cost cost){
        costRepository.delete(cost);
    }

    public List<CostDTO> getAllCost(Pageable pageable){
        return costRepository.findAll(pageable).stream().map(cost -> converterDTOService.costConvert(cost)).toList();
    }

    public Cost getCostById(Long id){
        return costRepository.findById(id).orElse(null);
    }

    public List<CostDTO> getAllCostByPeriod(LocalDateTime dateFrom, LocalDateTime dateTo){
        return costRepository.findAllByPeriod(dateFrom,dateTo).stream().map(cost -> converterDTOService.costConvert(cost)).toList();
    }
}
