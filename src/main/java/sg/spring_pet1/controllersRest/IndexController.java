package sg.spring_pet1.controllersRest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.spring_pet1.model.dto.IndexDto;

import static sg.spring_pet1.util.CollectionsAPI.INDEX;

@RestController
public class IndexController {
    @GetMapping(value = INDEX, produces = {"application/json"})
    public ResponseEntity<IndexDto> index() {
        System.out.println("index");
        String userName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String message = "все работает, я знаю, что зашел пользователь: " + userName;
        IndexDto indexDto = new IndexDto();
        indexDto.setMessage(message);
        return ResponseEntity.ok(indexDto);
    }

   // @Operation(summary = "Returns a greeting", description = "This endpoint returns a simple greeting message.")
    @GetMapping(value = "/test", produces = {"application/json"})
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }
}
