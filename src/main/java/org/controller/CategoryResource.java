package org.controller;

import org.dto.CartDto;
import org.dto.CategoryDto;
import org.dto.ProductDto;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.service.CartService;
import org.service.CategoryService;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("/categories")
@Tag(name = "сategory", description = "All the сategory methods")
public class CategoryResource {
    @Inject
    CategoryService categoryService;

    @GET
    public List<CategoryDto> findAll() {
        return this.categoryService.findAll();
    }

    @GET
    @Path("/{id}")
    public CategoryDto findById(@PathParam("id") Long id) {
        return this.categoryService.findById(id);
    }

    @GET
    @Path("/{id}/products")
    public List<ProductDto> findProductsByCategoryId(@PathParam("id") Long id) {
        return this.categoryService.findProductsByCategoryId(id);
    }

    @POST
    public CategoryDto create(CategoryDto categoryDto) {
        return this.categoryService.create(categoryDto);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        this.categoryService.delete(id);
    }
}
