package ru.job4j.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "urls")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "full_url")
    private String fullUrl;

    @Column(name = "short_url")
    private String shortUrl;

    private Integer total;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;

    public Url(String fullUrl, String shortUrl, Site site) {
        this.fullUrl = fullUrl;
        this.shortUrl = shortUrl;
        this.site = site;
        this.total = 0;
    }
}
