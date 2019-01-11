package lv.helloit.lottery.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
        http.httpBasic()
                .and()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("chooseLotteryWinner.html", "createLottery.html",
                        "stopLottery.html", "lotteryStats.html", "lotteryList.html").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/start-registration", "/stop-registration",
                        "/choose-winner").hasRole("ADMIN")
                .   antMatchers(HttpMethod.GET, "/stats", "/show-admin-page").hasRole("ADMIN")
                .and()
                    .formLogin()
                    .loginPage("/show-login-page")
                    .loginProcessingUrl("/authenticateTheUser")
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .permitAll();
    }
}
