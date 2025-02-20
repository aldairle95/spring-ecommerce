package com.curso_ecommerce.service;

import com.curso_ecommerce.model.Orden;

import java.util.List;
import java.util.Optional;

public interface IOrdenService {
    public List<Orden> findAll();
    public Orden save(Orden orden);
    public Optional<Orden>get(Integer id);
    public void update(Orden orden);
    public void delete(Integer id);
}
