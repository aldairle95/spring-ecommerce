package com.curso_ecommerce.controller;


import com.curso_ecommerce.model.DetalleOrden;
import com.curso_ecommerce.model.Orden;
import com.curso_ecommerce.model.Producto;
import com.curso_ecommerce.model.Usuario;
import com.curso_ecommerce.service.ProductoService;
import com.curso_ecommerce.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log= LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    //  para almacenar los detalles de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

    //datos de la orden
    Orden orden = new Orden();

    @GetMapping("")
    public String home(Model model){
        model.addAttribute("productos",productoService.findAll());
        return "usuario/home";
    }

    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Integer id, Model model){
        log.info("variable enviado como parametro {}",id);
        Producto producto= new Producto();
        Optional<Producto> productoOptional= productoService.get(id);
        producto= productoOptional.get();
        model.addAttribute("producto",producto);
        return "usuario/productohome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model){
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto= new Producto();
        double sumaTotal= 0;
        Optional<Producto> productoOptional= productoService.get(id);
        log.info("producto añadido {}",productoOptional.get());
        log.info("cantidad: {}", cantidad);
        producto=productoOptional.get();

        detalleOrden.setCantidad(cantidad.doubleValue());
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio() *cantidad);
        detalleOrden.setProducto(producto);

        //validar que el producto no se añada 2 veces
        Integer idProducto= producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p->p.getProducto().getId()==idProducto);
        if (!ingresado){
            detalles.add(detalleOrden);
        }

        sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);

        return "usuario/carrito";
    }

    // quitar un producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductCart(@PathVariable Integer id,Model model){
        //lista nueva de productos
        List<DetalleOrden> ordenesNueva= new ArrayList<DetalleOrden>();

        for (DetalleOrden detalleOrden : detalles){
            if (detalleOrden.getProducto().getId() != id){
                ordenesNueva.add(detalleOrden);
            }
        }
        // poner la lista para los productos restantes
        detalles=ordenesNueva;
        double sumaTotal= 0;
        sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);

        return "usuario/carrito";

    }

    @GetMapping("/getcart")
    public String getcart(Model model){
        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);
        return "usuario/carrito";
    }

    @GetMapping("/order")
    public String order(Model model){
        Usuario usuario = usuarioService.get(1).get();
        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);
        model.addAttribute("usuario",usuario);
        return "usuario/resumenorden";
    }

}
