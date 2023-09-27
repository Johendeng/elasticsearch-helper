package org.pippi.elasticsearch.test.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.common.geo.GeoPoint;
import org.pippi.elasticsearch.helper.model.annotations.meta.EsField;
import org.pippi.elasticsearch.helper.model.annotations.meta.EsIndex;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;

import java.util.Date;
import java.util.List;

/**
 * @author JohenDeng
 * @date 2023/9/26
 **/
@Setter
@Getter
@EsIndex("movies")
public class Movies extends EsEntity {

    private Integer id;

    private String originalLanguage;

    private Keywords keywords;

    private String imdbId;

    private Log log;

    private List<String> genresList;

    private Boolean video;

    private String title;

    private Crew crew;

    private Cast cast;

    private Double revenue;

    private List<Genres> genres;

    private Ratings ratings;

    private Float popularity;

    @EsField(name = "@version")
    private String version;

    private List<ProductionCountries> productionCountries;

    private Event event;

    private Long voteCount;

    private Double budget;

    private String overview;

    private String originalTitle;

    private Float runtime;

    private String posterPath;

    private String tags;

    private String keywordsList;

    private List<SpokenLanguages> spokenLanguages;

    @EsField(name = "@timestamp")
    private Date timestamp;

    private List<ProductionCompanies> productionCompanies;

    @EsField(format = "yyyy-MM-dd")
    private Date releaseDate;

    private Float voteAverage;

    private String tagline;

    private List<String> productionCountriesLocationList;

    private List<String> productionCountriesNameList;

    private Boolean adult;

    private String homepage;

    private String status;

    @Setter
    @Getter
    public static class Keywords {

        private String name;

        private Integer id;
    }


    @Setter
    @Getter
    public static class Log {

        private File file;

        @Setter
        @Getter
        public static class File {

            private String path;
        }

    }

    @Setter
    @Getter
    public static class Crew {

        private Integer gender;

        private String creditId;

        private String name;

        private String profilePath;

        private Integer id;

        private String department;

        private String job;
    }

    @Setter
    @Getter
    public static class Cast {

        private Integer castId;

        private String character;

        private Integer gender;

        private String creditId;

        private String name;

        private String profilePath;

        private Integer id;

        private Integer order;
    }

    @Getter
    @Setter
    public static class Genres {

        private String name;

        private Integer id;
    }

    @Getter
    @Setter
    public static class Ratings {

        private Float rating;

        private Integer movieId;

        private Integer userId;

        private Date timestamp;
    }

    @Getter
    @Setter
    public static class ProductionCountries {

        @EsField(name = "iso_3166_1")
        private String iso_3166_1_s;

        private String name;

        private String location;
    }

    @Setter
    @Getter
    public static class SpokenLanguages {

        private String name;

        @EsField(name = "iso_639_1")
        private String iso_639_1_s;
    }

    @Setter
    @Getter
    public static class ProductionCompanies {

        private String name;

        private Integer id;
    }

    @Setter
    @Getter
    private static class Event {

        private String original;
    }
}
