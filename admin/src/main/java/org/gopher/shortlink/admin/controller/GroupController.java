package org.gopher.shortlink.admin.controller;

import lombok.RequiredArgsConstructor;
import org.gopher.shortlink.admin.service.GroupService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

}
