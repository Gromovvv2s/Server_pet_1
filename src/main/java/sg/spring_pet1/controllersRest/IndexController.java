package sg.spring_pet1.controllersRest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.spring_pet1.model.dto.Test;

@RestController
public class IndexController {
    @GetMapping("/index")
    public ResponseEntity<String> index() {
        System.out.println("index");
        //SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping(value = "/test", produces = "application/json")
    public ResponseEntity<Test> test() {
        Test t = new Test("заебал");
        return ResponseEntity.ok(t);
    }
}
