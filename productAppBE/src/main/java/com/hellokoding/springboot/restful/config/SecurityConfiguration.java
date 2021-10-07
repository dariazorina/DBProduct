package com.hellokoding.springboot.restful.config;


import com.hellokoding.springboot.restful.security.AuthoritiesConstants;
//import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.security.AjaxAuthenticationFailureHandler;
import io.github.jhipster.security.AjaxAuthenticationSuccessHandler;
import io.github.jhipster.security.AjaxLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@EnableWebSecurity
//@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {//} implements WebMvcConfigurer {

//    private final JHipsterProperties jHipsterProperties;

    private final RememberMeServices rememberMeServices;
    private final CorsFilter corsFilter;
    private final SecurityProblemSupport problemSupport;

    public SecurityConfiguration(RememberMeServices rememberMeServices, CorsFilter corsFilter,  SecurityProblemSupport problemSupport) {
//        this.jHipsterProperties = jHipsterProperties;
        this.rememberMeServices = rememberMeServices;
        this.corsFilter = corsFilter;
       this.problemSupport = problemSupport;
    }

//    @Override
//    @CrossOrigin
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:8098")
////                .allowedOrigins("http://localhost:8080")
//                .allowedMethods("*");
//    }

//    @CrossOrigin
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
////                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedMethods("*")
//                .allowedOrigins("http://localhost:8098")
//                //                .allowedOrigins("*");
//                .allowedHeaders("*");
//    }


//    @Bean
//    public SavedRequestAwareAuthenticationSuccessHandler successHandler() {
//        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
////        successHandler.setTargetUrlParameter("/secure/");  //If the targetUrlParameter has been set on the request, the value will be used as the destination. Any DefaultSavedRequest will again be removed.
//        return successHandler;
//    }


//    public class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//        public AjaxAuthenticationSuccessHandler() {
//        }
//
//        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//            response.setStatus(200);
//        }
//    }


    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler myAuthenticationFailureHandler(){
        return new MySimpleUrlAuthenticationFailureHandler();
    }

//    @Bean
//    public AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
//        return new AjaxAuthenticationSuccessHandler();
//    }

//    @Bean
//    public AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
//        return new AjaxAuthenticationFailureHandler();
//    }

//    @Bean
//    public AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler() {
//        return new AjaxLogoutSuccessHandler();
//    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new MySimpleLogoutSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/h2-console/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }
    // curl -i -X POST -d username=admin -d password=admin -c /opt/cookies.txt http://localhost:8098/api/authentication
    //@Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
////                .cors()
////                .and()
////                .csrf().disable()
//                .csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//
//                .and()
//                .addFilterBefore(corsFilter, CsrfFilter.class)
//                .exceptionHandling()
//
//
//              //  .authenticationEntryPoint(problemSupport)
//               // .accessDeniedHandler(problemSupport)
//
//                .and()
//                .rememberMe()
//                .rememberMeServices(rememberMeServices)
//                .rememberMeParameter("remember-me")
//                .key("67cc9eda5071660462ea5397a972db51b2bdaf592eb0c7c3c59304f1a3f6ee955fd7ac59cef9597e75b5ca9f6b2ba1946787")
//
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/api/authentication")
//                .successHandler(myAuthenticationSuccessHandler())
//                .failureHandler(myAuthenticationFailureHandler())
//                .permitAll()
//
//                .and()
////                .logout()
////                .logoutUrl("/#/login")               //https://www.baeldung.com/spring-security-logout
////            .logoutSuccessHandler(logoutSuccessHandler())  // Also, keep in mind that this custom bean has the responsibility to determine the destination to which the user is directed after logging out. Because of this, pairing the logoutSuccessHandler attribute with logoutSuccessUrl is not going to work, as both cover similar functionality.
////                .invalidateHttpSession(true)    //is it necessary?
////                .deleteCookies("JSESSIONID")    //is it necessary?
////                .permitAll()
//
////                .and()
//                .headers()
//                .contentSecurityPolicy("default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://cdn.ckeditor.com https://storage.googleapis.com; style-src 'self' 'unsafe-inline' https://cdn.ckeditor.com; img-src 'self' blob: data: https://cdn.ckeditor.com; font-src 'self' data:")
//
//                .and()
//                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
//
//                .and()
//                .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
//
//                .and()
//                .frameOptions()
//                .deny()
//
//                .and().httpBasic() //not tested a lot
//
//                .and()
//                .authorizeRequests()
////            .antMatchers("/api/authenticate").permitAll()
////            .antMatchers("/api/register").permitAll()
////            .antMatchers("/api/activate").permitAll()
////            .antMatchers("/api/account/reset-password/init").permitAll()
////            .antMatchers("/api/account/reset-password/finish").permitAll()
////            .antMatchers("/api/**").authenticated()
////            .antMatchers("/management/health").permitAll()
////            .antMatchers("/management/info").permitAll()
////            .antMatchers("/management/prometheus").permitAll()
////            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
////            .antMatchers("/person/**").hasAuthority(AuthoritiesConstants.ADMIN);
//
//
////                .antMatchers( "/#/mtype").hasAuthority(AuthoritiesConstants.ADMIN)
////            .antMatchers( "/#/article").hasAnyRole("ADMIN");
//
////            .antMatchers("/#/article/**").hasAuthority(AuthoritiesConstants.ADMIN)
//
//
////                .antMatchers("/**").permitAll()
////                .antMatchers("/article/**").hasRole("ADMIN")//hasAuthority(AuthoritiesConstants.ADMIN)
////                .antMatchers("/person/**").hasAuthority(AuthoritiesConstants.ADMIN);
//
//
//        // @formatter:off
//
//
////            .antMatchers("/article/**").hasRole("ADMIN")//hasAuthority(AuthoritiesConstants.ADMIN)
////            .antMatchers("/person/**").hasAuthority(AuthoritiesConstants.ADMIN)
////            .antMatchers("/**").denyAll();
//
//
//
////                .antMatchers("/article/**").permitAll()
//
//
//                //работает
//                //doesn't work >>>>>>>
////                .antMatchers("/article/**", "/person/**").hasAuthority(AuthoritiesConstants.ADMIN)
//
//                .anyRequest().hasAnyAuthority(AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN)
//                .antMatchers("/**").denyAll();
///////////////////
//
//
////                .antMatchers("/").permitAll()
////                .antMatchers("/login**").permitAll()
////                .anyRequest().authenticated();
//
//
//                //.antMatchers("/*").permitAll();//denyAll();
//
//        // @formatter:on
//    }



