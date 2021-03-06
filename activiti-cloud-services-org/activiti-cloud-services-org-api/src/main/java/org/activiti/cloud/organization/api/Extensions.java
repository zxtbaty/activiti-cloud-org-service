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
package org.activiti.cloud.organization.api;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Model extensions
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class Extensions {

    @JsonProperty("properties")
    private Map<String, ProcessVariable> processVariables = new HashMap<>();

    private Map<String, Map<VariableMappingType, Map<String, String>>> variablesMappings = new HashMap<>();

    public Map<String, ProcessVariable> getProcessVariables() {
        return processVariables;
    }

    public void setProcessVariables(Map<String, ProcessVariable> processVariables) {
        this.processVariables = processVariables;
    }

    public Map<String, Map<VariableMappingType, Map<String, String>>> getVariablesMappings() {
        return variablesMappings;
    }

    public void setVariablesMappings(Map<String, Map<VariableMappingType, Map<String, String>>> variablesMappings) {
        this.variablesMappings = variablesMappings;
    }
}
