package br.com.fiap.resource;

import br.com.fiap.bo.DoacaoBO;
import br.com.fiap.entities.Doacao;
import br.com.fiap.exceptions.ErroNegocioException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/doacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoacaoResource {

    private DoacaoBO bo;

    public DoacaoResource() throws SQLException, ClassNotFoundException {
        bo = new DoacaoBO();
    }

    @GET
    public List<Doacao> listar() throws SQLException {
        return bo.listarTodas();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) throws SQLException {
        Doacao d = bo.buscarPorId(id);
        if (d == null) return Response.status(404).entity("Doacao nao encontrada").build();
        return Response.ok(d).build();
    }

    @POST
    public Response cadastrar(Doacao doacao) {
        try {
            bo.registrarComValidacao(doacao);
            return Response.status(201).entity("Doacao cadastrada com sucesso").build();
        } catch (ErroNegocioException e) {
            return Response.status(400).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(500).entity("Erro ao acessar banco de dados").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Doacao doacao) throws SQLException {
        Doacao existente = bo.buscarPorId(id);
        if (existente == null) return Response.status(404).entity("Doacao nao encontrada").build();
        doacao.setId(id);
        bo.atualizar(doacao);
        return Response.ok("Doacao atualizada").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws SQLException {
        Doacao existente = bo.buscarPorId(id);
        if (existente == null) return Response.status(404).entity("Doacao nao encontrada").build();
        bo.deletar(id);
        return Response.noContent().build();
    }
}
