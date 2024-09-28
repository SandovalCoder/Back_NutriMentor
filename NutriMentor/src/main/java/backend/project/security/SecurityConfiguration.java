package backend.project.security;

import backend.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfiguration {

    // Rutas públicas que no requieren autenticación
    private static final String[] AUTH_WHITELIST = {
            "/api/users/login",
            "/api/users/register",
            "/api/clients/**"
    };

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Validar el token JWT antes de cualquier solicitud
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // Permitir solicitudes desde otros servidores (CORS) y deshabilitar CSRF (ya que usamos JWT)
        http.cors(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);

        // Permisos para cada rol y ruta
        http.authorizeHttpRequests(auth -> auth
                // Rutas abiertas (sin autenticación)
                .requestMatchers(AUTH_WHITELIST).permitAll()

                // Rutas para `ADMIN` - CRUD completo
                .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ADMIN")

                // Rutas para `USER` - Crear y Modificar propias entidades
                .requestMatchers(HttpMethod.POST, "/api/buys/**").hasAuthority("USER") // Crear nuevas compras
                .requestMatchers(HttpMethod.PUT, "/api/buys/**").hasAuthority("USER") // Modificar detalles de compras propias

                .requestMatchers(HttpMethod.POST, "/api/reviews/**").hasAuthority("USER") // Crear reseñas
                .requestMatchers(HttpMethod.PUT, "/api/reviews/**").hasAuthority("USER") // Modificar reseñas propias

                .requestMatchers(HttpMethod.POST, "/api/health-goals/**").hasAuthority("USER") // Crear objetivo de salud
                .requestMatchers(HttpMethod.PUT, "/api/health-goals/**").hasAuthority("USER") // Modificar objetivos de salud propios

                // Permisos de `HEALTH_PROFESSIONAL` - Gestionar seguimiento y recomendaciones
                .requestMatchers(HttpMethod.POST, "/api/trackings/**").hasAuthority("HEALTH_PROFESSIONAL") // Crear seguimiento
                .requestMatchers(HttpMethod.PUT, "/api/trackings/**").hasAuthority("HEALTH_PROFESSIONAL") // Modificar seguimiento
                .requestMatchers(HttpMethod.GET, "/api/recommendations/**").hasAuthority("HEALTH_PROFESSIONAL") // Ver recomendaciones
                .requestMatchers(HttpMethod.POST, "/api/recommendations/**").hasAuthority("HEALTH_PROFESSIONAL") // Crear recomendaciones

                // Permisos de visualización para todos (ADMIN, USER, HEALTH_PROFESSIONAL)
                .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyAuthority("USER", "ADMIN", "HEALTH_PROFESSIONAL") // Ver usuarios
                .requestMatchers(HttpMethod.GET, "/api/products/**").hasAnyAuthority("USER", "ADMIN", "HEALTH_PROFESSIONAL") // Ver productos
                .requestMatchers(HttpMethod.GET, "/api/categories/**").hasAnyAuthority("USER", "ADMIN", "HEALTH_PROFESSIONAL") // Ver categorías
                .requestMatchers(HttpMethod.GET, "/api/buys/**").hasAnyAuthority("USER", "ADMIN") // Ver compras
                .requestMatchers(HttpMethod.GET, "/api/health-goals/**").hasAnyAuthority("USER", "ADMIN", "HEALTH_PROFESSIONAL") // Ver objetivos de salud
                .requestMatchers(HttpMethod.GET, "/api/reviews/**").hasAnyAuthority("USER", "ADMIN") // Ver reseñas
                .requestMatchers(HttpMethod.GET, "/api/questions/**").hasAnyAuthority("USER", "ADMIN") // Ver preguntas
                .requestMatchers(HttpMethod.GET, "/api/answers/**").hasAnyAuthority("USER", "ADMIN") // Ver respuestas

                // Autenticación requerida para cualquier otra ruta
                .anyRequest().authenticated()
        );

        // Manejo de sesiones como `stateless` ya que se utiliza JWT para autenticación
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}

