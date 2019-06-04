package com.itp.ciecyt.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.itp.ciecyt.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.itp.ciecyt.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.itp.ciecyt.domain.User.class.getName());
            createCache(cm, com.itp.ciecyt.domain.Authority.class.getName());
            createCache(cm, com.itp.ciecyt.domain.User.class.getName() + ".authorities");
            createCache(cm, com.itp.ciecyt.domain.Proyecto.class.getName());
            createCache(cm, com.itp.ciecyt.domain.Proyecto.class.getName() + ".entidadFinanciadoras");
            createCache(cm, com.itp.ciecyt.domain.Proyecto.class.getName() + ".elementosProyectos");
            createCache(cm, com.itp.ciecyt.domain.Proyecto.class.getName() + ".productoProyectos");
            createCache(cm, com.itp.ciecyt.domain.Proyecto.class.getName() + ".presupuestoValors");
            createCache(cm, com.itp.ciecyt.domain.Proyecto.class.getName() + ".impactosEsperados");
            createCache(cm, com.itp.ciecyt.domain.Proyecto.class.getName() + ".cronogramas");
            createCache(cm, com.itp.ciecyt.domain.Proyecto.class.getName() + ".resultadosEsperados");
            createCache(cm, com.itp.ciecyt.domain.Proyecto.class.getName() + ".integrantesProyectos");
            createCache(cm, com.itp.ciecyt.domain.LineaDeInvestigacion.class.getName());
            createCache(cm, com.itp.ciecyt.domain.LineaDeInvestigacion.class.getName() + ".proyectos");
            createCache(cm, com.itp.ciecyt.domain.GrupoSemillero.class.getName());
            createCache(cm, com.itp.ciecyt.domain.GrupoSemillero.class.getName() + ".proyectos");
            createCache(cm, com.itp.ciecyt.domain.Facultad.class.getName());
            createCache(cm, com.itp.ciecyt.domain.Facultad.class.getName() + ".proyectos");
            createCache(cm, com.itp.ciecyt.domain.ModalidadDeGrado.class.getName());
            createCache(cm, com.itp.ciecyt.domain.ModalidadDeGrado.class.getName() + ".proyectos");
            createCache(cm, com.itp.ciecyt.domain.Categorizacion.class.getName());
            createCache(cm, com.itp.ciecyt.domain.Empresas.class.getName());
            createCache(cm, com.itp.ciecyt.domain.EntidadFinanciadora.class.getName());
            createCache(cm, com.itp.ciecyt.domain.EntidadFinanciadora.class.getName() + ".empresas");
            createCache(cm, com.itp.ciecyt.domain.Elementos.class.getName());
            createCache(cm, com.itp.ciecyt.domain.Elementos.class.getName() + ".elementosProyectos");
            createCache(cm, com.itp.ciecyt.domain.Elementos.class.getName() + ".preguntas");
            createCache(cm, com.itp.ciecyt.domain.ElementosProyecto.class.getName());
            createCache(cm, com.itp.ciecyt.domain.Producto.class.getName());
            createCache(cm, com.itp.ciecyt.domain.Producto.class.getName() + ".productoProyectos");
            createCache(cm, com.itp.ciecyt.domain.ProductoProyecto.class.getName());
            createCache(cm, com.itp.ciecyt.domain.Rubros.class.getName());
            createCache(cm, com.itp.ciecyt.domain.Rubros.class.getName() + ".presupuestoValors");
            createCache(cm, com.itp.ciecyt.domain.PresupuestoValor.class.getName());
            createCache(cm, com.itp.ciecyt.domain.ImpactosEsperados.class.getName());
            createCache(cm, com.itp.ciecyt.domain.Cronograma.class.getName());
            createCache(cm, com.itp.ciecyt.domain.ResultadosEsperados.class.getName());
            createCache(cm, com.itp.ciecyt.domain.Preguntas.class.getName());
            createCache(cm, com.itp.ciecyt.domain.Preguntas.class.getName() + ".preguntaRespuestas");
            createCache(cm, com.itp.ciecyt.domain.FaseTrabajo.class.getName());
            createCache(cm, com.itp.ciecyt.domain.FaseTrabajo.class.getName() + ".faseTrabajos");
            createCache(cm, com.itp.ciecyt.domain.PreguntaRespuesta.class.getName());
            createCache(cm, com.itp.ciecyt.domain.TipoPregunta.class.getName());
            createCache(cm, com.itp.ciecyt.domain.TipoPregunta.class.getName() + ".preguntaRespuestas");
            createCache(cm, com.itp.ciecyt.domain.IntegrantesProyecto.class.getName());
            createCache(cm, com.itp.ciecyt.domain.IntegrantesProyecto.class.getName() + ".preguntaRespuestas");
            createCache(cm, com.itp.ciecyt.domain.IntegrantesProyecto.class.getName() + ".solicituds");
            createCache(cm, com.itp.ciecyt.domain.FichaTecnica.class.getName());
            createCache(cm, com.itp.ciecyt.domain.Solicitud.class.getName());
            createCache(cm, com.itp.ciecyt.domain.RolesUsuarioProyecto.class.getName());
            createCache(cm, com.itp.ciecyt.domain.RolesUsuarioProyecto.class.getName() + ".integrantesProyectos");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
