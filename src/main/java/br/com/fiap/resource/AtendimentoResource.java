package br.com.fiap.resource;

import br.com.fiap.bo.AtendimentoBO;
import br.com.fiap.entities.Atendimento;
import br.com.fiap.exceptions.ErroNegocioException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/atendimentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AtendimentoResource {

    private AtendimentoBO bo;

    public AtendimentoResource() throws SQLException, ClassNotFoundException {
        bo = new AtendimentoBO();
    }

    @GET
    public List<Atendimento> listar() throws SQLException {
        return bo.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) throws SQLException {
        Atendimento a = bo.buscarPorId(id);
        if (a == null) return Response.status(404).entity("Atendimento nao encontrado").build();
        return Response.ok(a).build();
    }

    @POST
    public Response cadastrar(Atendimento atendimento) {
        try {
            bo.cadastrarComValidacao(atendimento);
            return Response.status(201).entity("Atendimento registrado com sucesso").build();
        } catch (ErroNegocioException e) {
            return Response.status(400).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(500).entity("Erro ao acessar banco de dados").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Atendimento atendimento) throws SQLException {
        Atendimento existente = bo.buscarPorId(id);
        if (existente == null) return Response.status(404).entity("Atendimento nao encontrado").build();
        atendimento.setId(id);
        bo.atualizar(atendimento);
        return Response.ok("Atendimento atualizado").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws SQLException {
        Atendimento existente = bo.buscarPorId(id);
        if (existente == null) return Response.status(404).entity("Atendimento nao encontrado").build();
        bo.deletar(id);
        return Response.noContent().build();
    }
}
