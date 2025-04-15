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

import com.lazzuri.Market.model.Item;
import com.lazzuri.Market.model.RareItem;
import com.lazzuri.Market.model.TypeItem;
import com.lazzuri.Market.repository.ItemRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/item")
@Slf4j
public class ItemController {

    @Autowired
    private ItemRepository repository;

    @GetMapping // Adicionado para mapear o método como um endpoint HTTP GET
    public List<Item> index() {
        log.info("Listando todos os itens");
        return repository.findAll();
    }

    // Cadastrar um item
    @PostMapping
    @CacheEvict(value = "items", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(responses = {
            @ApiResponse(responseCode = "400", description = "Falha na Validação")
    })
    public Item create(@RequestBody @Valid Item item) {
        log.info("Cadastrando item: " + item.getName());
        return repository.save(item);
    }

    // Retornar um item pelo ID
    @GetMapping("{id}")
    public Item get(@PathVariable Long id) {
        log.info("Buscando item com ID: " + id);
        return getItem(id);
    }

    // Deletar um item pelo ID
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("Apagando item com ID: " + id);
        repository.delete(getItem(id));
    }

    // Atualizar um item pelo ID
    @PutMapping("{id}")
    public Item update(@PathVariable Long id, @RequestBody @Valid Item item) {
        log.info("Atualizando item com ID: " + id + " " + item);
        getItem(id);
        item.setId(id);
        return repository.save(item);
    }

    // Método auxiliar para buscar um item pelo ID
    private Item getItem(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Item não encontrado"));
    }

    
    // Listar itens por tipo
    @GetMapping("/type")
    public List<Item> findByType(@RequestParam String typeItem) {
        log.info("Buscando itens com o tipo: " + typeItem);
        try {
            TypeItem type = TypeItem.valueOf(typeItem.toUpperCase());
            return repository.findByTypeItem(type);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo inválido: " + typeItem);
        }
    }

    // Listar itens por raridade
    @GetMapping("/rare")
    public List<Item> findByRare(@RequestParam String rareItem) {
        log.info("Buscando itens com a raridade: " + rareItem);
        try {
            RareItem rare = RareItem.valueOf(rareItem.toUpperCase());
            return repository.findByRareItem(rare);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Raridade inválida: " + rareItem);
        }
    }

    // Listar itens por faixa de preço
@GetMapping("/price-range")
public List<Item> findByPriceRange(@RequestParam Integer minPrice, @RequestParam Integer maxPrice) {
    log.info("Buscando itens com preço entre " + minPrice + " e " + maxPrice);
    if (minPrice < 0 || maxPrice < 0) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Os preços devem ser valores positivos.");
    }
    if (minPrice > maxPrice) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O preço mínimo não pode ser maior que o preço máximo.");
    }
    return repository.findByPriceBetween(minPrice, maxPrice);
}





}