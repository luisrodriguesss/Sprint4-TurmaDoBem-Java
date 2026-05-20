package br.com.fiap.resource;

import br.com.fiap.bo.TriagemBO;
import br.com.fiap.entities.Triagem;
import br.com.fiap.exceptions.ErroNegocioException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/triagens")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TriagemResource {

    private TriagemBO bo;

    public TriagemResource() throws SQLException, ClassNotFoundException {
        bo = new TriagemBO();
    }

    @GET
    public List<Triagem> listar() throws SQLException {
        return bo.listarTodas();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) throws SQLException {
        Triagem t = bo.buscarPorId(id);
        if (t == null) return Response.status(404).entity("Triagem nao encontrada").build();
        return Response.ok(t).build();
    }

    @POST
    public Response cadastrar(Triagem triagem) {
        try {
            bo.cadastrarComValidacao(triagem);
            return Response.status(201).entity("Triagem registrada com sucesso").build();
        } catch (ErroNegocioException e) {
            return Response.status(400).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(500).entity("Erro ao acessar banco de dados").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Triagem triagem) throws SQLException {
        Triagem existente = bo.buscarPorId(id);
        if (existente == null) return Response.status(404).entity("Triagem nao encontrada").build();
        triagem.setId(id);
        bo.atualizar(triagem);
        return Response.ok("Triagem atualizada").build();
    }

 @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws SQLException {
        Triagem existente = bo.buscarPorId(id);
        if (existente == null) return Response.status(404).entity("Triagem nao encontrada").build();
        bo.deletar(id);
        return Response.noContent().build();
    }
}
