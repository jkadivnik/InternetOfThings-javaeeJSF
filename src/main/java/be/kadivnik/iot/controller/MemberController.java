/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.kadivnik.iot.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import be.kadivnik.iot.model.Member;
import be.kadivnik.iot.service.MemberService;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
@Model
public class MemberController extends BaseController {

    @Inject
    private MemberService memberService;

    @Produces
    @Named
    private Member newMember;

    @Produces
    @Named
    private Member selectedMember;

    @PostConstruct
    public void initMemberController() {
    	initNewMember();
    	initSelectedMember();
    }
    
    public void initNewMember() {
        newMember = new Member();
    }

    public void initSelectedMember() {
        selectedMember = new Member();
    }


    @Override
    public void create() {
        try {
            memberService.create(newMember);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
            getFacesContext().addMessage(null, m);
            initNewMember();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            getFacesContext().addMessage(null, m);
        }
    }

	@Override
	public void update() {
        try {
            memberService.create(selectedMember);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated!", "Update successful");
            getFacesContext().addMessage(null, m);
            initSelectedMember();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Update unsuccessful");
            getFacesContext().addMessage(null, m);
        }
	}

	@Override
	void delete() {
        try {
            memberService.create(selectedMember);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Deleted!", "Delete successful");
            getFacesContext().addMessage(null, m);
            initSelectedMember();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Delete unsuccessful");
            getFacesContext().addMessage(null, m);
        }
	}
}
