package com.lazzuri.Market.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.lazzuri.Market.model.ClassPersonageType;
import com.lazzuri.Market.model.Personage;
import com.lazzuri.Market.repository.PersonageRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/personage")
@Slf4j
public class PersonageController {

    @Autowired
    private PersonageRepository repository;

    @GetMapping
    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "Listando todos os personagens"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public List<Personage> index() {
        log.info("Listando todos os personagens");
        return repository.findAll();
    }

    // Cadastrar um personagem
    @PostMapping
    @CacheEvict(value = "personages", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(responses = {
            @ApiResponse(responseCode = "400", description = "Falha na Validação")
    })
    public Personage create(@RequestBody @Valid Personage personage) {
        log.info("Cadastrando personagem: " + personage.getName());
        return repository.save(personage);
    }

    // Retornar um personagem pelo ID
    @GetMapping("{id}")
    public Personage get(@PathVariable Long id) {
        log.info("Buscando personagem com ID: " + id);
        return getPersonage(id);
    }

    // Deletar um personagem pelo ID
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("Apagando personagem com ID: " + id);
        repository.delete(getPersonage(id));
    }

    // Atualizar um personagem pelo ID
    @PutMapping("{id}")
    public Personage update(@PathVariable Long id, @RequestBody @Valid Personage personage) {
        log.info("Atualizando personagem com ID: " + id + " " + personage);
        getPersonage(id);
        personage.setId(id);
        return repository.save(personage);
    }

    // Método auxiliar para buscar um personagem pelo ID
    private Personage getPersonage(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Personagem não encontrado"));
    }

    //Listar personagens por nome 
    @GetMapping("/name")
    public List<Personage> findByName(@RequestParam String name) {
        log.info("Buscando personagens com o nome: " + name);
        return repository.findByNameContainingIgnoreCase(name);}

    //Listar personagens por classe
    @GetMapping("/class")
    List<Personage> findByClass(@RequestParam String classType) {
    log.info("Buscando personagens com a classe: " + classType);
    return repository.findByClassType(ClassPersonageType.valueOf(classType));}
    
} 

