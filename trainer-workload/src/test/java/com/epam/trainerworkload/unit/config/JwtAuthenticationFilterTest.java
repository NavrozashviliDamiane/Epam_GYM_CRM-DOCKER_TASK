package com.epam.trainerworkload.unit.config;//package com.epam.trainerworkload.unit.config;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.io.IOException;
//
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class JwtAuthenticationFilterTest {
//
//    @Mock
//    private JwtService jwtService;
//
//    @Mock
//    private HttpServletRequest request;
//
//    @Mock
//    private HttpServletResponse response;
//
//    @Mock
//    private FilterChain filterChain;
//
//    @InjectMocks
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    private final String token = "mockToken";
//    private final String username = "mockUser";
//
//    @BeforeEach
//    void setUp() {
//        SecurityContextHolder.clearContext();
//    }
//
//    @Test
//    void testDoFilterInternal_NoAuthHeader() throws ServletException, IOException {
//        when(request.getHeader("Authorization")).thenReturn(null);
//
//        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
//
//        verify(filterChain).doFilter(request, response);
//        verify(jwtService, never()).validateToken(anyString());
//    }
//
//    @Test
//    void testDoFilterInternal_InvalidToken() throws ServletException, IOException {
//        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
//        when(jwtService.validateToken(token)).thenReturn(false);
//
//        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
//
//        verify(filterChain).doFilter(request, response);
//        verify(jwtService).validateToken(token);
//        verify(jwtService, never()).extractUsername(anyString());
//    }
//
//    @Test
//    void testDoFilterInternal_ValidToken() throws ServletException, IOException {
//        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
//        when(jwtService.validateToken(token)).thenReturn(true);
//        when(jwtService.extractUsername(token)).thenReturn(username);
//
//        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
//
//        verify(jwtService).validateToken(token);
//        verify(jwtService).extractUsername(token);
//        verify(filterChain).doFilter(request, response);
//    }
//
//    @Test
//    void testDoFilterInternal_ValidToken_SetsAuthentication() throws ServletException, IOException {
//        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
//        when(jwtService.validateToken(token)).thenReturn(true);
//        when(jwtService.extractUsername(token)).thenReturn(username);
//
//        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
//
//        verify(jwtService).validateToken(token);
//        verify(jwtService).extractUsername(token);
//        verify(filterChain).doFilter(request, response);
//
//        UsernamePasswordAuthenticationToken authenticationToken =
//                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//        assert authenticationToken != null;
//        assert authenticationToken.getName().equals(username);
//    }
//}
