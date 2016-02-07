package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.UUID;

@SpringBootApplication
@Controller
public class DemoApplication implements ServletContextInitializer, CommandLineRunner {
    private void initH2Console(ServletContext servletContext) {
        ServletRegistration.Dynamic h2ConsoleServlet = servletContext.addServlet("H2Console", new org.h2.server.web.WebServlet());
        h2ConsoleServlet.addMapping("/h2-console/*");
        h2ConsoleServlet.setInitParameter("-properties", "src/main/resources");
        h2ConsoleServlet.setLoadOnStartup(1);
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        initH2Console(servletContext);

    }

    @RequestMapping
    String index() {
        return "redirect:/h2-console";
    }

    @Override
    public void run(String... strings) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    userRepository.save(new User(UUID.randomUUID().toString()));
                }
            }
        }).start();
    }

    @Autowired
    UserRepository userRepository;
}
