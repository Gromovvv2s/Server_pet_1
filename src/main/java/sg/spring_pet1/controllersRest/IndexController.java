package sg.spring_pet1.controllersRest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.spring_pet1.model.dto.Test;

@RestController
public class IndexController {
    @GetMapping(value = "/index", produces = {"application/json"})
    public ResponseEntity<Test> index() {
        System.out.println("index");
        String userName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Test test = new Test("все работает, я знаю, что зашел пользователь: " + userName);
        return ResponseEntity.ok(test);
    }

   // @Operation(summary = "Returns a greeting", description = "This endpoint returns a simple greeting message.")
    @GetMapping(value = "/test", produces = {"application/json"})
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }
}
