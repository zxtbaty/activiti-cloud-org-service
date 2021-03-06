/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.cloud.services.organization.rest.api;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.activiti.cloud.organization.api.Project;
import org.activiti.cloud.services.organization.rest.config.ApiAlfrescoPageableApi;
import org.activiti.cloud.services.organization.swagger.SwaggerConfiguration.AlfrescoProjectsPage;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.activiti.cloud.services.organization.rest.api.ProjectRestApi.PROJECTS;
import static org.activiti.cloud.services.organization.rest.config.RepositoryRestConfig.API_VERSION;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * Controller for {@link Project} resources.
 */
@RestController
@Api(tags = PROJECTS, description = "Retrieve and manage project definitions")
@RequestMapping(path = API_VERSION, produces = {HAL_JSON_VALUE, APPLICATION_JSON_VALUE})
public interface ProjectRestApi {

    String PROJECTS = "projects";

    String GET_PROJECT_ID_PARAM_DESCR = "The id of the project to retrieve";

    String CREATE_PROJECT_PARAM_DESCR = "The details of the project to create";

    String UPDATE_PROJECT_ID_PARAM_DESCR = "The id of the project to update";

    String UPDATE_PROJECT_PARAM_DESCR = "The new values to update";

    String DELETE_PROJECT_ID_PARAM_DESCR = "The id of the project to delete";

    String IMPORT_PROJECT_FILE_PARAM_DESCR = "The file containing the zipped project";

    String EXPORT_PROJECT_ID_PARAM_DESCR = "The id of the project to export";

    String ATTACHMENT_API_PARAM_DESCR =
            "<b>true</b> value enables a web browser to download the file as an attachment.<br> " +
                    "<b>false</b> means that a web browser may preview the file in a new tab or window, " +
                    "but not download the file.";

    String UPLOAD_FILE_PARAM_NAME = "file";

    String EXPORT_AS_ATTACHMENT_PARAM_NAME = "attachment";

    @ApiOperation(
            tags = PROJECTS,
            value = "List projects",
            notes = "Get the list of available projects. " +
                    "Minimal information for each project is returned.",
            produces = APPLICATION_JSON_VALUE,
            response = AlfrescoProjectsPage.class)
    @ApiAlfrescoPageableApi
    @GetMapping(path = "/projects")
    PagedResources<Resource<Project>> getProjects(Pageable pageable);

    @ApiOperation(
            tags = PROJECTS,
            value = "Create new project")
    @PostMapping(path = "/projects")
    @ResponseStatus(CREATED)
    Resource<Project> createProject(
            @ApiParam(CREATE_PROJECT_PARAM_DESCR)
            @RequestBody Project project);

    @ApiOperation(
            tags = PROJECTS,
            value = "Get project")
    @GetMapping(path = "/projects/{projectId}")
    Resource<Project> getProject(
            @ApiParam(GET_PROJECT_ID_PARAM_DESCR)
            @PathVariable String projectId);

    @ApiOperation(
            tags = PROJECTS,
            value = "Update project details")
    @PutMapping(path = "/projects/{projectId}")
    Resource<Project> updateProject(
            @ApiParam(UPDATE_PROJECT_ID_PARAM_DESCR)
            @PathVariable String projectId,
            @ApiParam(UPDATE_PROJECT_PARAM_DESCR)
            @RequestBody Project project);

    @ApiOperation(
            tags = PROJECTS,
            value = "Delete project")
    @DeleteMapping(path = "/projects/{projectId}")
    @ResponseStatus(NO_CONTENT)
    void deleteProject(
            @ApiParam(DELETE_PROJECT_ID_PARAM_DESCR)
            @PathVariable String projectId);

    @ApiOperation(
            tags = PROJECTS,
            value = "Import an project as zip file",
            notes = "Allows a zip file to be uploaded containing an project definition and any number of included models.")
    @PostMapping(path = "/projects/import", consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(CREATED)
    Resource<Project> importProject(
            @ApiParam(IMPORT_PROJECT_FILE_PARAM_DESCR)
            @RequestParam(UPLOAD_FILE_PARAM_NAME) MultipartFile file) throws IOException;

    @ApiOperation(
            tags = PROJECTS,
            value = "Export an project as zip file",
            notes = "This will create and download the zip " +
                    "containing the project folder and all related models.<br>")
    @GetMapping(path = "/projects/{projectId}/export")
    void exportProject(
            HttpServletResponse response,
            @ApiParam(EXPORT_PROJECT_ID_PARAM_DESCR)
            @PathVariable String projectId,
            @ApiParam(ATTACHMENT_API_PARAM_DESCR)
            @RequestParam(name = EXPORT_AS_ATTACHMENT_PARAM_NAME,
                    required = false,
                    defaultValue = "true") boolean attachment) throws IOException;
}
