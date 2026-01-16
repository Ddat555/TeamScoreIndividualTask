package org.teamscore.individualTask.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.teamscore.individualTask.exceptions.CannotDeleteEntityException;
import org.teamscore.individualTask.models.DTO.entity.TypePaymentDTO;
import org.teamscore.individualTask.models.DTO.entity.createDTO.CreateTypePaymentDTO;
import org.teamscore.individualTask.models.entity.TypePayment;
import org.teamscore.individualTask.repositories.TypePaymentRepository;

import java.util.List;

@Service
public class TypePaymentService {

    @Autowired
    private TypePaymentRepository typePaymentRepository;
    @Autowired
    private ConverterDTOService converterDTOService;

    public TypePaymentDTO createTypePayment(CreateTypePaymentDTO typePaymentDTO){
        var typePayment = new TypePayment(
                typePaymentDTO.getName()
        );
        return converterDTOService.typePaymentConvert(typePaymentRepository.save(typePayment));
    }

    public TypePaymentDTO updateTypePayment(TypePaymentDTO typePayment){
        var oldTypePayment = typePaymentRepository.findById(typePayment.getId());
        if(oldTypePayment.isEmpty())
            return null;
        var oldTypePaymentPres = oldTypePayment.get();
        oldTypePaymentPres.setName(typePayment.getName());
        return converterDTOService.typePaymentConvert(typePaymentRepository.save(oldTypePaymentPres));
    }

    public void deleteTypePayment(Long id){
        var type = typePaymentRepository.findById(id).orElse(null);
        deleteTypePayment(type);
    }

    public void deleteTypePayment(TypePayment typePayment){
        if(typePayment != null){
            if(!typePayment.getCosts().isEmpty())
                throw new CannotDeleteEntityException(String.format("Невозможно удалить %s с ID %d, так как он используется", typePayment.getName(), typePayment.getId()));
            typePaymentRepository.delete(typePayment);
        }
    }

    public List<TypePaymentDTO> getAllTypePayment(Pageable pageable){
        return typePaymentRepository.findAll(pageable).stream().map(typ -> converterDTOService.typePaymentConvert(typ)).toList();
    }

    public TypePaymentDTO getTypePaymentByName(String name){
        return converterDTOService.typePaymentConvert(typePaymentRepository.findByName(name).orElse(null));
    }


}
