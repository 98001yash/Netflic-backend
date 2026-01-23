package com.company.BingeBox_backend_application.user_service.dtos;


import lombok.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponseDto {

    private Long id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String trailerUrl;
    private String contentUrl;
    private int releaseYear;
    private int duration;

    // Mutable collections to avoid NPEs with Jackson/Feign
    @Builder.Default
    private Set<GenreDto> genres = new HashSet<>();

    @Builder.Default
    private List<String> cast = new ArrayList<>();

    private String maturityRating;
    private boolean featured;

    @Builder.Default
    private Set<ActorDto> actors = new HashSet<>();

    @Builder.Default
    private Set<DirectorDto> directors = new HashSet<>();

    @Builder.Default
    private Set<ProducerDto> producers = new HashSet<>();
    private CategoryDto category;
}
