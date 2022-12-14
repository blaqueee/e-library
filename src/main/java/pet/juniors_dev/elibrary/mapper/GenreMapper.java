package pet.juniors_dev.elibrary.mapper;

import org.springframework.stereotype.Component;
import pet.juniors_dev.elibrary.dto.GenreDto;
import pet.juniors_dev.elibrary.entity.Genre;

@Component
public class GenreMapper {
    public Genre toEntity(GenreDto genreDto){
        return Genre.builder()
                .id(genreDto.getId())
                .name(genreDto.getName())
                .build();
    }

    public GenreDto toDto(Genre genre){
        return GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}
