package io.haedoang.transactional.api;

import io.haedoang.transactional.api.application.dto.SignUpRequest;
import io.haedoang.transactional.user.domain.User;
import io.haedoang.transactional.user.domain.application.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author : haedoang
 * date : 2022-08-23
 * description :
 */
@Slf4j
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ApiController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest request) {
        User savedUser = userService.save(request);
        log.info("savedUser: {}", savedUser);
        return ResponseEntity.ok().body(savedUser);
    }
//
//    //required
//    @PostMapping("/signup/fail")
//    public ResponseEntity<Void> signupFail(@RequestBody SignUpRequest request) {
//        userService.saveFail_required(request);
//        return ResponseEntity.ok().build();
//    }
//
//    //requiredsNew
//    @PostMapping("/signup/fail2")
//    public ResponseEntity<Void> signupFailRequiredsNew(@RequestBody SignUpRequest request) {
//        userService.saveFail_requireds_new(request);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/signup/fail3")
//    public ResponseEntity<Void> signupFailRequiredsNewThrowParent(@RequestBody SignUpRequest request) {
//        userService.saveFail_requireds_new_ThrowParent(request);
//        return ResponseEntity.ok().build();
//    }
}
