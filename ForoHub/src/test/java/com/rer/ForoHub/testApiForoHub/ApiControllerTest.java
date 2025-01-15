package com.rer.ForoHub.testApiForoHub;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.rer.ForoHub.Controllers.ApiController;
import com.rer.ForoHub.Models.Dto.TopicoDto;
import com.rer.ForoHub.Models.Model.Respuestas;
import com.rer.ForoHub.Models.Model.Topico;
import com.rer.ForoHub.Models.Model.Usuario;
import com.rer.ForoHub.Repository.RespuestasRepository;
import com.rer.ForoHub.Repository.TopicoRepository;
import com.rer.ForoHub.Repository.UsuarioRepository;
import com.rer.ForoHub.Services.RespuestasService;
import com.rer.ForoHub.Services.TopicoService;
import com.rer.ForoHub.Services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.mockito.*;

@WebMvcTest(ApiController.class)
public class ApiControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Mock
    private UsuarioService usuarioServ;
    @Mock
    TopicoService topicoServ;
    @Mock
    RespuestasService respuestasServ;
    @Mock
    UsuarioRepository usuarioRepo;
    @Mock
    TopicoRepository topicoRepo;
    @Mock
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
        when(usuarioServ.getAllUsuarios()).thenReturn(List.of(new Usuario(), new Usuario()));

        mockMvc.perform(get("/usuarios/listarUsuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @WithMockUser(authorities = "LEER_USUARIO")
    void testObtenerUsuarioPorId() throws Exception {
        Usuario usuario = new Usuario();
        when(usuarioServ.getUsuarioById(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/usuarios/obtenerUsuarioId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    @WithMockUser(authorities = "ACTUALIZAR_USUARIO")
    void testActualizarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        when(usuarioServ.getUsuarioById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioServ.saveUsuario(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/usuarios/actualizarUsuarioId/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre_usuario\":\"updatedUser\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre_usuario").value("updatedUser"));
    }

    @Test
    @WithMockUser(authorities = "ELIMINAR_USUARIO")
    void testEliminarUsuario() throws Exception {
        when(usuarioServ.getUsuarioById(1L)).thenReturn(Optional.of(new Usuario()));

        mockMvc.perform(delete("/usuarios/eliminarUsuarioId/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario Eliminado"));
    }

    @Test
    @WithMockUser(authorities = "LEER_TOPICO")
    void testListarTopicos() throws Exception {
        Page<Topico> pageTopicos = new PageImpl<>(List.of(new Topico(), new Topico()));
        when(topicoServ.getAllTopicos(any(Pageable.class))).thenReturn(pageTopicos);

        mockMvc.perform(get("/topicos/listarTopicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    @WithMockUser(authorities = "LEER_RESPUESTAS")
    void testListarRespuestas() throws Exception {
        when(respuestasServ.getAllRespuestas()).thenReturn(List.of(new Respuestas(), new Respuestas()));

        mockMvc.perform(get("/respuestas/listarRespuestas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @WithMockUser(authorities = "CREAR_TOPICO")
    void testCrearTopico() throws Exception {
        TopicoDto topicoDto = new TopicoDto("Título", "Mensaje", LocalDate.now(), "Categoría");
        Topico topico = new Topico();
        when(topicoServ.guardarTopico(any(Topico.class))).thenReturn(topico);

        mockMvc.perform(post("/topicos/crearTopico")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Título\",\"mensaje\":\"Mensaje\",\"fecha_creacion_topico\":" +
                                 "\"2025-01-15\",\"categoria\":\"Categoría\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "ACTUALIZAR_TOPICO")
    void testActualizarTopico() throws Exception {
        Topico topico = new Topico();
        when(topicoServ.getTopicoById(1L)).thenReturn(Optional.of(topico));
        when(topicoServ.guardarTopico(any(Topico.class))).thenReturn(topico);

        mockMvc.perform(put("/topicos/actualizarTopicoId/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Actualizado\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Actualizado"));
    }

    @Test
    @WithMockUser(authorities = "ELIMINAR_TOPICO")
    void testEliminarTopico() throws Exception {
        when(topicoServ.getTopicoById(1L)).thenReturn(Optional.of(new Topico()));

        mockMvc.perform(delete("/topicos/eliminarTopicoId/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Topico Eliminado"));
    }
}

