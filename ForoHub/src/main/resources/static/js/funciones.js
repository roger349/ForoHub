document.addEventListener('DOMContentLoaded', function () {
    // Simulamos que el usuario se ha logueado (esto normalmente vendría del servidor)
    const isUsuarioComun = true; // Aquí puedes hacer la lógica para saber si es un usuario común o no
    // Elementos del DOM
    const crearRespuestaBtn = document.getElementById('crear-respuesta-btn');
    const formCrearTopico = document.getElementById('form-crear-topico');
    const crearTopicoSection = document.getElementById('crear-topico');
    const tituloTopico = document.getElementById('titulo-topico');
    const mensajeTopico = document.getElementById('mensaje-topico');
    const listaRespuestas = document.getElementById('lista-respuestas');
    const respuestasSection = document.getElementById('respuestas');
    // Mostrar el formulario de crear tópicos si el usuario está logueado como usuario común
    if (isUsuarioComun) {
        crearRespuestaBtn.classList.remove('hide'); // Mostrar botón de crear respuesta
        crearTopicoSection.classList.remove('hide'); // Mostrar formulario de crear tópicos
    }
    // Mostrar la opción de "Crear Respuesta" solo si hay un tópico
    if (tituloTopico && mensajeTopico) {
        // Aquí iría la lógica para mostrar respuestas
        crearRespuestaBtn.addEventListener('click', function () {
            const nuevaRespuesta = document.createElement('div');
            nuevaRespuesta.classList.add('respuesta');
            nuevaRespuesta.innerHTML = `
                <textarea placeholder="Escribe tu respuesta..."></textarea>
                <button>Enviar Respuesta</button>
            `;
            listaRespuestas.appendChild(nuevaRespuesta);
        });
    }
    // Formulario para crear un nuevo tópico
    formCrearTopico.addEventListener('submit', function (event) {
        event.preventDefault();

        const nuevoTopico = {
            titulo: formCrearTopico.titulo.value,
            mensaje: formCrearTopico.mensaje.value,
        };
        // Aquí podrías hacer una solicitud POST al servidor para crear un tópico
        console.log('Nuevo tópico creado:', nuevoTopico);
        alert('Tópico creado exitosamente');
    });
});
document.addEventListener('DOMContentLoaded', function () {
    // Simulamos que el usuario es un usuario común (esto debería venir del backend)
    const isUsuarioComun = true; // Cambiar a false si no es usuario común
    // Elementos de la página
    const crearTopicoBtn = document.getElementById('crearTopicoBtn');
    const formCrearRespuesta = document.getElementById('formCrearRespuesta');
    const formMostrarTopico = document.getElementById('formMostrarTopico');
    // Mostrar las opciones solo si el usuario es común
    if (isUsuarioComun) {
        crearTopicoBtn.classList.remove('hide'); // Mostrar botón de crear tópico
        formCrearRespuesta.classList.remove('hide'); // Mostrar formulario de respuestas
    }
    // Agregar evento al formulario de crear respuesta
    formCrearRespuesta.addEventListener('submit', function(event) {
        event.preventDefault();
        // Aquí podrías hacer una petición AJAX para crear una respuesta
        alert('Respuesta enviada');
    });
    // Si se está mostrando un tópico, habilitar el formulario de respuestas
    // Esto se simula como un ejemplo, normalmente lo harías con un API
    const mostrarTopico = () => {
        // Simulamos un tópico cargado
        formMostrarTopico.querySelector('textarea').value = 'Contenido del tópico cargado';
    };
    // Llamar la función para cargar el tópico
    mostrarTopico();
});
document.addEventListener('DOMContentLoaded', function () {
    // Simulamos que el usuario es un administrador
    const isAdmin = true; // Cambiar a false si el usuario es común
    const isUsuarioComun = !isAdmin; // Si no es admin, es usuario común

    // Elementos de la página
    const crearTopicoBtn = document.getElementById('crearTopicoBtn');
    const formCrearRespuesta = document.getElementById('formCrearRespuesta');
    const formMostrarTopico = document.getElementById('formMostrarTopico');
    const adminPanel = document.getElementById('adminPanel');
    const respuestaUsuarioComun = document.querySelectorAll('.respuestaUsuarioComun');
    const accionesAdmin = document.querySelectorAll('.accionesAdmin');

    // Mostrar u ocultar botones y formularios según el rol
    if (isAdmin) {
        adminPanel.classList.remove('hide'); // Mostrar panel de administración
        // Mostrar opciones de administrar respuestas y tópicos
        document.querySelectorAll('.accionesAdmin').forEach(el => el.classList.remove('hide'));
    } else if (isUsuarioComun) {
        // Solo usuarios comunes pueden ver "Crear Respuesta"
        respuestaUsuarioComun.forEach(el => el.classList.remove('hide'));
    }

    // Mostrar opciones de crear tópicos para usuarios comunes
    if (isUsuarioComun) {
        crearTopicoBtn.classList.remove('hide'); // Mostrar botón de crear tópico
    }

    // Funciones para administrar respuestas y tópicos
    function editarTopico(id) {
        alert("Editar Tópico con ID: " + id);
    }

    function eliminarTopico(id) {
        alert("Eliminar Tópico con ID: " + id);
    }

    // Agregar más lógica de funciones si es necesario (crear respuestas, etc.)

});

