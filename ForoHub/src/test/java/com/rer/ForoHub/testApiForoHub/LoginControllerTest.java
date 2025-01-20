package com.rer.ForoHub.testApiForoHub;

import com.rer.ForoHub.Controllers.LoginController;
import com.rer.ForoHub.Models.Dto.LoginDto;
import com.rer.ForoHub.Models.Dto.UsuarioDto;
import com.rer.ForoHub.Models.Enum.Roles;
import com.rer.ForoHub.Models.Model.Usuario;
import com.rer.ForoHub.Repository.UsuarioRepository;
import com.rer.ForoHub.Security.Authenticar;
import com.rer.ForoHub.Security.JwtAuthenticationFilter;
import com.rer.ForoHub.Security.JwtUtil;
import com.rer.ForoHub.Security.SecurityConfiguration;
import com.rer.ForoHub.Services.UserDetailsServiceUsuario;
import com.rer.ForoHub.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfiguration.class)
public class LoginControllerTest {

    @MockBean
    JwtAuthenticationFilter JwtAuthenticationFilter;
    @MockBean
    UserDetailsServiceUsuario userDetailsServiceUsuario;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    Authenticar authenticar;
    @MockBean
    JwtUtil jwtUtil;
    @MockBean
    UsuarioService usuarioServ;
    @MockBean
    UsuarioRepository usuarioRepo;
    @MockBean
    AuthenticationManager authenticationManager;
    @InjectMocks
    LoginController loginController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrarUsuario_AdminAlreadyExists() throws Exception {

        UsuarioDto usuarioDTO = new UsuarioDto("rogerA10781078", "rogerAdmin",
                "roger@mail.com", Roles.ADMIN);

        Mockito.when(usuarioServ.existeAdministrador()).thenReturn(true);

        mockMvc.perform(post("/registrarUsuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombreUsuarioDto\":\"rogerAdmin\",\"contraseñaDto\":\"rogerA10781078\"," +
                                "\"correoElectronicoDto\":\"roger@mail.com\",\"rolDto\":\"ADMIN\"}")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJBQ1RVQUxJWkFSX1JFU1BVRVNUQVMiLCJBQ1RVQUxJWkFSX1RPUElDTyIsIkFDVFVBTElaQVJfVVNVQVJJTyIsIkNSRUFSX1JFU1BVRVNUQVMiLCJDUkVBUl9UT1BJQ08iLCJDUkVBUl9VU1VBUklPIiwiRUxJTUlOQVJfUkVTUFVFU1RBUyIsIkVMSU1JTkFSX1RPUElDTyIsIkVMSU1JTkFSX1VTVUFSSU8iLCJMRUVSX1JFU1BVRVNUQVMiLCJMRUVSX1RPUElDTyIsIkxFRVJfVVNVQVJJTyJdLCJ1c2VybmFtZSI6InJvZ2VyQWRtaW4iLCJzdWIiOiJyb2dlckFkbWluIiwiaWF0IjoxNzM3MTcyNjE5LCJleHAiOjE3MzcxNzk4MTl9.7MFwewUrLk5vqcfZEuzfAWV7VkPiTREakZPbTXXrEuc"))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    void testRegistrarUsuario_Success() throws Exception {

        UsuarioDto usuarioDTO = new UsuarioDto("rogerAdmin", "rogerA10781078",
                "usuario1@mail.com", Roles.ADMIN);

        Mockito.when(usuarioServ.existeAdministrador()).thenReturn(false);
       // Mockito.when(usuarioServ.crearUsuario(any(Usuario.class)))
         //       .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        mockMvc.perform(post("/registrarUsuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombreUsuarioDto\":\"rogerAdmin\",\"contraseñaDto\":\"rogerA10781078\"," +
                                "\"correoElectronicoDto\":\"roger@mail.com\",\"rolDto\":\"ADMIN\"}"))
                        .andExpect(status().isOk())
                        .andDo(print());
    }
    @Test
    void testAuthenticate_Success() throws Exception {
        LoginDto loginDTO = new LoginDto("rogerAdmin", "rogerA10781078");
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJBQ1RVQUxJWkFSX1JFU1BVRVNUQVMiLCJBQ1RVQUxJWkFSX1RPUElDTyIsIkFDVFVBTElaQVJfVVNVQVJJTyIsIkNSRUFSX1JFU1BVRVNUQVMiLCJDUkVBUl9UT1BJQ08iLCJDUkVBUl9VU1VBUklPIiwiRUxJTUlOQVJfUkVTUFVFU1RBUyIsIkVMSU1JTkFSX1RPUElDTyIsIkVMSU1JTkFSX1VTVUFSSU8iLCJMRUVSX1JFU1BVRVNUQVMiLCJMRUVSX1RPUElDTyIsIkxFRVJfVVNVQVJJTyJdLCJ1c2VybmFtZSI6InJvZ2VyQWRtaW4iLCJzdWIiOiJyb2dlckFkbWluIiwiaWF0IjoxNzM3MTcyNjE5LCJleHAiOjE3MzcxNzk4MTl9.7MFwewUrLk5vqcfZEuzfAWV7VkPiTREakZPbTXXrEuc";

        Mockito.when(authenticar.loginValidacion(anyString(), anyString())).thenReturn(token);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"rogerAdmin\",\"password\":\"rogerA10781078\"}"))
                        .andExpect(status().isOk())
                        .andDo(print());
    }
    @Test
    void testAuthenticate_Failure() throws Exception {
        Mockito.when(authenticar.loginValidacion(anyString(), anyString()))
                  .thenThrow(new RuntimeException("Autenticación fallida"));

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"rogerAdmin\",\"password\":\"roger10781078\"}"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}

