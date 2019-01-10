package lv.helloit.lottery.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final SecurityPropertiesBean securityPropertiesBean;

    @Autowired
    public SecurityConfiguration(SecurityPropertiesBean securityPropertiesBean) {
        this.securityPropertiesBean = securityPropertiesBean;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(securityPropertiesBean.getUsername()).password("{noop}" + securityPropertiesBean.getPassword()).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/adminMenu.html").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/start-registration").hasRole("ADMIN")
                .antMatchers("/tasks**").authenticated()
                .and()
                .formLogin()
                .loginPage("/show-login-page")
                .loginProcessingUrl("/authenticateTheUser")
                .defaultSuccessUrl("/index.html")
                .permitAll();
    }
}
