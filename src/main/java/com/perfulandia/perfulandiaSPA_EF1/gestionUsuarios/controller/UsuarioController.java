package com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.model.Usuario;
import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;  
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/gestionUsuarios/usuarios") //URL base
@Tag(name= "Usuarios", description = "Operaciones relacionadas con los usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //GUARDAR USUARIO
    @Operation(summary = "Crear usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario guardado correctamente")
    })
    @PostMapping
    ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    //LISTAR USUARIOS
    @Operation(summary = "Listar todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay usuarios registrados")
    })
    @GetMapping()
    public ResponseEntity<List<Usuario>> ListarUsuarios() {
        List<Usuario> usuarios = usuarioService.buscarTodosUsuarios();
        if (usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    //BUSCAR USUARIO POR ID
    @Operation(summary = "Buscar usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Integer id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    //ACTUALIZAR USUARIO
    @Operation(summary = "Actualizar usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping
    public ResponseEntity<Usuario> actualizarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioActualizado = usuarioService.update(usuario);
        if (usuarioActualizado != null) {
            return ResponseEntity.ok(usuarioActualizado);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //ELIMINAR USUARIO
    @Operation(summary = "Eliminar usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        } 
        usuarioService.delete(id);
        return ResponseEntity.ok().build();
    }
}