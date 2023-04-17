package com.zerobase.babbook.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/owner/*")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_')")

public class OwnerController {

}
