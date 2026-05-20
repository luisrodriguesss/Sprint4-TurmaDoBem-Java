package br.com.fiap.resource;

import br.com.fiap.bo.ProcedimentoBO;
import br.com.fiap.entities.Procedimento;
import br.com.fiap.exceptions.ErroNegocioException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/procedimentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProcedimentoResource {

    private ProcedimentoBO bo;

    public ProcedimentoResource() throws SQLException, ClassNotFoundException {
        bo = new ProcedimentoBO();
    }

    @GET
    public List<Procedimento> listar() throws SQLException {
        return bo.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) throws SQLException {
        Procedimento p = bo.buscarPorId(id);
        if (p == null) return Response.status(404).entity("Procedimento nao encontrado").build();
        return Response.ok(p).build();
    }

    @POST
    public Response cadastrar(Procedimento procedimento) {
        try {
            bo.cadastrarComValidacao(procedimento);
            return Response.status(201).entity("Procedimento cadastrado com sucesso").build();
        } catch (ErroNegocioException e) {
            return Response.status(400).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(500).entity("Erro ao acessar banco de dados").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Procedimento procedimento) throws SQLException {
        Procedimento existente = bo.buscarPorId(id);
        if (existente == null) return Response.status(404).entity("Procedimento nao encontrado").build();
        procedimento.setId(id);
        bo.atualizar(procedimento);
        return Response.ok("Procedimento atualizado").build();
    }

    @DELETE
    @Path("/{id}")
   public Response deletar(@PathParam("id") int id) throws SQLException {
        Procedimento existente = bo.buscarPorId(id);
        if (existente == null) return Response.status(404).entity("Procedimento nao encontrado").build();
        bo.deletar(id);
        return Response.noContent().build();
    }
}
