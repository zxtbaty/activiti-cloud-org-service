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

package org.activiti.cloud.organization.core.rest.resource;

import org.activiti.cloud.organization.core.model.Group;
import org.activiti.cloud.organization.core.model.Model;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.activiti.cloud.organization.core.model.Model.ModelType.PROCESS_MODEL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link RestResourceEventHandler}
 */
@RunWith(MockitoJUnitRunner.class)
public class RestResourceEventHandlerTest {

    @Mock
    private RestResourceService restResourceService;

    @InjectMocks
    @Spy
    private RestResourceEventHandler restResourceEventHandler;

    @Test
    public void testHandleBeforeCreates() {

        // GIVEN
        Model processModel = new Model();
        processModel.setType(PROCESS_MODEL);
        processModel.setRefId("process_model_refId");

        doReturn(true).when(restResourceEventHandler).isEntityWithRestResource(eq(Model.class));

        // WHEN
        restResourceEventHandler.handleBeforeCreates(processModel);

        // THEN
        verify(restResourceService,
               times(1))
                .saveRestResourceFromEntityField(any(Model.class),
                                                 eq("data"),
                                                 eq("type"),
                                                 eq("refId"),
                                                 eq(false));
    }

    @Test
    public void testHandleBeforeUpdate() {

        // GIVEN
        Model processModel = new Model();
        processModel.setType(PROCESS_MODEL);
        processModel.setRefId("process_model_refId");

        doReturn(true).when(restResourceEventHandler).isEntityWithRestResource(eq(Model.class));

        // WHEN
        restResourceEventHandler.handleBeforeUpdate(processModel);

        // THEN
        verify(restResourceService,
               times(1))
                .saveRestResourceFromEntityField(any(Model.class),
                                                 eq("data"),
                                                 eq("type"),
                                                 eq("refId"),
                                                 eq(true));
    }

    @Test
    public void testHandleBeforeCreateForEntityWithoutRestResource() {

        // GIVEN
        doReturn(false).when(restResourceEventHandler).isEntityWithRestResource(eq(Group.class));

        // WHEN
        restResourceEventHandler.handleBeforeCreates(new Group());

        // THEN
        verify(restResourceService,
               never())
                .saveRestResourceFromEntityField(any(),
                                                 anyString(),
                                                 anyString(),
                                                 anyString(),
                                                 anyBoolean());
    }

    @Test
    public void testHandleBeforeUpdateForEntityWithoutRestResource() {

        // GIVEN
        doReturn(false).when(restResourceEventHandler).isEntityWithRestResource(eq(Group.class));

        // WHEN
        restResourceEventHandler.handleBeforeUpdate(new Group());

        // THEN
        verify(restResourceService,
               never())
                .saveRestResourceFromEntityField(any(),
                                                 anyString(),
                                                 anyString(),
                                                 anyString(),
                                                 anyBoolean());
    }
}
