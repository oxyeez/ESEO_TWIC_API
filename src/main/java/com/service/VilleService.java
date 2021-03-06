package com.service;

import java.sql.SQLException;
import java.util.Optional;

import com.dto.VilleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.model.Ville;
import com.repository.VilleRepo;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class VilleService {

    @Autowired
    private VilleRepo villeRepo;

    public Optional<Ville> getVille(final String codeCommune) {
        return villeRepo.findById(codeCommune);
    }
    
    public Object getVilles(final String codePostal, int size, int page) {
    	if (codePostal == null) {
    		if (size != -1 && page != -1) {
    			Pageable pageable = PageRequest.of(page-1, size);
    			return villeRepo.findByOrderByNomCommune(pageable);
    		} else if (size != -1 && page == -1) {
    			Pageable pageable = PageRequest.of(0, size);
    			return villeRepo.findByOrderByNomCommune(pageable);
    		} else {
    			return villeRepo.findByOrderByNomCommune();
    		}
    	} else {
    		if (size != -1 && page != -1) {
    			Pageable pageable = PageRequest.of(page-1, size);
    			return villeRepo.findByCodePostalOrderByNomCommune(codePostal, pageable);
    		} else if (size != -1 && page == -1) {
    			Pageable pageable = PageRequest.of(0, size);
    			return villeRepo.findByCodePostalOrderByNomCommune(codePostal, pageable);
    		} else {
    			return villeRepo.findByCodePostalOrderByNomCommune(codePostal);
    		}
    	}
    }

    public void createVille(VilleDTO ville) throws SQLException {
    	if (villeRepo.existsById(ville.getCodeCommune())) {
    		throw new SQLException("Primary key already exists!");
    	}
    	villeRepo.save(ville.toEntity());
    }

    public void replaceVille(final VilleDTO ville) throws ObjectNotFoundException {
    	if (!villeRepo.existsById(ville.getCodeCommune())) {
    		throw new ObjectNotFoundException("Record does not exists!");
    	}
    	villeRepo.save(ville.toEntity());
    }
    
    public void deleteVille(final String id) throws ObjectNotFoundException {
    	if (!villeRepo.existsById(id)) {
    		throw new ObjectNotFoundException("Record does not exists!");
    	}
    	villeRepo.deleteById(id);
    }
}