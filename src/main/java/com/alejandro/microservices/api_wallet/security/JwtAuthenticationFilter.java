package com.alejandro.microservices.api_wallet.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.alejandro.microservices.api_wallet.security.TokenBlacklistService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 🛡️ JWT Authentication Filter - Filtro de Seguridad Principal
 * 
 * Este filtro intercepta todas las peticiones HTTP y valida los tokens JWT
 * para establecer la autenticación en el contexto de Spring Security.
 * 
 * 🎯 Responsabilidades:
 * - Interceptar headers de autorización
 * - Validar tokens JWT en cada request
 * - Verificar blacklist de tokens
 * - Establecer contexto de autenticación
 * - Manejo seguro de errores
 * 
 * 🔄 Flujo de Autenticación:
 * 1. Extraer token del header "Authorization: Bearer <token>"
 * 2. Validar firma y expiración del token
 * 3. Verificar que no esté en blacklist
 * 4. Cargar detalles del usuario
 * 5. Establecer autenticación en SecurityContext
 * 
 * 🛡️ Medidas de Seguridad:
 * - Validación triple: firma, expiración, blacklist
 * - Manejo de excepciones sin exponer información sensible
 * - Continuación de la cadena de filtros en caso de error
 * - Logging de errores para auditoría
 * 
 * 📊 Performance:
 * - Filtro ejecutado una vez por request (OncePerRequestFilter)
 * - Validación rápida con early returns
 * - No bloquea requests sin token
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // 🔧 Dependencias inyectadas por constructor (mejor práctica que @Autowired)
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final TokenBlacklistService tokenBlacklistService;

    /**
     * 🔧 Constructor con inyección de dependencias
     * 
     * Usa constructor injection en lugar de field injection para:
     * - Mejor testabilidad
     * - Dependencias explícitas
     * - Inmutabilidad de dependencias
     * 
     * @param jwtTokenProvider Servicio para validación de JWT
     * @param userDetailsService Servicio para cargar detalles de usuario
     * @param tokenBlacklistService Servicio para verificar blacklist
     */
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,
                                   UserDetailsService userDetailsService,
                                   TokenBlacklistService tokenBlacklistService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    /**
     * 🔍 Procesa cada request HTTP para autenticación JWT
     * 
     * Este método se ejecuta para cada request y implementa la lógica
     * de autenticación basada en tokens JWT.
     * 
     * 🔄 Flujo de Procesamiento:
     * 1. Extraer token del header Authorization
     * 2. Validar token (firma, expiración, blacklist)
     * 3. Cargar detalles del usuario
     * 4. Establecer autenticación en SecurityContext
     * 5. Continuar con la cadena de filtros
     * 
     * 🛡️ Validaciones de Seguridad:
     * - Formato correcto del header Authorization
     * - Token válido y no expirado
     * - Token no en blacklist
     * - Usuario existe en el sistema
     * 
     * ⚡ Optimizaciones:
     * - Early return si no hay token
     * - Validación rápida antes de cargar usuario
     * - Manejo de errores sin bloquear request
     * 
     * @param request Request HTTP entrante
     * @param response Response HTTP saliente
     * @param filterChain Cadena de filtros de Spring Security
     * @throws ServletException Error en el procesamiento del servlet
     * @throws IOException Error de entrada/salida
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 🔍 Extraer token del header Authorization
        String authHeader = request.getHeader("Authorization");

        // ⚡ Early return si no hay header de autorización
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remover "Bearer " prefix

            // 🛡️ Validación triple de seguridad
            if (jwtTokenProvider.validarToken(token) && 
                !jwtTokenProvider.tokenExpirado(token) && 
                !tokenBlacklistService.isTokenBlacklisted(token)) {
                
                try {
                    // 🔍 Extraer username del token validado
                    String username = jwtTokenProvider.obtenerUsernameDelToken(token);
                    
                    // 👤 Cargar detalles completos del usuario
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // 🔐 Crear token de autenticación con autoridades
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );

                    // 🎯 Establecer autenticación en el contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    
                } catch (Exception e) {
                    // 🔒 Log de seguridad sin exponer información sensible
                    // En producción, usar logger apropiado con nivel WARN
                    logger.warn("Error procesando token JWT: " + e.getMessage());
                    
                    // ⚠️ No lanzar excepción para no bloquear el request
                    // El usuario será tratado como no autenticado
                }
            }
        }

        // 🔄 Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
