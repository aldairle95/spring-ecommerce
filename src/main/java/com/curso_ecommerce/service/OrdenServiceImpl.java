package com.curso_ecommerce.service;

import com.curso_ecommerce.model.Orden;
import com.curso_ecommerce.repository.IOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenServiceImpl implements IOrdenService{

    @Autowired
    private IOrdenRepository iOrdenRepository;

    @Override
    public List<Orden> findAll() {
        return iOrdenRepository.findAll();
    }

    @Override
    public Orden save(Orden orden) {
        return iOrdenRepository.save(orden);
    }

    @Override
    public Optional<Orden> get(Integer id) {
        return iOrdenRepository.findById(id);
    }

    @Override
    public void update(Orden orden) {
        iOrdenRepository.save(orden);
    }

    @Override
    public void delete(Integer id) {
        iOrdenRepository.deleteById(id);
    }
}
