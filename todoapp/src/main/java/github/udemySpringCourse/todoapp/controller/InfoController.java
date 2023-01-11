package github.udemySpringCourse.todoapp.controller;

import github.udemySpringCourse.todoapp.TaskConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Constructor;

@RestController
public class InfoController {

   // @Value("${spring.datasource.url}")

    private DataSourceProperties url;
    private TaskConfigurationProperties myProp;

    public InfoController(DataSourceProperties url, TaskConfigurationProperties myProp) {
        this.url = url;
        this.myProp = myProp;
    }

    @GetMapping("/info/url")
    String url(){
        return url.getUrl();
    }
    @GetMapping("/info/prop")
    boolean myProp(){
        return myProp.getTemplate().isAllowMultipletasks();
    }
}
