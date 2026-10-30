package com.critical_hit.loja.controller;

import com.critical_hit.lib.dto.loja.CarrinhoResponse;
import com.critical_hit.lib.dto.loja.CompraRequest;
import com.critical_hit.lib.enums.StatusCarrinho;
import com.critical_hit.loja.service.CarrinhoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/carrinho")
@RequiredArgsConstructor
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarrinhoResponse comprar(@Valid @RequestBody CompraRequest compraRequest) {
        return carrinhoService.comprar(compraRequest);
    }

    @GetMapping("/{id}")
    public CarrinhoResponse buscarPorId(@PathVariable Integer id) {
        return carrinhoService.buscarPorId(id);
    }

    @GetMapping
    public List<CarrinhoResponse> listarTodos() {
        return carrinhoService.listarTodos();
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<CarrinhoResponse> buscarPorUsuario(@PathVariable Long usuarioId) {
        return carrinhoService.buscarPorUsuario(usuarioId);
    }

    @GetMapping("/status/{status}")
    public List<CarrinhoResponse> buscarPorStatus(@PathVariable StatusCarrinho status) {
        return carrinhoService.buscarPorStatus(status);
    }

    @GetMapping("/data/{data}")
    public List<CarrinhoResponse> buscarPorData(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return carrinhoService.buscarPorData(data);
    }
}