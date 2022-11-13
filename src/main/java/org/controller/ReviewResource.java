package org.controller;

import org.dto.ReviewDto;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.service.ReviewService;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("/reviews")
@Tag(name = "review", description = "All the review methods")
public class ReviewResource {
    @Inject
    ReviewService reviewService;

    @GET
    @Path("/product/{id}")
    public List<ReviewDto> findAllByProduct(@PathParam("id") Long id) {
        return this.reviewService.findReviewsByProductId(id);
    }

    @GET
    @Path("/{id}")
    public ReviewDto findById(@PathParam("id") Long id) {
        return this.reviewService.findById(id);
    }

    @POST
    @Path("/product/{id}")
    public ReviewDto create(ReviewDto reviewDto, @PathParam("id") Long id) {
        return this.reviewService.create(reviewDto, id);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        this.reviewService.delete(id);
    }
}