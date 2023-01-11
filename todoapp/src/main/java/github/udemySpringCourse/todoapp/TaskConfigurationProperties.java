package github.udemySpringCourse.todoapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;



@Configuration
@ConfigurationProperties("task")
public class TaskConfigurationProperties {
    private Template template;

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public static class Template{
        private boolean allowMultipletasks;

         public boolean isAllowMultipletasks() {
             return allowMultipletasks;
         }

         public void setAllowMultipletasks(boolean allowMultipletasks) {
             this.allowMultipletasks = allowMultipletasks;
         }
     }
}
