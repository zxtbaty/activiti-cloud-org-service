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

package org.activiti.cloud.services.organization.controllers;

import java.util.Optional;

import org.activiti.cloud.organization.core.model.Model;
import org.activiti.cloud.organization.core.rest.context.RestContext;
import org.activiti.cloud.organization.core.rest.context.RestContextProvider;
import org.activiti.cloud.organization.core.service.RestClientService;
import org.activiti.cloud.services.organization.assemblers.ValidationErrorResourceAssembler;
import org.activiti.cloud.services.organization.jpa.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.activiti.cloud.organization.core.model.Model.ModelType.PROCESS_MODEL;
import static org.activiti.cloud.services.organization.config.RepositoryRestConfig.API_VERSION;

@RestController
@RequestMapping(value = API_VERSION + "/validate")
public class ValidateModelController {

    private final RestClientService restClientService;
    private final RestContextProvider contextProvider;

    private final ModelRepository modelRepository;
    private final ValidationErrorResourceAssembler validationErrorResourceAssembler;

    @Autowired
    public ValidateModelController(RestClientService restClientService,
                                   RestContextProvider contextProvider,
                                   ModelRepository modelRepository,
                                   ValidationErrorResourceAssembler validationErrorResourceAssembler) {
        this.restClientService = restClientService;
        this.contextProvider = contextProvider;
        this.modelRepository = modelRepository;
        this.validationErrorResourceAssembler = validationErrorResourceAssembler;
    }

    @RequestMapping(value = "/{modelId}", method = RequestMethod.POST)
    @ResponseBody
    public String validateModel(@PathVariable(value = "modelId") String modelId,
                                @RequestParam("file") MultipartFile content) {

        // todo refactor this to a service separately
        final Optional<Model> model = modelRepository.findById(modelId);
        if (!model.isPresent()) {
            // todo handle missing model exception
        }

        // call repository for model
        final String url = contextProvider.getContext(RestContext.ACTIVITI).getResource(PROCESS_MODEL).getUrl();

        return restClientService.validateModel(url,
                                               content);
    }
}
