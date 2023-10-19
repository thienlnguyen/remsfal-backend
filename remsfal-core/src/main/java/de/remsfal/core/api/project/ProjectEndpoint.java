package de.remsfal.core.api.project;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import de.remsfal.core.json.ProjectJson;
import de.remsfal.core.json.ProjectListJson;
import de.remsfal.core.json.ProjectMemberJson;
import de.remsfal.core.json.ProjectMemberListJson;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@Path(ProjectEndpoint.CONTEXT + "/" + ProjectEndpoint.VERSION + "/" + ProjectEndpoint.SERVICE)
public interface ProjectEndpoint {

    static final String CONTEXT = "api";
    static final String VERSION = "v1";
    static final String SERVICE = "projects";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieve information of all users.")
    ProjectListJson getProjects();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new project.")
    @APIResponse(responseCode = "201", description = "Project created successfully",
        headers = @Header(name = "Location", description = "URL of the new project"))
    @APIResponse(responseCode = "400", description = "Invalid request message")
    Response createProject(
        @Parameter(description = "Project information", required = true) @Valid ProjectJson project);

    @GET
    @Path("/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieve information of a project.")
    @APIResponse(responseCode = "404", description = "The project does not exist")
    ProjectJson getProject(
        @Parameter(description = "ID of the project", required = true) @PathParam("projectId") String projectId);

    @PATCH
    @Path("/{projectId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update information of a project.")
    @APIResponse(responseCode = "404", description = "The project does not exist")
    ProjectJson updateProject(
        @Parameter(description = "ID of the project", required = true) @PathParam("projectId") String projectId,
        @Parameter(description = "Project information", required = true) @Valid ProjectJson project);

    @DELETE
    @Path("/{projectId}")
    @Operation(summary = "Delete an existing project.")
    @APIResponse(responseCode = "204", description = "The project was deleted successfully")
    void deleteProject(
        @Parameter(description = "ID of the project", required = true) @PathParam("projectId") String projectId);

    @POST
    @Path("/{projectId}/members")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add a member to project.")
    @APIResponse(responseCode = "200", description = "Member added successfully")
    @APIResponse(responseCode = "400", description = "Invalid request message")
    Response addProjectMember(
        @Parameter(description = "ID of the project", required = true) @PathParam("projectId") String projectId,
        @Parameter(description = "Project member information", required = true) @Valid ProjectMemberJson member);

    @GET
    @Path("/{projectId}/members")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieve information of all project members.")
    @APIResponse(responseCode = "404", description = "The project does not exist")
    ProjectMemberListJson getProjectMembers(
        @Parameter(description = "ID of the project", required = true) @PathParam("projectId") String projectId);

    @PATCH
    @Path("/{projectId}/members/{memberId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update role of a project member.")
    @APIResponse(responseCode = "404", description = "The project or the member does not exist")
    ProjectJson updateProjectMember(
        @Parameter(description = "ID of the project", required = true) @PathParam("projectId") String projectId,
        @Parameter(description = "ID of the member", required = true) @PathParam("memberId") String memberId,
        @Parameter(description = "Project information", required = true) @Valid ProjectJson project);

    @DELETE
    @Path("/{projectId}/members/{memberId}")
    @Operation(summary = "Delete an existing project member.")
    @APIResponse(responseCode = "204", description = "The project member was deleted successfully")
    @APIResponse(responseCode = "404", description = "The project or the member does not exist")
    void deleteProjectMember(
        @Parameter(description = "ID of the project", required = true) @PathParam("projectId") String projectId,
        @Parameter(description = "ID of the member", required = true) @PathParam("memberId") String memberId);

}