package br.com.fiap.resource;

import br.com.fiap.bo.BeneficiarioBO;
import br.com.fiap.entities.Beneficiario;
import br.com.fiap.exceptions.ErroNegocioException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/beneficiarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BeneficiarioResource {

    private BeneficiarioBO bo;

    public BeneficiarioResource() throws SQLException, ClassNotFoundException {
        bo = new BeneficiarioBO();
    }

    @GET
    public List<Beneficiario> listar() throws SQLException {
        return bo.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) throws SQLException {
        Beneficiario b = bo.buscarPorId(id);
        if (b == null) return Response.status(404).entity("Beneficiario nao encontrado").build();
        return Response.ok(b).build();
    }

    @POST
    public Response cadastrar(Beneficiario beneficiario) {
        try {
            bo.cadastrarComValidacao(beneficiario);
            return Response.status(201).entity("Beneficiario cadastrado com sucesso").build();
        } catch (ErroNegocioException e) {
            return Response.status(400).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(500).entity("Erro ao acessar banco de dados").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Beneficiario beneficiario) throws SQLException {
        Beneficiario existente = bo.buscarPorId(id);
        if (existente == null) return Response.status(404).entity("Beneficiario nao encontrado").build();
        beneficiario.setId(id);
        bo.atualizar(beneficiario);
        return Response.ok("Beneficiario atualizado").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws SQLException {
        Beneficiario existente = bo.buscarPorId(id);
        if (existente == null) return Response.status(404).entity("Beneficiario nao encontrado").build();
        bo.deletar(id);
        return Response.noContent().build();
    }
}