//    @Bean
//    public AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
//        return new AjaxAuthenticationSuccessHandler();
//    }
//
//    @Bean
//    public AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
//        return new AjaxAuthenticationFailureHandler();
//    }
//
//    @Bean
//    public AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler() {
//        return new AjaxLogoutSuccessHandler();
//    }

@Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf().disable()  //todo
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .addFilterBefore(corsFilter, CsrfFilter.class)
                .exceptionHandling()

                .authenticationEntryPoint(problemSupport)
                .accessDeniedHandler(problemSupport)


                .and()
                .rememberMe()
                .rememberMeServices(rememberMeServices)
                .rememberMeParameter("remember-me")
                .key("67cc9eda5071660462ea5397a972db51b2bdaf592eb0c7c3c59304f1a3f6ee955fd7ac59cef9597e75b5ca9f6b2ba1946787")


                .and()
                .formLogin()
                .loginProcessingUrl("/api/authentication")
                .successHandler(myAuthenticationSuccessHandler())
                .failureHandler(myAuthenticationFailureHandler())
                .permitAll()

                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
                .permitAll()

                .and()
                .headers()
                .contentSecurityPolicy("default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://cdn.ckeditor.com https://storage.googleapis.com; style-src 'self' 'unsafe-inline' https://cdn.ckeditor.com; img-src 'self' blob: data: https://cdn.ckeditor.com; font-src 'self' data:")
                .and()
                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                .and()
                .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
                .and()
                .frameOptions()
                .deny()

                .and()
                .authorizeRequests()
//                .antMatchers("/api/authenticate").permitAll()
//                .antMatchers("/api/register").permitAll()
//                .antMatchers("/api/activate").permitAll()
//                .antMatchers("/api/account/reset-password/init").permitAll()
//                .antMatchers("/api/account/reset-password/finish").permitAll()
//                .antMatchers("/api/**").authenticated()
//                .antMatchers("/management/health").permitAll()
//                .antMatchers("/management/info").permitAll()
//                .antMatchers("/management/prometheus").permitAll()
//                .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
//                .antMatchers("/person/**").hasAuthority(AuthoritiesConstants.ADMIN);


    //                //работает
//                //doesn't work >>>>>>>
////                .antMatchers("/article/**", "/person/**").hasAuthority(AuthoritiesConstants.ADMIN)
//

                .antMatchers("/").permitAll()
                .antMatchers("/login**").permitAll()
                .antMatchers("/static/**", "/css/**", "/js/**", "/favicon.ico").permitAll()
                .anyRequest().hasAnyAuthority(AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN);
              //  .antMatchers("/**").denyAll();
//
//    // @formatter:on
    }
}
