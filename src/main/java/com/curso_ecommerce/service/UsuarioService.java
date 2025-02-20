package com.curso_ecommerce.service;

import com.curso_ecommerce.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    public List<Usuario> findAll();
    public Usuario save(Usuario usuario);
    public Optional<Usuario> get(Integer id);
    public void update(Usuario usuario);
    public void delete(Integer id);
}
