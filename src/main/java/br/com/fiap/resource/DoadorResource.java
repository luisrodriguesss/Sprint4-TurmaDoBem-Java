package br.com.fiap.resource;

import br.com.fiap.bo.DoadorBO;
import br.com.fiap.entities.Doador;
import br.com.fiap.exceptions.ErroNegocioException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/doadores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoadorResource {

    private DoadorBO bo;

    public DoadorResource() throws SQLException, ClassNotFoundException {
        bo = new DoadorBO();
    }

    @GET
    public List<Doador> listar() throws SQLException {
        return bo.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) throws SQLException {
        Doador d = bo.buscarPorId(id);
        if (d == null) return Response.status(404).entity("Doador nao encontrado").build();
        return Response.ok(d).build();
    }

    @POST
    public Response cadastrar(Doador doador) {
        try {
            bo.cadastrarComValidacao(doador);
            return Response.status(201).entity("Doador cadastrado com sucesso").build();
        } catch (ErroNegocioException e) {
            return Response.status(400).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(500).entity("Erro ao acessar banco de dados").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Doador doador) throws SQLException {
        Doador existente = bo.buscarPorId(id);
        if (existente == null) return Response.status(404).entity("Doador nao encontrado").build();
        doador.setId(id);
        bo.atualizar(doador);
        return Response.ok("Doador atualizado").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws SQLException {
        Doador existente = bo.buscarPorId(id);
        if (existente == null) return Response.status(404).entity("Doador nao encontrado").build();
        bo.deletar(id);
        return Response.noContent().build();
    }
}
