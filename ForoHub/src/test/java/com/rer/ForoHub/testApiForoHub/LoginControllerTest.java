package com.rer.ForoHub.testApiForoHub;

import com.rer.ForoHub.Controllers.LoginController;
import com.rer.ForoHub.Models.Dto.LoginDTO;
import com.rer.ForoHub.Models.Dto.UsuarioDTO;
import com.rer.ForoHub.Models.Model.Usuario;
import com.rer.ForoHub.Repository.UsuarioRepository;
import com.rer.ForoHub.Security.Authenticar;
import com.rer.ForoHub.Security.JwtUtil;
import com.rer.ForoHub.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Mock
    UsuarioService usuarioServ;
    @Mock
    Authenticar authenticar;
    @Mock
    JwtUtil jwtUtil;
    @Mock
    UsuarioRepository usuarioRepo;
    @Mock
    AuthenticationManager authenticationManager;
    @InjectMocks
    LoginController loginController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrarUsuario_Success() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO("usuario1", "password123",
                              "usuario1@mail.com", RolDTO.USER);

        when(usuarioServ.existeAdministrador()).thenReturn(false);
        when(usuarioServ.crearUsuario(any(Usuario.class))).thenReturn(new Usuario());

        mockMvc.perform(post("/registrarUsuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombreUsuarioDto\":\"usuario1\",\"contraseñaDto\"" +
                                ":\"password123\",\"correoElectronicoDto\":\"usuario1@mail.com\",\"rolDto\":\"USER\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testRegistrarUsuario_AdminAlreadyExists() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO("admin", "admin123",
                               "admin@mail.com", RolDTO.ADMIN);

        when(usuarioServ.existeAdministrador()).thenReturn(true);

        mockMvc.perform(post("/registrarUsuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombreUsuarioDto\":\"admin\",\"contraseñaDto\":\"admin123\"," +
                                 "\"correoElectronicoDto\":\"admin@mail.com\",\"rolDto\":\"ADMIN\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("El usuario con rol ADMIN ya existe."));
    }

    @Test
    void testAuthenticate_Success() throws Exception {
        LoginDTO loginDTO = new LoginDTO("usuario1", "password123");
        String token = "valid.jwt.token";

        when(authenticar.loginValidacion(anyString(), anyString())).thenReturn(token);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"usuario1\",\"password\":\"password123\"}"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void testAuthenticate_Failure() throws Exception {
        when(authenticar.loginValidacion(anyString(), anyString()))
                  .thenThrow(new RuntimeException("Autenticación fallida"));

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"usuario1\",\"password\":\"wrongpassword\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Error del servidor"));
    }
}

