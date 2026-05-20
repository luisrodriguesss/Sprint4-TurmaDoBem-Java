package br.com.fiap.resource;

import br.com.fiap.bo.DentistaBO;
import br.com.fiap.entities.Dentista;
import br.com.fiap.exceptions.ErroNegocioException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/dentistas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DentistaResource {

    private DentistaBO bo;

    public DentistaResource() throws SQLException, ClassNotFoundException {
        bo = new DentistaBO();
    }

    @GET
    public List<Dentista> listar() throws SQLException {
        return bo.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) throws SQLException {
        Dentista d = bo.buscarPorId(id);
        if (d == null) return Response.status(404).entity("Dentista nao encontrado").build();
        return Response.ok(d).build();
    }

    @POST
    public Response cadastrar(Dentista dentista) {
        try {
            bo.cadastrarComValidacao(dentista);
            return Response.status(201).entity("Dentista cadastrado com sucesso").build();
        } catch (ErroNegocioException e) {
            return Response.status(400).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(500).entity("Erro ao acessar banco de dados").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Dentista dentista) throws SQLException {
        Dentista existente = bo.buscarPorId(id);
        if (existente == null) return Response.status(404).entity("Dentista nao encontrado").build();
        dentista.setId(id);
        bo.atualizar(dentista);
        return Response.ok("Dentista atualizado").build();
    }

   @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws SQLException {
        Dentista existente = bo.buscarPorId(id);
        if (existente == null) return Response.status(404).entity("Dentista nao encontrado").build();
        bo.deletar(id);
        return Response.noContent().build();
    }
}
