package co.edu.itp.ciecyt.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, co.edu.itp.ciecyt.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, co.edu.itp.ciecyt.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, co.edu.itp.ciecyt.domain.User.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Authority.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.User.class.getName() + ".authorities");
            createCache(cm, co.edu.itp.ciecyt.domain.Acuerdo.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.AdjuntoProyectoFase.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.AdjuntoRetroalimentacion.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Categorizacion.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.CicloPropedeutico.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Cronograma.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.CronogramaCiecyt.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.CronogramaCiecytFases.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Elemento.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.ElementoProyecto.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Entidad.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.EntidadFinanciadora.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Facultad.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Facultad.class.getName() + ".facultadProyectos");
            createCache(cm, co.edu.itp.ciecyt.domain.Fases.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.FichaTecnica.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Formato.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.GrupoSemillero.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.ImpactosEsperados.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.InformacionPasantia.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.IntegranteProyecto.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.InvestigacionTipo.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.LineaInvestigacion.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Menu.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Modalidad.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Modalidad.class.getName() + ".modalidadCicloPropedeuticos");
            createCache(cm, co.edu.itp.ciecyt.domain.Pregunta.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.PresupuestoValor.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Producto.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.ProductoProyecto.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Proyecto.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.ProyectoFase.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.ProyectoRespuestas.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.ResultadosEsperados.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Retroalimentacion.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.RolesModalidad.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.RolMenu.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Rubro.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Solicitud.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.TipoPregunta.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Usuario.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Programa.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.UserInfo.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.PreguntaModalidad.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.PreguntaAuthority.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.ElementoModalidad.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.AdjuntoRetroalimentacion.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Municipio.class.getName());
            createCache(cm, co.edu.itp.ciecyt.domain.Departamento.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
