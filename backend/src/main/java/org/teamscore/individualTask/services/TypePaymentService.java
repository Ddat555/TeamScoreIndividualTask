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

    public TypePaymentDTO createTypePayment(CreateTypePaymentDTO typePaymentDTO) {
        var typePayment = new TypePayment(
                typePaymentDTO.getName()
        );
        typePayment = typePaymentRepository.save(typePayment);
        return new TypePaymentDTO(typePayment);
    }

    public TypePaymentDTO getTypePaymentById(Long id){
        var result = typePaymentRepository.findById(id);
        return result.map(TypePaymentDTO::new).orElse(null);
    }

    public TypePaymentDTO updateTypePayment(TypePaymentDTO typePayment) {
        var oldTypePayment = typePaymentRepository.findById(typePayment.getId());
        if (oldTypePayment.isEmpty())
            return null;
        var oldTypePaymentPres = oldTypePayment.get();
        oldTypePaymentPres.setName(typePayment.getName());
        oldTypePaymentPres = typePaymentRepository.save(oldTypePaymentPres);
        return new TypePaymentDTO(oldTypePaymentPres);
    }

    public void deleteTypePayment(Long id) {
        var type = typePaymentRepository.findById(id).orElse(null);
        deleteTypePayment(type);
    }

    public void deleteTypePayment(TypePayment typePayment) {
        if (typePayment != null) {
            if (!typePayment.getCosts().isEmpty())
                throw new CannotDeleteEntityException(String.format("Невозможно удалить %s с ID %d, так как он используется", typePayment.getName(), typePayment.getId()));
            typePaymentRepository.delete(typePayment);
        }
    }

    public List<TypePaymentDTO> getAllTypePayment(Pageable pageable) {
        return typePaymentRepository.findAll(pageable).stream().map(TypePaymentDTO::new).toList();
    }

    public TypePaymentDTO getTypePaymentByName(String name) {
        var typeOpt = typePaymentRepository.findByName(name);
        return typeOpt.map(TypePaymentDTO::new).orElse(null);
    }


}
