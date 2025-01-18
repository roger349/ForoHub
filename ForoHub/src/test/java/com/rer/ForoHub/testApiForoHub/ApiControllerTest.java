package com.rer.ForoHub.testApiForoHub;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.rer.ForoHub.Controllers.ApiController;
import com.rer.ForoHub.Models.Dto.TopicoDto;
import com.rer.ForoHub.Models.Enum.Categorias;
import com.rer.ForoHub.Models.Model.Respuestas;
import com.rer.ForoHub.Models.Model.Topico;
import com.rer.ForoHub.Models.Model.Usuario;
import com.rer.ForoHub.Repository.RespuestasRepository;
import com.rer.ForoHub.Repository.TopicoRepository;
import com.rer.ForoHub.Repository.UsuarioRepository;
import com.rer.ForoHub.Security.JwtUtil;
import com.rer.ForoHub.Security.SecurityConfiguration;
import com.rer.ForoHub.Services.RespuestasService;
import com.rer.ForoHub.Services.TopicoService;
import com.rer.ForoHub.Services.UserDetailsServiceUsuario;
import com.rer.ForoHub.Services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;
import org.mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfiguration.class)
public class ApiControllerTest {

    @MockBean
    UserDetailsServiceUsuario userDetailsServiceUsuario;
    @MockBean
    JwtUtil jwtUtil;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UsuarioService usuarioServ;
    @MockBean
    TopicoService topicoServ;
    @MockBean
    RespuestasService respuestasServ;
    @MockBean
    UsuarioRepository usuarioRepo;
    @MockBean
    TopicoRepository topicoRepo;
    @MockBean
    RespuestasRepository respuestasRepo;
    @InjectMocks
    ApiController apiController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(authorities = "LEER_USUARIO")
    void testListarUsuarios() throws Exception {
        Mockito.when(usuarioServ.getAllUsuarios()).thenReturn(List.of(new Usuario(), new Usuario()));

        mockMvc.perform(get("/usuarios/listarUsuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
    @Test
    @WithMockUser(authorities = "LEER_USUARIO")
    void testObtenerUsuarioPorId() throws Exception {
        Usuario usuario = new Usuario();
        Mockito.when(usuarioServ.getUsuarioById(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/usuarios/obtenerUsuarioId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }
    @Test
    @WithMockUser(authorities = "ACTUALIZAR_USUARIO")
    void testActualizarUsuario() throws Exception {
        Usuario usuarioDto = new Usuario();
        usuarioDto.setNombre_usuario("updatedUser");
        Mockito.when(usuarioServ.getUsuarioById(1L)).thenReturn(Optional.of(usuarioDto));
        Mockito.when(usuarioServ.saveUsuario(any(Usuario.class))).thenReturn(usuarioDto);

                 mockMvc.perform(put("/usuarios/actualizarUsuarioId/1")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJBQ1RVQUxJWkFSX1JFU1BVRVNUQVMiLCJBQ1RVQUxJWkFSX1RPUElDTyIsIkFDVFVBTElaQVJfVVNVQVJJTyIsIkNSRUFSX1JFU1BVRVNUQVMiLCJDUkVBUl9UT1BJQ08iLCJDUkVBUl9VU1VBUklPIiwiRUxJTUlOQVJfUkVTUFVFU1RBUyIsIkVMSU1JTkFSX1RPUElDTyIsIkVMSU1JTkFSX1VTVUFSSU8iLCJMRUVSX1JFU1BVRVNUQVMiLCJMRUVSX1RPUElDTyIsIkxFRVJfVVNVQVJJTyJdLCJ1c2VybmFtZSI6InJvZ2VyQWRtaW4iLCJzdWIiOiJyb2dlckFkbWluIiwiaWF0IjoxNzM3MTM4MjU0LCJleHAiOjE3MzcxNDU0NTR9.dPWF4YMTjw-zAfl3ELfL0JMJ7tMIyzXYNREXy6-Fug8")
                        .header("X-Auth-Token", "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJBQ1RVQUxJWkFSX1JFU1BVRVNUQVMiLCJBQ1RVQUxJWkFSX1RPUElDTyIsIkFDVFVBTElaQVJfVVNVQVJJTyIsIkNSRUFSX1JFU1BVRVNUQVMiLCJDUkVBUl9UT1BJQ08iLCJDUkVBUl9VU1VBUklPIiwiRUxJTUlOQVJfUkVTUFVFU1RBUyIsIkVMSU1JTkFSX1RPUElDTyIsIkVMSU1JTkFSX1VTVUFSSU8iLCJMRUVSX1JFU1BVRVNUQVMiLCJMRUVSX1RPUElDTyIsIkxFRVJfVVNVQVJJTyJdLCJ1c2VybmFtZSI6InJvZ2VyQWRtaW4iLCJzdWIiOiJyb2dlckFkbWluIiwiaWF0IjoxNzM3MTM4MjU0LCJleHAiOjE3MzcxNDU0NTR9.dPWF4YMTjw-zAfl3ELfL0JMJ7tMIyzXYNREXy6-Fug8"))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(authorities = "ELIMINAR_USUARIO")
    void testEliminarUsuario() throws Exception {
        Mockito.when(usuarioServ.getUsuarioById(1L)).thenReturn(Optional.of(new Usuario()));

        mockMvc.perform(delete("/usuarios/eliminarUsuarioId/1")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJBQ1RVQUxJWkFSX1JFU1BVRVNUQVMiLCJBQ1RVQUxJWkFSX1RPUElDTyIsIkFDVFVBTElaQVJfVVNVQVJJTyIsIkNSRUFSX1JFU1BVRVNUQVMiLCJDUkVBUl9UT1BJQ08iLCJDUkVBUl9VU1VBUklPIiwiRUxJTUlOQVJfUkVTUFVFU1RBUyIsIkVMSU1JTkFSX1RPUElDTyIsIkVMSU1JTkFSX1VTVUFSSU8iLCJMRUVSX1JFU1BVRVNUQVMiLCJMRUVSX1RPUElDTyIsIkxFRVJfVVNVQVJJTyJdLCJ1c2VybmFtZSI6InJvZ2VyQWRtaW4iLCJzdWIiOiJyb2dlckFkbWluIiwiaWF0IjoxNzM3MTM4MjU0LCJleHAiOjE3MzcxNDU0NTR9.dPWF4YMTjw-zAfl3ELfL0JMJ7tMIyzXYNREXy6-Fug8")
                        .header("X-Auth-Token", "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJBQ1RVQUxJWkFSX1JFU1BVRVNUQVMiLCJBQ1RVQUxJWkFSX1RPUElDTyIsIkFDVFVBTElaQVJfVVNVQVJJTyIsIkNSRUFSX1JFU1BVRVNUQVMiLCJDUkVBUl9UT1BJQ08iLCJDUkVBUl9VU1VBUklPIiwiRUxJTUlOQVJfUkVTUFVFU1RBUyIsIkVMSU1JTkFSX1RPUElDTyIsIkVMSU1JTkFSX1VTVUFSSU8iLCJMRUVSX1JFU1BVRVNUQVMiLCJMRUVSX1RPUElDTyIsIkxFRVJfVVNVQVJJTyJdLCJ1c2VybmFtZSI6InJvZ2VyQWRtaW4iLCJzdWIiOiJyb2dlckFkbWluIiwiaWF0IjoxNzM3MTM4MjU0LCJleHAiOjE3MzcxNDU0NTR9.dPWF4YMTjw-zAfl3ELfL0JMJ7tMIyzXYNREXy6-Fug8"))
                        .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(authorities = "LEER_TOPICO")
    void testListarTopicos() throws Exception {
        Page<Topico> pageTopicos = new PageImpl<>(List.of(new Topico(), new Topico()));
        Mockito.when(topicoServ.getAllTopicos(any(Pageable.class))).thenReturn(pageTopicos);

        mockMvc.perform(get("/topicos/listarTopicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }
    @Test
    @WithMockUser(authorities = "LEER_RESPUESTAS")
    void testListarRespuestas() throws Exception {
        Mockito.when(respuestasServ.getAllRespuestas()).thenReturn(List.of(new Respuestas(), new Respuestas()));

        mockMvc.perform(get("/respuestas/listarRespuestas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
    @Test
    @WithMockUser(authorities = "CREAR_TOPICO")
    void testCrearTopico() throws Exception {
        TopicoDto topicoDto = new TopicoDto("Título", "Mensaje","2025/1/28", Categorias.BackEnd);
        Topico topico = new Topico();
        Mockito.when(topicoServ.guardarTopico(any(Topico.class))).thenReturn(topico);

        mockMvc.perform(post("/topicos/crearTopico")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Título\",\"mensaje\":\"Mensaje\",\"fecha_creacion_topico\":" +
                                 "\"2025-01-15\",\"categoria\":\"BackEnd\"}"))
                .andExpect(status().isCreated());
    }
    @Test
    @WithMockUser(authorities = "ACTUALIZAR_TOPICO")
    void testActualizarTopico() throws Exception {
        Topico topico = new Topico();
        topico.setTitulo("tituloTopico");

        Mockito.when(topicoServ.getTopicoById(1L)).thenReturn(Optional.of(topico));
        Mockito.when(topicoServ.guardarTopico(any(Topico.class))).thenReturn(topico);

        mockMvc.perform(put("/topicos/actualizarTopicoId/1")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJBQ1RVQUxJWkFSX1JFU1BVRVNUQVMiLCJBQ1RVQUxJWkFSX1RPUElDTyIsIkFDVFVBTElaQVJfVVNVQVJJTyIsIkNSRUFSX1JFU1BVRVNUQVMiLCJDUkVBUl9UT1BJQ08iLCJDUkVBUl9VU1VBUklPIiwiRUxJTUlOQVJfUkVTUFVFU1RBUyIsIkVMSU1JTkFSX1RPUElDTyIsIkVMSU1JTkFSX1VTVUFSSU8iLCJMRUVSX1JFU1BVRVNUQVMiLCJMRUVSX1RPUElDTyIsIkxFRVJfVVNVQVJJTyJdLCJ1c2VybmFtZSI6InJvZ2VyQWRtaW4iLCJzdWIiOiJyb2dlckFkbWluIiwiaWF0IjoxNzM3MTcyNjE5LCJleHAiOjE3MzcxNzk4MTl9.7MFwewUrLk5vqcfZEuzfAWV7VkPiTREakZPbTXXrEuc"))
                        .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(authorities = "ELIMINAR_TOPICO")
    void testEliminarTopico() throws Exception {
        Mockito.when(topicoServ.getTopicoById(1L)).thenReturn(Optional.of(new Topico()));

        mockMvc.perform(delete("/topicos/eliminarTopicoId/1")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJBQ1RVQUxJWkFSX1JFU1BVRVNUQVMiLCJBQ1RVQUxJWkFSX1RPUElDTyIsIkFDVFVBTElaQVJfVVNVQVJJTyIsIkNSRUFSX1JFU1BVRVNUQVMiLCJDUkVBUl9UT1BJQ08iLCJDUkVBUl9VU1VBUklPIiwiRUxJTUlOQVJfUkVTUFVFU1RBUyIsIkVMSU1JTkFSX1RPUElDTyIsIkVMSU1JTkFSX1VTVUFSSU8iLCJMRUVSX1JFU1BVRVNUQVMiLCJMRUVSX1RPUElDTyIsIkxFRVJfVVNVQVJJTyJdLCJ1c2VybmFtZSI6InJvZ2VyQWRtaW4iLCJzdWIiOiJyb2dlckFkbWluIiwiaWF0IjoxNzM3MTcyNjE5LCJleHAiOjE3MzcxNzk4MTl9.7MFwewUrLk5vqcfZEuzfAWV7VkPiTREakZPbTXXrEuc"))
                        .andExpect(status().isForbidden());
    }

}
