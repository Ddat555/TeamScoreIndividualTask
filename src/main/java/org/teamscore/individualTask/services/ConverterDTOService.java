package org.teamscore.individualTask.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamscore.individualTask.models.DTO.entity.CategoryDTO;
import org.teamscore.individualTask.models.DTO.entity.CostDTO;
import org.teamscore.individualTask.models.DTO.entity.TypePaymentDTO;
import org.teamscore.individualTask.models.entity.Category;
import org.teamscore.individualTask.models.entity.Cost;
import org.teamscore.individualTask.models.entity.TypePayment;
import org.teamscore.individualTask.repositories.CategoryRepository;
import org.teamscore.individualTask.repositories.CostRepository;
import org.teamscore.individualTask.repositories.TypePaymentRepository;

import java.util.stream.Collectors;

@Service
public class ConverterDTOService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TypePaymentRepository typePaymentRepository;
    @Autowired
    private CostRepository costRepository;

    public CategoryDTO categoryConvert(Category category) {
        if (category != null)
            return new CategoryDTO(
                    category.getId(),
                    category.getName(),
                    category.getColor(),
                    category.getDescription()
            );
        else
            return null;
    }

    public Category categoryConvert(CategoryDTO categoryDTO){
        if(categoryDTO != null){
            return categoryRepository.findById(categoryDTO.getId()).orElse(null);
        }
        return null;
    }



    public TypePaymentDTO typePaymentConvert(TypePayment typePayment) {
        if (typePayment != null)
            return new TypePaymentDTO(
                    typePayment.getId(),
                    typePayment.getName()
            );
        else
            return null;
    }

    public TypePayment typePaymentConvert(TypePaymentDTO typePaymentDTO){
        if(typePaymentDTO != null){
            return typePaymentRepository.findById(typePaymentDTO.getId()).orElse(null);
        }
        return null;
    }

    public CostDTO costConvert(Cost cost){
        if(cost != null)
            return new CostDTO(
                    cost.getId(),
                    cost.getDateTimePay(),
                    cost.getSellerName(),
                    cost.getSum(),
                    typePaymentConvert(cost.getTypePayment()),
                    cost.getCategories().stream()
                            .map(this::categoryConvert)
                            .collect(Collectors.toList())
            );
        else
            return null;
    }

    public Cost costConvert(CostDTO costDTO){
        if(costDTO != null){
            return costRepository.findById(costDTO.getId()).orElse(null);
        }
        return null;
    }
}
