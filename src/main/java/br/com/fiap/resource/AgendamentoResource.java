package br.com.fiap.resource;

import br.com.fiap.bo.AgendamentoBO;
import br.com.fiap.entities.Agendamento;
import br.com.fiap.exceptions.ErroNegocioException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/agendamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgendamentoResource {

    private AgendamentoBO bo;

    public AgendamentoResource() throws SQLException, ClassNotFoundException {
        bo = new AgendamentoBO();
    }

    @GET
    public List<Agendamento> listar() throws SQLException {
        return bo.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) throws SQLException {
        Agendamento a = bo.buscarPorId(id);
        if (a == null) return Response.status(404).entity("Agendamento nao encontrado").build();
        return Response.ok(a).build();
    }

    @POST
    public Response cadastrar(Agendamento agendamento) {
        try {
            bo.cadastrarComValidacao(agendamento);
            return Response.status(201).entity("Agendamento cadastrado com sucesso").build();
        } catch (ErroNegocioException e) {
            return Response.status(400).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(500).entity("Erro ao acessar banco de dados").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Agendamento agendamento) throws SQLException {
        Agendamento existente = bo.buscarPorId(id);
        if (existente == null) return Response.status(404).entity("Agendamento nao encontrado").build();
        agendamento.setId(id);
        bo.atualizar(agendamento);
        return Response.ok("Agendamento atualizado").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws SQLException {
        Agendamento existente = bo.buscarPorId(id);
        if (existente == null) return Response.status(404).entity("Agendamento nao encontrado").build();
        bo.deletar(id);
        return Response.noContent().build();
    }
}
