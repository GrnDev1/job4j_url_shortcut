package ru.job4j.mappers;

import org.mapstruct.Mapper;
import ru.job4j.dtos.UrlDto;
import ru.job4j.models.Url;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UrlMapper {
    default UrlDto getUrlDtoFromEntity(Url url) {
        return new UrlDto(url.getFullUrl(), url.getTotal());
    }

    default List<UrlDto> getListFromUrl(List<Url> urls) {
        List<UrlDto> list = new ArrayList<>();
        for (Url url : urls) {
            list.add(getUrlDtoFromEntity(url));
        }
        return list;
    }
}
