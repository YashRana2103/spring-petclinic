package com.petclinic.system;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.MutableConfiguration;

@Configuration(proxyBeanMethods = false)
@EnableCaching
public class CacheConfiguration {

    /**
     * <p>
     *     <b>Why does spring need {@link JCacheManagerCustomizer}</b>
     *     <p> When spring starts it creates the Bean for {@link javax.cache.CacheManager},
     *     <p> but it's the empty one. Spring doesn't know what to do with this customizer, whether to create cache, close cache, get cache, what..
     *     <p> So for that purpose user defines the custom behavior for the {@link javax.cache.CacheManager} in this customizer.
     *     <p> and then this behavior is used by that empty cache manager.
     *     <pre><code>
     * // INTERNAL SPRING BOOT CODE (Simplified)
     * {@code @Bean}
     * public CacheManager cacheManager(List<JCacheManagerCustomizer> userCustomizers) {
     *
     *     // 1. Spring creates the default empty Manager
     *     CacheManager manager = new CachingProvider().getCacheManager();
     *
     *     // 2. THE HOOK: Spring loops through any customizers YOU provided
     *     for (JCacheManagerCustomizer customizer : userCustomizers) {
     *         // "Hey user, do you want to add anything to this manager?"
     *         customizer.customize(manager);
     *     }
     *
     *     // 3. Spring returns the finished manager
     *     return manager;
     * }
     *     </code></pre>
     * </p>
     * <p>
     * <b>Flow explanation for the code:</b>
     * <p> It is like implementing the interface {@link JCacheManagerCustomizer},
     * <p>
     * means its method has to be overridden in the child class and the code for the method will be:
     * <pre><code>
     * {@code @Override}
     * void customize(CacheManager cacheManager) {
     *     cacheManager.createCache("vets", cacheConfiguration());
     * }
     * </code></pre>
     * that is according to:
     * <pre><code>
     * cm -> cm.createCache("vets", cacheConfiguration());
     * </code></pre>
     * and then it creates the Object of that implemented class and returns it.
     * <p> Specifically, the code inside overridden {@code customize()} method:
     * <ul>
     * <li> allocates new memory and called it as "vets" cache container
     * <li> it creates that "vets" container with defined configurations
     * </ul>
     * @return implementation of the {@link JCacheManagerCustomizer}
     */
    @Bean
    public JCacheManagerCustomizer petclinicCacheConfigurationCustomizer() {
        return cm -> cm.createCache("vets", cacheConfiguration());
    }

    /**
     * This is the custom configuration for the cache.
     * <ul>
     *     <li> creates the new instance of {@link MutableConfiguration}, for example {@code MutableConfiguration obj = new MutableConfiguration();}
     *     <li> then it calls {@code setStatisticsEnabled(Boolean enabled)} for that object:
     *     <li> {@code obj.setStatisticsEnabled(true)}
     *     <li> which sets {@code this.isStatisticsEnabled = enabled;} for object obj, and the method itself returns the current object {@code return this;}
     * </ul>
     * @return obj object that is instance of {@link MutableConfiguration}
     */
    private javax.cache.configuration.Configuration<Object, Object> cacheConfiguration() {
        return new MutableConfiguration<>().setStatisticsEnabled(true);
    }
}
