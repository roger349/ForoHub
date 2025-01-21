APP ForoHub

# Título del Proyecto

App Foro Hub challenger

# Tecnologia: 

Proyecto con Spring Boot
Maven para el manejo de dependencias
MySql para el manejo de la base de datos
Flyway migrations
Seguridad: La seguridad esta en base a Spring Security y JWT
Validacion de datos con Validate

# Descripción

Este proyecto de un Foro que permite a los usuarios registrarse, iniciar sesión y acceder a una API para realizar operaciones crud.

# Características:

- En esta aplicacion hay dos clases de usuarios: 
  ADMIN : que tiene el crud de las entidades Usuarios,Topicos y Respuestas.
  permisos del Admin:
        CREAR_USUARIO
        LEER_USUARIO
        ACTUALIZAR_USUARIO
        ELIMINAR_USUARIO
        CREAR_TOPICO
        LEER_TOPICO
        ACTUALIZAR_TOPICO
        ELIMINAR_TOPICO
        CREAR_RESPUESTAS
        LEER_RESPUESTAS
        ACTUALIZAR_RESPUESTAS
        ELIMINAR_RESPUESTAS
  USUARIO: que tiene el crud de las entidades Topicos y Respuestas.
  Permisos del Usuario comun:
        CREAR_TOPICO
        LEER_TOPICO
        ACTUALIZAR_TOPICO
        ELIMINAR_TOPICO
        CREAR_RESPUESTAS
        LEER_RESPUESTAS
        ACTUALIZAR_RESPUESTAS
        ELIMINAR_RESPUESTAS

- Autenticación de usuarios mediante login y contraseña
- Registrar nuevos usuarios
- API para realizar operaciones de crud
- Controladores para manejar la autenticación de usuarion en el login y la API (LoginController y ApiController)

# Controladores

LoginController:

Maneja la autenticación de usuarios

- Métodos:

  login: autentica a un usuario y devuelve un token de acceso
  registrarUsuario: registra a un nuevo usuario

ApiController:

Realiza operaciones de crud sobre las entidades Usuarios, Topico y Respuestas

- Métodos:
         obtenerUsuarioPorId()
         actualizarUsuario()
         eliminarUsuario()    
         listarTopicos()       
         crearTopico()  
         actualizarTopico()
         eliminarTopico()
         listarRespuestas()
         crearRespuesta()
         actualizarRespuesta()   
         eliminarRespuesta()        
         listarTopicoPorIdRespuestas()

Se utiliza el protocolo HTTP. Los métodos y rutas disponibles son:

         PostMapping("/topicos/crearTopico")
         PostMapping("/respuestas/crearRespuesta/{id}")
         GetMapping("/usuarios/listarUsuarios")
         GetMapping("/usuarios/obtenerUsuarioId/{id}")
         GetMapping("/topicos/listarTopicos")
         GetMapping("/respuestas/listarRespuestas")
         GetMapping("/topicos/listarRespuestasPorIdTopico/{id}")
         PutMapping("/usuarios/actualizarUsuarioId/{id}")
         PutMapping("/topicos/actualizarTopicoId/{id}")
         PutMapping("/respuestas/actualizarRespuestaId/{id}")
         DeleteMapping("/usuarios/eliminarUsuarioId/{id}")
         DeleteMapping("/topicos/eliminarTopicoId/{id}")
         DeleteMapping("/respuestas/eliminarRespuestaId/{id}")




     