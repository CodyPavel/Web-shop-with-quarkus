package org.controller;

import org.dto.OrderItemDto;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.service.OrderItemService;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
@Path("/order-items")
@Tag(name = "оrderItem", description = "All the оrderItem methods")
public class OrderItemResource {
    @Inject
    OrderItemService itemService;
    @GET
    @Path("/order/{id}")
    public List<OrderItemDto> findByOrderId(@PathParam("id") Long id) {
        return this.itemService.findByOrderId(id);
    }
    @GET
    @Path("/{id}")
    public OrderItemDto findById(@PathParam("id") Long id) {
        return this.itemService.findById(id);
    }
    @POST
    public OrderItemDto create(OrderItemDto orderItemDto) {
        return this.itemService.create(orderItemDto);
    }
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        this.itemService.delete(id);
    }
}