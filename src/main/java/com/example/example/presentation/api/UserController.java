package com.example.example.presentation.api;

import com.example.example.application.UserService;
import com.example.example.domain.User;
import com.example.example.infrastructure.projection.UserAndOrder;
import com.example.example.presentation.api.request.SignupRequest;
import com.example.example.presentation.api.response.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(
        value = "유저 관련 API",
        tags = "user"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @ApiOperation(
            value = "내 정보 상세 조회"
    )
    @GetMapping("/me")
    public UserResponse get(@ApiIgnore @AuthenticationPrincipal UserDetails userDetails) {
        Long id = Long.parseLong(userDetails.getUsername());
        final User user = userService.getById(id);
        return UserResponse.of(user);
    }

    @ApiOperation(
            value = "전체 유저 목록 및 가장 최근 주문 정보 조회"
    )
    @GetMapping
    public Page<UserAndOrder> find(@RequestParam(required = false) String email, @RequestParam(required = false) String name, Pageable pageable) {
        if (StringUtils.hasText(email)) {
            return userService.findLastOrderPerUserByEmail(email, pageable);
        } else if (StringUtils.hasText(name)) {
            return userService.findLastOrderPerUserByName(name, pageable);
        }
        return userService.findLastOrderPerUser(pageable);
    }

    @ApiOperation(
            value = "회원 가입"
    )
    @PostMapping
    public UserResponse signup(@RequestBody @Valid SignupRequest signupRequest, @ApiIgnore BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        final User user = userService.signup(signupRequest);
        return UserResponse.of(user);
    }
}
