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
package be.kadivnik.iot.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import be.kadivnik.iot.data.MemberDAO;
import be.kadivnik.iot.model.Member;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class MemberService implements DataAccessService<Member> {

    @Inject
    private Logger log;
    @Inject
    private MemberDAO memberDAO;
    @Inject
    private Event<Member> memberEventSrc;

    public Member create(Member member) {
        log.info("Creating " + member.getName());
        Member createdMember = memberDAO.create(member);
        memberEventSrc.fire(member);
        return createdMember;
    }

	@Override
	public Member update(Member member) {
        log.info("Updating " + member.getName());
		Member updatedMember = memberDAO.update(member);
        memberEventSrc.fire(member);
        return updatedMember;
	}

	@Override
	public void delete(Member member) {
        log.info("Deleting " + member.getName());
		memberDAO.delete(member);
        memberEventSrc.fire(member);
		
	}
}
